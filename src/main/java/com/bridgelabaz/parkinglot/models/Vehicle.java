package com.bridgelabaz.parkinglot.models;

import com.bridgelabaz.parkinglot.enums.VehicleBrand;
import com.bridgelabaz.parkinglot.enums.VehicleColor;

import java.util.Objects;

public class Vehicle {
      private final String vehicleNumber;
      private final VehicleBrand vehicleBrand;
      private final VehicleColor vehicleColor;

      public Vehicle(String vehicleNumber, VehicleBrand vehicleBrand, VehicleColor vehicleColor) {
            this.vehicleNumber = vehicleNumber;
            this.vehicleBrand = vehicleBrand;
            this.vehicleColor = vehicleColor;
      }

      public String getVehicleNumber() {
            return vehicleNumber;
      }

      public VehicleBrand getVehicleBrand() {
            return vehicleBrand;
      }

      public VehicleColor getVehicleColour() {
            return vehicleColor;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vehicle vehicle = (Vehicle) o;
            return Objects.equals(vehicleNumber, vehicle.vehicleNumber) &&
                    vehicleBrand == vehicle.vehicleBrand &&
                    vehicleColor == vehicle.vehicleColor;
      }

      @Override
      public int hashCode() {
            return Objects.hash(vehicleNumber, vehicleBrand, vehicleColor);
      }
}
