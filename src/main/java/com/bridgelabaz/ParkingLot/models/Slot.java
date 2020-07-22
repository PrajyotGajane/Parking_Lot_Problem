package com.bridgelabaz.ParkingLot.models;

import java.time.LocalTime;

public class Slot {
      public String vehicle;
      public LocalTime time = LocalTime.now().withNano(0);
      public int slot;

      public Slot(int slot, String vehicle) {
            this.vehicle = vehicle;
            System.out.println(time);
            this.slot = slot;
      }

      public Slot() {
      }

      public int getSlot() {
            return slot;
      }

      public LocalTime getTime() {
            return time;
      }

      public String getVehicle() {
            return vehicle;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slot that = (Slot) o;
            return vehicle.equals(that.vehicle) &&
                    time.equals(that.time);
      }
}
