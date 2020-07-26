package com.bridgelabaz.parkinglot.models;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleColor;
import com.bridgelabaz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class VehicleDetails {
      private VehicleSize vehicleSize;
      private Object vehicle;
      private DriverType driverType;
      private VehicleColor color;

      public VehicleDetails(Object vehicle, DriverType driverType, VehicleSize vehicleSize, VehicleColor color) {
            this.vehicle = vehicle;
            this.driverType = driverType;
            this.vehicleSize = vehicleSize;
            this.color = color;
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

      public VehicleColor getColor() {
            return color;
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
