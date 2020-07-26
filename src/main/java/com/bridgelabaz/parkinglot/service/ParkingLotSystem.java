package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleSize;
import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.VehicleDetails;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotSystem {
      public ArrayList<ParkingLot> parkingLots;

      public ParkingLotSystem(ParkingLot... parkingLot) {
            this.parkingLots = new ArrayList<>(Arrays.asList(parkingLot));
      }

      public void park(VehicleDetails vehicle) {
            ParkingLot parkingLotAlLot = null;
            List<ParkingLot> lots = this.parkingLots;
            for (ParkingLot parkingLot : lots) {
                  parkingLot.isVehicleAlreadyPresent(vehicle);
            }
            if (vehicle.getDriverType().equals(DriverType.HANDICAPPED)) {
                        parkingLotAlLot = getLotForLargeVehicle(this.parkingLots);
            }
            if (vehicle.getDriverType().equals(DriverType.NORMAL)) {
                  if (vehicle.getVehicleSize().equals(VehicleSize.SMALL)) {
                        parkingLotAlLot = getLotForNormal(this.parkingLots);
                  }
                  else {
                        parkingLotAlLot = getLotForLargeVehicle(this.parkingLots);
                  }
            }

            if (parkingLotAlLot != null) {
                  parkingLotAlLot.parkVehicle(vehicle);
            }
      }

      public void unPark(VehicleDetails vehicle) {
            ParkingLot parkingLot = this.getParkingLotOfParkedVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
      }

      public ParkingLot getParkingLotOfParkedVehicle(VehicleDetails vehicle) {
            for (ParkingLot parkingLot : this.parkingLots) {
                  if (parkingLot.isVehiclePresent(vehicle)) {
                        return parkingLot;
                  }
            }
            throw new ParkingLotException("No Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
      }

      public boolean isVehicleParked(VehicleDetails vehicle) {
            ParkingLot parkingLot = getParkingLotOfParkedVehicle(vehicle);
            return parkingLot.isVehiclePresent(vehicle);
      }

      public int getVehicleSpot(VehicleDetails vehicle) {
            ParkingLot parkingLot = getParkingLotOfParkedVehicle(vehicle);
            return parkingLot.getPositionOfVehicle(vehicle);
      }

      public LocalDateTime getVehicleParkedTime(VehicleDetails vehicle) {
            ParkingLot parkingLot = getParkingLotOfParkedVehicle(vehicle);
            return parkingLot.getParkingTime(vehicle);
      }

      public ParkingLot getLotForNormal(List<ParkingLot> parkingLots) {
            parkingLots.sort(Comparator.comparing(ParkingLot::getCountOfVehicles));
            return parkingLots.get(0);
      }

      public ParkingLot getLotForHandicappedDriver(List<ParkingLot> parkingLots) throws ParkingLotException {
            return parkingLots.stream()
                    .filter(parkingLot -> parkingLot.getCountOfVehicles() != parkingLot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("Parking Full", ParkingLotException.ExceptionType.PARKING_LOT_FULL));
      }

      public static ParkingLot getLotForLargeVehicle(List<ParkingLot> parkingLots) throws ParkingLotException {
            return parkingLots.stream()
                    .sorted(Comparator.comparing(parkingLot -> (parkingLot.getParkingCapacity() - parkingLot.getCountOfVehicles()),
                            Comparator.reverseOrder()))
                    .filter(parkingLot -> parkingLot.getCountOfVehicles() != parkingLot.getParkingCapacity())
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException("Parking Full", ParkingLotException.ExceptionType.PARKING_LOT_FULL));
      }
}
