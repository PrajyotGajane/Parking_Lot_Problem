package com.bridgelabaz.parkinglot.models;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class VehicleDetails {
      private VehicleSize vehicleSize;
      private Object vehicle;
      private DriverType driverType;

      public VehicleDetails(Object vehicle, DriverType driverType, VehicleSize vehicleSize) {
            this.vehicle = vehicle;
            this.driverType = driverType;
            this.vehicleSize = vehicleSize;
      }

      public Object getVehicle() {
            return vehicle;
      }

      public DriverType getDriverType() {
            return driverType;
      }

      public VehicleSize getVehicleSize() {
            return vehicleSize;
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
