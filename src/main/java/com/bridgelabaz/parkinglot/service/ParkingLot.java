package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.utility.Slot;
import com.bridgelabaz.parkinglot.observer.AirportSecurity;
import com.bridgelabaz.parkinglot.observer.Observer;
import com.bridgelabaz.parkinglot.observer.Owner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
      private HashMap<Integer, Slot> parkingSpotMap = new HashMap<>();
      private int sizeOfParkingLot;
      public Owner owner;
      public AirportSecurity airportSecurity;
      List<Observer> observerList = new ArrayList<>();
      int carsParkedInLot = 0;

      public ParkingLot(int sizeOfParkingLot,Owner owner, AirportSecurity airportSecurity) {
            this.sizeOfParkingLot = sizeOfParkingLot;
            this.owner = owner;
            this.airportSecurity = airportSecurity;
            this.observerList.add(owner);
            this.observerList.add(airportSecurity);
      }

      public boolean isVehiclePresent(String vehicleNumber) {
            return parkingSpotMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() != null)
                    .anyMatch(entry -> entry.getValue().getVehicle().equals(vehicleNumber));
      }

      public void parkVehicle(String vehicleNumber) throws ParkingLotException {
            if (vehicleNumber == null)
                  throw new ParkingLotException("Invalid Vehicle", ParkingLotException.ExceptionType.INVALID_VEHICLE);
            if (parkingSpotMap.containsValue(vehicleNumber))
                  throw new ParkingLotException("Already Parked", ParkingLotException.ExceptionType.ALREADY_PARKED);
            if (parkingSpotMap.size() > sizeOfParkingLot)
                  throw new ParkingLotException("Parking Lot Full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            parkingSpotMap.put(getSpot(), new Slot(getSpot(), vehicleNumber, LocalTime.now().withNano(0)));
            carsParkedInLot++;
            if (parkingSpotMap.size() == sizeOfParkingLot) {
                  notifyAllObservers(true);
            }
      }

      private Integer getSpot() {
            for (int slot = 0; slot <= parkingSpotMap.size(); slot++) {
                  if (parkingSpotMap.get(slot) == null)
                        return slot;
            }
            return null;
      }

      public void notifyAllObservers(boolean status) {
            observerList.forEach(observer -> observer.parkingLotFull(status));
      }

      public void unParkVehicle(String vehicleNumber) throws ParkingLotException {
            Integer key = null;
            if (!parkingSpotMap.containsValue(vehicleSpotInLot(vehicleNumber)))
                  throw new ParkingLotException("Vehicle not present in lot",
                          ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
            for (Map.Entry<Integer, Slot> entry : parkingSpotMap.entrySet()) {
                  if (entry.getValue().equals(vehicleSpotInLot(vehicleNumber))) {
                        key = entry.getKey();
                  }
            }
            parkingSpotMap.put(key, null);
            carsParkedInLot--;
            notifyAllObservers(false);
      }

      public void parkAtOwnerProvidedSlot(int slot, String vehicle) throws ParkingLotException {
            boolean isCarPresent = this.isVehiclePresent(vehicle);
            if (isCarPresent) {
                  throw new ParkingLotException("Already Parked",
                          ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
            if (parkingSpotMap.get(slot) != null)
                  throw new ParkingLotException("Slot not empty",
                          ParkingLotException.ExceptionType.SLOT_NOT_EMPTY);
            parkingSpotMap.put(slot, new Slot(slot, vehicle, LocalTime.now().withNano(0)));
            carsParkedInLot++;
      }

      public Slot vehicleSpotInLot(String vehicleNumber) throws ParkingLotException {
            return this.parkingSpotMap.values()
                    .stream()
                    .filter(slot -> vehicleNumber.equals(slot.getVehicle()))
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException(" Vehicle not present in lot",
                            ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT));
      }

      public int getVehicleSpot(String vehicleNumber) throws ParkingLotException {
            return vehicleSpotInLot(vehicleNumber).getSlot();
      }

      public LocalTime getParkTime(String vehicleNumber) throws ParkingLotException {
            return vehicleSpotInLot(vehicleNumber).getTime();
      }

      public int getCarCount() {
            return carsParkedInLot;
      }
}
