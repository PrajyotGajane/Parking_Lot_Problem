package com.bridgelabaz.parkinglot.models;

import com.bridgelabaz.parkinglot.enums.VehicleBrand;
import com.bridgelabaz.parkinglot.enums.VehicleColor;

import java.util.Objects;

public class Vehicle {
      private final String vehicleNumber;
      private final VehicleBrand brand;
      private final VehicleColor vehicleColor;

      public Vehicle(String vehicleNumber, VehicleBrand brand, VehicleColor vehicleColor) {
            this.vehicleNumber = vehicleNumber;
            this.brand = brand;
            this.vehicleColor = vehicleColor;
      }

      public String getVehicleNumber() {
            return vehicleNumber;
      }

      public VehicleBrand getBrand() {
            return brand;
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
                    brand == vehicle.brand &&
                    vehicleColor == vehicle.vehicleColor;
      }

      @Override
      public int hashCode() {
            return Objects.hash(vehicleNumber, brand, vehicleColor);
      }
}
