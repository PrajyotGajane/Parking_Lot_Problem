package com.bridgelabz.ParkingLot;

import com.bridgelabaz.ParkingLot.exception.ParkingLotException;
import com.bridgelabaz.ParkingLot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
      ParkingLot parkingLot;

      @Before
      public void setUp() {
            parkingLot = new ParkingLot();
      }
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

      @Test
      public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
            parkingLot.parkedVehicle("GA-08-A-2323", "Prajyot");
            boolean isUnParked = parkingLot.unparkVehicle("GA-08-A-2323");
            Assert.assertEquals(false, isUnParked);
      }

      @Test
      public void givenVehicleToUnPark_WhenNotPresent_ShouldThrowCustomException() {
            try {
                  parkingLot.unparkVehicle("GA-08-A-2323");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
            }
      }
}