package com.bridgelabaz.parkinglot.models;

import com.bridgelabaz.parkinglot.enums.DriverType;

import java.util.Objects;

public class VehicleDetails {
      private Object vehicle;
      private DriverType driverType;

      public VehicleDetails(Object vehicle, DriverType driverType) {
            this.vehicle = vehicle;
            this.driverType = driverType;
      }

      public Object getVehicle() {
            return vehicle;
      }

      public DriverType getDriverType() {
            return driverType;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VehicleDetails that = (VehicleDetails) o;
            return Objects.equals(vehicle, that.vehicle) &&
                    driverType == that.driverType;
      }

      @Override
      public int hashCode() {
            return Objects.hash(vehicle, driverType);
      }
}
