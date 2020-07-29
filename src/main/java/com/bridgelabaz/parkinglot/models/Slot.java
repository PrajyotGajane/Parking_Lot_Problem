package com.bridgelabaz.parkinglot.models;

import java.time.LocalDateTime;

public class Slot {
      public VehicleDetails vehicle;
      public LocalDateTime time;
      public Integer slotNumber;

      public Slot(VehicleDetails vehicle, Integer slotNumber, LocalDateTime time) {
            this.vehicle = vehicle;
            this.slotNumber = slotNumber;
            this.time = time;
      }

      public LocalDateTime getTime() {
            return time;
      }

      public VehicleDetails getDetailsVehicle() {
            return vehicle;
      }

      public Integer getSlotNumber() {
            return slotNumber;
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
