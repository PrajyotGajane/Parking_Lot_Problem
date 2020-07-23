package com.bridgelabaz.parkinglot.observer;

public class Owner implements Observer {
      public boolean isParkingLotFull;

      @Override
      public void parkingLotFull(boolean isParkingLotFUll) {
            this.isParkingLotFull = isParkingLotFUll;
      }

      public boolean isParkingLotFull() {
            return isParkingLotFull;
      }
}