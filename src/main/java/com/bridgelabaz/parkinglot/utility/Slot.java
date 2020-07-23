package com.bridgelabaz.parkinglot.utility;

import java.time.LocalTime;

public class Slot {
      public String vehicle;
      public LocalTime time;
      public int slot;

      public Slot(int slot, String vehicle, LocalTime time) {
            this.vehicle = vehicle;
            this.slot = slot;
            this.time = time;
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
