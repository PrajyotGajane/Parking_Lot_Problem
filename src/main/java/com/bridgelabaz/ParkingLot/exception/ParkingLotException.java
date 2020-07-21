package com.bridgelabaz.ParkingLot.exception;

public class ParkingLotException extends Exception {
      public ExceptionType type;

      public enum ExceptionType {
            ALREADY_PARKED, VEHICLE_NOT_PRESENT, PARKING_LOT_FULL, INVALID_VEHICLE
      }

      public ParkingLotException(String message, ExceptionType type) {
            super(message);
            this.type = type;
      }
}
