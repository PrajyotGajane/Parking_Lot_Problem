package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleBrand;
import com.bridgelabaz.parkinglot.enums.VehicleColor;
import com.bridgelabaz.parkinglot.enums.VehicleSize;
import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.VehicleDetails;
import com.bridgelabaz.parkinglot.models.Slot;
import com.bridgelabaz.parkinglot.observer.Observer;
import com.bridgelabaz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {
      private final int parkingCapacity;
      private final SlotAllotment slotAllotment;
      private final HashMap<Integer, Slot> parkedVehicles;
      private final List<Observer> observers;

      public ParkingLot(int parkingLotCapacity) {
            this.parkingCapacity = parkingLotCapacity;
            this.observers = new ArrayList<>();
            this.parkedVehicles = new HashMap<>();
            this.slotAllotment = new SlotAllotment(parkingLotCapacity);
      }

      public void informListeners(String capacityStatus) {
            for (Observer entry : observers) {
                  entry.update(capacityStatus);
            }
      }

      public void registerParkingLotObserver(Observer observer) {
            this.observers.add(observer);
      }

      public int getCountOfVehicles() {
            return parkedVehicles.size();
      }

      public boolean isVehiclePresent(VehicleDetails vehicle) {
            return parkedVehicles.entrySet()
                    .stream()
                    .anyMatch(entry -> vehicle.equals(entry.getValue().getVehicle()));

      }

      public void parkVehicle(VehicleDetails vehicle) {
            if (vehicle == null)
                  throw new ParkingLotException("Invalid Vehicle", ParkingLotException.ExceptionType.INVALID_VEHICLE);
            if (parkedVehicles.size() == parkingCapacity)
                  throw new ParkingLotException("Parking Lot full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            if (isVehiclePresent(vehicle))
                  throw new ParkingLotException("Already present", ParkingLotException.ExceptionType.ALREADY_PARKED);
            int slot = slotAllotment.getNearestParkingSlot();
            parkedVehicles.put(slot, new Slot(vehicle, slot, LocalDateTime.now().withSecond(0).withNano(0)));
            if (this.parkingCapacity == this.parkedVehicles.size()) {
                  informListeners("Capacity is Full");
            }
      }

      public void unParkVehicle(VehicleDetails vehicle) {
            if (vehicle == null)
                  throw new ParkingLotException("Invalid Vehicle", ParkingLotException.ExceptionType.INVALID_VEHICLE);

            if (!isVehiclePresent(vehicle)) {
                  throw new ParkingLotException("No Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
            }
            parkedVehicles.remove(getSlot(vehicle));
            informListeners("Capacity Available");
      }

      public Integer getSlot(VehicleDetails vehicle) {
            Integer slot = -1;
            for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
                  if (vehicle.equals(entry.getValue().getVehicle())) {
                        slot = entry.getKey();
                        slotAllotment.unParkUpdate(slot);
                  }
            }
            return slot;
      }

      public Integer getPositionOfVehicle(VehicleDetails vehicle) {
            Integer slot = -1;
            for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
                  if (vehicle.equals(entry.getValue().getVehicle())) {
                        slot = entry.getKey();
                  }
            }
            return slot;
      }

      public Integer findVehicle(VehicleDetails vehicle) {
            int slot = -1;
            for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
                  if (vehicle.equals(entry.getValue().getVehicle())) {
                        slot = entry.getKey();
                        this.unParkVehicle(vehicle);
                  }
            }
            return slot;
      }

      public void parkVehicle(int slot, VehicleDetails vehicle) {
            if (isVehiclePresent(vehicle))
                  throw new ParkingLotException("Already present", ParkingLotException.ExceptionType.ALREADY_PARKED);
            if (parkedVehicles.size() >= parkingCapacity)
                  throw new ParkingLotException("Parking Lot full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            this.parkedVehicles.put(slot, new Slot(vehicle, slot, LocalDateTime.now().withSecond(0).withNano(0)));
            slotAllotment.parkUpdate(slot);
      }

      public LocalDateTime getParkingTime(VehicleDetails vehicle) {
            Slot slot = parkedVehicles.get(getPositionOfVehicle(vehicle));
            return slot.getTime();
      }

      public void isVehicleAlreadyPresent(VehicleDetails vehicle) {
            if (isVehiclePresent(vehicle)) {
                  throw new ParkingLotException("Already present", ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
      }

      public Integer getParkingCapacity() {
            return parkingCapacity;
      }

      public List<Integer> getListOfSlotsByColour(VehicleColor vehicleColour) {
            List<Integer> slotNumbers = new ArrayList<>();
            for (Integer slotNumber : parkedVehicles.keySet()) {
                  if (parkedVehicles.get(slotNumber).getVehicle().getVehicle().getVehicleColour().equals(vehicleColour)) {
                        slotNumbers.add(slotNumber);
                  }
            }
            return slotNumbers;
      }

      public List<String> getSlotNumbersByCompanyAndColour(VehicleBrand brand, VehicleColor color) {
            List<String> parkingAttendant = new ArrayList<>();
            for (Integer slotNumber : parkedVehicles.keySet()) {
                  if (parkedVehicles.get(slotNumber).getVehicle().getVehicle().getVehicleColour().equals(color) &&
                          parkedVehicles.get(slotNumber).getVehicle().getVehicle().getBrand().equals(brand)) {
                        parkingAttendant.add(parkedVehicles.get(slotNumber).getVehicle().getVehicle().getVehicleNumber() +
                                " " + parkedVehicles.get(slotNumber).getVehicle().getParkingAttendant());
                  }
            }
            return parkingAttendant;
      }

      public List<Integer> getSlotNumbersByCompany(VehicleBrand brand) {
            List<Integer> slotNumbers = new ArrayList<>();
            for (Integer slotNumber : parkedVehicles.keySet()) {
                  if (parkedVehicles.get(slotNumber).getVehicle().getVehicle().getBrand().equals(brand)) {
                        slotNumbers.add(slotNumber);
                  }
            }
            return slotNumbers;
      }

      public List<Integer> getVehiclesParkedFromTime(int time) {
            List<Integer> slotNumbers = new ArrayList<>();
            for (Integer slotNumber : parkedVehicles.keySet()) {
                  if ((parkedVehicles.get(slotNumber).getTime().getMinute() - getCurrentTine().getMinute()) <= time) {
                        slotNumbers.add(slotNumber);
                  }
            }
            return slotNumbers;
      }

      private LocalDateTime getCurrentTine() {
            return LocalDateTime.now();
      }

      public List<String> getCompleteVehiclesList(DriverType driverType, VehicleSize vehicleSize) {
            return this.parkedVehicles.values()
                    .stream()
                    .filter(parkingSlot -> parkingSlot.getVehicle().getDriverType() == driverType)
                    .filter(parkingSlot -> parkingSlot.getVehicle().getVehicleSize() == vehicleSize)
                    .map(parkingSlot -> (("Slot: "+parkingSlot.getSlotNumber() + " Brand: "
                            + parkingSlot.getVehicle().getVehicle().getBrand()) + " Color:"
                            + (parkingSlot.getVehicle().getVehicle().getVehicleColour()) + " Size: "
                            + (parkingSlot.getVehicle().getVehicleSize()) + " Parking Attendant Name: "
                            + (parkingSlot.getVehicle().getParkingAttendant())))
                    .collect(Collectors.toList());
      }

      public List<Integer> getAllVehiclesParkedInParkingLot() {
            List<Integer> slotNumbers = new ArrayList<>();
            slotNumbers.addAll(parkedVehicles.keySet());
            return slotNumbers;
      }
}
