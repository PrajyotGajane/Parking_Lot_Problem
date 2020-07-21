package com.bridgelabaz.ParkingLot.service;

import com.bridgelabaz.ParkingLot.exception.ParkingLotException;

import java.util.HashMap;

public class ParkingLot {
      HashMap<String, String> isVehicleParked = new HashMap<>();

      public boolean parkedVehicle(String vehicleNumber, String owner) throws ParkingLotException {
            if (isVehicleParked.containsKey(vehicleNumber))
                  throw new ParkingLotException("Already Parked", ParkingLotException.ExceptionType.ALREADY_PARKED);
            isVehicleParked.put(vehicleNumber, owner);
            return isVehicleParked.containsKey(vehicleNumber);
      }
}
