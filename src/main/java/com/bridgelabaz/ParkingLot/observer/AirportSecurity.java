package com.bridgelabaz.ParkingLot.observer;

public class AirportSecurity implements Observer {
      public boolean isParkingLotFull;

      @Override
      public void parkingLotFull(boolean isParkingLotFull) {
            this.isParkingLotFull = isParkingLotFull;
      }

      public boolean isParkingLotFull() {
            return isParkingLotFull;
      }
}
