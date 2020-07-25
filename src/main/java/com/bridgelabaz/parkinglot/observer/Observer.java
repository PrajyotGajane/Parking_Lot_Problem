package com.bridgelabaz.parkinglot.observer;

public interface Observer {
      String getParkingLotStatus();
      void update(String parkingLotStatus);
}
