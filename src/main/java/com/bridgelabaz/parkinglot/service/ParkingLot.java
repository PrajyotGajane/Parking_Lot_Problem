package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.Vehicle;
import com.bridgelabaz.parkinglot.utility.Slot;
import com.bridgelabaz.parkinglot.observer.Observer;
import com.bridgelabaz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
      public int parkingCapacity;
      public SlotAllotment slotAllotment;
      public HashMap<Integer, Slot> parkedVehicles;
      public List<Observer> observers;

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

      public boolean isVehiclePresent(Vehicle vehicle) {
            return parkedVehicles.entrySet()
                    .stream()
                    .anyMatch(entry -> vehicle.equals(entry.getValue().getVehicle()));

      }

      public void parkVehicle(Vehicle vehicle) {
            if (vehicle == null)
                  throw new ParkingLotException("Invalid Vehicle", ParkingLotException.ExceptionType.INVALID_VEHICLE);
            if (parkedVehicles.size() == parkingCapacity)
                  throw new ParkingLotException("Parking Lot full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            if (isVehiclePresent(vehicle))
                  throw new ParkingLotException("Already present", ParkingLotException.ExceptionType.ALREADY_PARKED);
            parkedVehicles.put(slotAllotment.getNearestParkingSlot(), new Slot(vehicle, LocalDateTime.now().withSecond(0).withNano(0)));
            if (this.parkingCapacity == this.parkedVehicles.size()) {
                  informListeners("Capacity is Full");
            }
      }

      public void unParkVehicle(Vehicle vehicle) {
            if (vehicle == null)
                  throw new ParkingLotException("Invalid Vehicle", ParkingLotException.ExceptionType.INVALID_VEHICLE);

            if (!isVehiclePresent(vehicle)) {
                  throw new ParkingLotException("No Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
            }
            parkedVehicles.remove(getPositionOfVehicle(vehicle));
            informListeners("Capacity Available");
      }

      public Integer getPositionOfVehicle(Vehicle vehicle) {
            Integer slot = -1;
            for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
                  if (vehicle.equals(entry.getValue().getVehicle())) {
                        slot = entry.getKey();
                  }
            }
            return slot;
      }

      public Integer findVehicle(Vehicle vehicle) {
            int slot = -1;
            for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
                  if (vehicle.equals(entry.getValue().getVehicle())) {
                        slot = entry.getKey();
                        this.unParkVehicle(vehicle);
                  }
            }
            return slot;
      }

      public void parkVehicle(int slot, Vehicle vehicle) {
            if (isVehiclePresent(vehicle))
                  throw new ParkingLotException("Already present", ParkingLotException.ExceptionType.ALREADY_PARKED);
            if (parkedVehicles.size() >= parkingCapacity)
                  throw new ParkingLotException("Parking Lot full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            this.parkedVehicles.put(slot, new Slot(vehicle, LocalDateTime.now().withSecond(0).withNano(0)));
            slotAllotment.parkUpdate(slot);
      }

      public LocalDateTime getParkingTime(Vehicle vehicle) {
            Slot slot = parkedVehicles.get(getPositionOfVehicle(vehicle));
            return slot.getTime();
      }

      public void isVehicleAlreadyPresent(Vehicle vehicle) {
            if (isVehiclePresent(vehicle)) {
                  throw new ParkingLotException("Already present", ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
      }
}
