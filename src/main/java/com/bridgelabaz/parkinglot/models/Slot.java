package com.bridgelabaz.parkinglot.models;

import java.time.LocalDateTime;

public class Slot {
      public VehicleDetails vehicle;
      public LocalDateTime time;

      public Slot(VehicleDetails vehicle, LocalDateTime time) {
            this.vehicle = vehicle;
            this.time = time;
      }

      public LocalDateTime getTime() {
            return time;
      }

      public VehicleDetails getVehicle() {
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
