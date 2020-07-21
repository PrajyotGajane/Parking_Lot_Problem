package com.bridgelabaz.ParkingLot.service;

import com.bridgelabaz.ParkingLot.exception.ParkingLotException;
import com.bridgelabaz.ParkingLot.observer.AirportSecurity;
import com.bridgelabaz.ParkingLot.observer.Observer;
import com.bridgelabaz.ParkingLot.observer.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLot {
      private HashMap<Integer, String> parkingSpotMap = new HashMap<>();
      private int sizeOfParkingLot = 10;
      public Owner owner;
      public AirportSecurity airportSecurity;
      List<Observer> observerList = new ArrayList<>();

      public boolean isVehiclePresent(String vehicleNumber) {
            return parkingSpotMap.containsValue(vehicleNumber);
      }

      public ParkingLot(Owner owner, AirportSecurity airportSecurity) {
            this.owner = owner;
            this.airportSecurity = airportSecurity;
            this.observerList.add(owner);
            this.observerList.add(airportSecurity);
      }

      public void parkedVehicle(String vehicleNumber) throws ParkingLotException {
            if (vehicleNumber == null)
                  throw new ParkingLotException("Invalid Vehicle", ParkingLotException.ExceptionType.INVALID_VEHICLE);
            if (parkingSpotMap.containsValue(vehicleNumber))
                  throw new ParkingLotException("Already Parked", ParkingLotException.ExceptionType.ALREADY_PARKED);
            if (parkingSpotMap.size() > sizeOfParkingLot)
                  throw new ParkingLotException("Parking Lot Full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            parkingSpotMap.put(getSpot(), vehicleNumber);
            if (parkingSpotMap.size() == sizeOfParkingLot) {
                  notifyAllObservers(true);
            }
      }

      private Integer getSpot() {
            return IntStream.rangeClosed(0, parkingSpotMap.size()).filter(slot -> parkingSpotMap.get(slot) == null).findFirst().orElse(-1);
      }

      public void notifyAllObservers(boolean status) {
            for (Observer observer : observerList) {
                  observer.parkingLotFull(status);
            }
      }

      public void unParkVehicle(String vehicleNumber) throws ParkingLotException {
            Integer key = null;
            if (!parkingSpotMap.containsValue(vehicleNumber))
                  throw new ParkingLotException("Vehicle not present in lot",
                          ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
            for (Map.Entry<Integer, String> entry : parkingSpotMap.entrySet()) {
                  if (entry.getValue().equals(vehicleNumber)) {
                        key = entry.getKey();
                  }
            }
            parkingSpotMap.put(key, null);
            notifyAllObservers(false);
      }

      public void parkingLotSize(int size) {
            this.sizeOfParkingLot = size;
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
            parkingSpotMap.put(slot, vehicle);
      }

      public int vehicleSpotInLot(String vehicleNumber) throws ParkingLotException {
            if (parkingSpotMap.containsValue(vehicleNumber)) {
                 int spot = parkingSpotMap.keySet()
                          .stream()
                          .filter(key -> vehicleNumber.equals(parkingSpotMap.get(key)))
                          .findFirst().get();
                  return spot;
            }
            throw new ParkingLotException("Vehicle not present in lot",
                    ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
      }
}
