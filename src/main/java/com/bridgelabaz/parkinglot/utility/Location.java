package com.bridgelabaz.parkinglot.utility;

import com.bridgelabaz.parkinglot.service.ParkingLot;

public class Location {
      ParkingLot parkingLot;
      int slotNumber;

      public void setParkingLot(ParkingLot parkingLot) {
            this.parkingLot = parkingLot;
      }

      public int getSlotNumber() {
            return slotNumber;
      }

      public void setSlotNumber(int slotNumber) {
            this.slotNumber = slotNumber;
      }
}
