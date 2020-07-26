package com.bridgelabaz.parkinglot.models;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleColor;
import com.bridgelabaz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class VehicleDetails {
      private VehicleSize vehicleSize;
      private Vehicle vehicle;
      private DriverType driverType;
      private String parkingAttendant;

      public VehicleDetails(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize, String parkingAttendant) {
            this.vehicle = vehicle;
            this.driverType = driverType;
            this.vehicleSize = vehicleSize;
            this.parkingAttendant = parkingAttendant;
      }

      public Vehicle getVehicle() {
            return vehicle;
      }

      public DriverType getDriverType() {
            return driverType;
      }

      public VehicleSize getVehicleSize() {
            return vehicleSize;
      }

      public String getParkingAttendant(){
            return parkingAttendant;
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
