package com.bridgelabz.ParkingLot;

import com.bridgelabaz.ParkingLot.exception.ParkingLotException;
import com.bridgelabaz.ParkingLot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
      @Test
      public void givenVehicle_WhenParked_ShouldReturnTrue() {
            ParkingLot parkingLot = new ParkingLot();
            boolean isParked;
            try {
                  isParked = parkingLot.parkedVehicle("GA-08-A-2323", "Prajyot");
                  Assert.assertTrue(isParked);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }

      }

      @Test
      public void givenVehicle_WhenAlreadyParked_ShouldThrowCustomException() {
            ParkingLot parkingLot = new ParkingLot();
            try {
                  parkingLot.parkedVehicle("GA-08-A-2323", "Prajyot");
                  parkingLot.parkedVehicle("GA-08-A-2323", "Prajyot");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
      }
}