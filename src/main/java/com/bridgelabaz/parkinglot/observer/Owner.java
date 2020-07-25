package com.bridgelabaz.parkinglot.observer;

public class Owner implements Observer {
      private String parkingLotStatus;

      @Override
      public String getParkingLotStatus() {
            return parkingLotStatus;

      }

      @Override
      public void update(String parkingLotStatus) {
            this.parkingLotStatus = parkingLotStatus;
      }


}