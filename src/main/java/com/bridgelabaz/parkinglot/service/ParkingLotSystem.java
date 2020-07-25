package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.Vehicle;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotSystem {
      public ArrayList<ParkingLot> parkingLots;

      public ParkingLotSystem(ParkingLot... parkingLot) {
            this.parkingLots = new ArrayList<>(Arrays.asList(parkingLot));
      }

      public void park(Vehicle vehicle) {
            for (ParkingLot parkingLot : parkingLots) {
                  parkingLot.isVehicleAlreadyPresent(vehicle);
            }
            ArrayList<ParkingLot> listOfLots = new ArrayList<>(parkingLots);
            listOfLots.sort(Comparator.comparing(ParkingLot::getCountOfVehicles));
            listOfLots.get(0).parkVehicle(vehicle);
      }

      public void unPark(Vehicle vehicle) {
            ParkingLot parkingLot = this.getParkingLotOfParkedVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
      }

      public ParkingLot getParkingLotOfParkedVehicle(Vehicle vehicle) {
            for (ParkingLot parkingLot : this.parkingLots) {
                  if (parkingLot.isVehiclePresent(vehicle)) {
                        return parkingLot;
                  }
            }
            throw new ParkingLotException("No Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
      }

      public boolean isVehicleParked(Vehicle vehicle) {
            ParkingLot parkingLot = getParkingLotOfParkedVehicle(vehicle);
            return parkingLot.isVehiclePresent(vehicle);
      }

      public int getVehicleSpot(Vehicle vehicle) {
            ParkingLot parkingLot = getParkingLotOfParkedVehicle(vehicle);
            return parkingLot.getPositionOfVehicle(vehicle);
      }

      public LocalDateTime getVehicleParkedTime(Vehicle vehicle) {
            ParkingLot parkingLot = getParkingLotOfParkedVehicle(vehicle);
            return parkingLot.getParkingTime(vehicle);
      }
}
