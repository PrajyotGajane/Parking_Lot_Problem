package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleBrand;
import com.bridgelabaz.parkinglot.enums.VehicleColor;
import com.bridgelabaz.parkinglot.enums.VehicleSize;
import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.Slot;
import com.bridgelabaz.parkinglot.models.VehicleDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                  parkingLotAlLot = getLotForHandicappedDriver(this.parkingLots);
            }
            if (vehicle.getDriverType().equals(DriverType.NORMAL)) {
                  if (vehicle.getVehicleSize().equals(VehicleSize.SMALL)) {
                        parkingLotAlLot = getLotForNormal(this.parkingLots);
                  } else {
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

      public Map<ParkingLot, List<Integer>> getLotAndSlotListOfVehiclesByColor(VehicleColor vehicleColour) {
            Map<ParkingLot, List<Integer>> vehiclesWithSpecificColor = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<Integer> slotNumbers = parkingLot.getListOfSlotsByColour(vehicleColour);
                  if (slotNumbers.size() > 0) {
                        vehiclesWithSpecificColor.put(parkingLot, slotNumbers);
                  }
            }
            return vehiclesWithSpecificColor;
      }

      public Map<ParkingLot, List<String>> getVehicleNumberAndAttendantName(VehicleBrand brand, VehicleColor color) {
            Map<ParkingLot, List<String>> vehicleByCompanyAndColour = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<String> slotNumbers = parkingLot.getSlotNumbersByCompanyAndColour(brand, color);
                  if (slotNumbers.size() > 0) {
                        vehicleByCompanyAndColour.put(parkingLot, slotNumbers);
                  }
            }
            return vehicleByCompanyAndColour;
      }

      public Map<ParkingLot, List<Integer>> getSlotNumbersOfVehiclesByCompany(VehicleBrand brand) {
            Map<ParkingLot, List<Integer>> vehicleByCompany = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<Integer> slotNumbers = parkingLot.getSlotNumbersByCompany(brand);
                  if (slotNumbers.size() > 0) {
                        vehicleByCompany.put(parkingLot, slotNumbers);
                  }
            }
            return vehicleByCompany;
      }

      public Map<ParkingLot, List<Integer>> getVehiclesParkedFromTime(int time) {
            Map<ParkingLot, List<Integer>> slotNumbersByTime = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<Integer> slotNumbers = parkingLot.getVehiclesParkedFromTime(time);
                  if (slotNumbers.size() > 0) {
                        slotNumbersByTime.put(parkingLot, slotNumbers);
                  }
            }
            return slotNumbersByTime;
      }

      public Map<ParkingLot, List<Integer>> getSlotNumbersByVehicleSizeAndDriverType(DriverType driverType, VehicleSize vehicleSize) {
            Map<ParkingLot, List<Integer>> lotAndSlotNumbers = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<Integer> slotNumbers = parkingLot.getSlotNumbersByVehicleSizeAndDriverType(driverType, vehicleSize);
                  if (slotNumbers.size() > 0) {
                        lotAndSlotNumbers.put(parkingLot, slotNumbers);
                  }
            }
            return lotAndSlotNumbers;
      }

      public Map<ParkingLot, List<String>> getSlotNumbersBySizeAndDriverType(DriverType driverType, VehicleSize vehicleSize) {
            Map<ParkingLot, List<String>> lotAndSlotNumbers = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<String> slotNumbers = parkingLot.getCompleteVehiclesList(driverType, vehicleSize);
                  if (slotNumbers.size() > 0) {
                        lotAndSlotNumbers.put(parkingLot, slotNumbers);
                  }
            }
            return lotAndSlotNumbers;
      }

      public Map<ParkingLot, List<Integer>> getAllVehiclesParkedInParkingAllLot() {
            Map<ParkingLot, List<Integer>> lotAndSlotNumbers = new HashMap<>();
            for (ParkingLot parkingLot : this.parkingLots) {
                  List<Integer> slotNumbers = parkingLot.getAllVehiclesParkedInParkingLot();
                  if (slotNumbers.size() > 0) {
                        lotAndSlotNumbers.put(parkingLot, slotNumbers);
                  }
            }
            return lotAndSlotNumbers;
      }
}
