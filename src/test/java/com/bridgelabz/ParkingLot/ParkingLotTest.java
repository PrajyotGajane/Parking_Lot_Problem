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
      public void givenVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
            parkingLot.parkedVehicle("GA-08-A-2323");
            boolean isParked = parkingLot.isVehiclePresent("GA-08-A-2323");
            Assert.assertTrue(isParked);
      }

      @Test
      public void givenVehicleNull_WhenParked_ShouldReturnTrue() {
            try {
                  parkingLot.parkedVehicle(null);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.INVALID_VEHICLE);
            }
      }

      @Test
      public void givenVehicle_WhenAlreadyParked_ShouldThrowCustomException() {
            ParkingLot parkingLot = new ParkingLot();
            try {
                  parkingLot.parkedVehicle("GA-08-A-2323");
                  parkingLot.parkedVehicle("GA-08-A-2323");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
      }

      @Test
      public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
            parkingLot.parkedVehicle("GA-08-A-2323");
            parkingLot.unParkVehicle("GA-08-A-2323");
            boolean isUnParked = parkingLot.isVehiclePresent("GA-08-A-2323");
            Assert.assertFalse(isUnParked);
      }

      @Test
      public void givenVehicleToUnPark_WhenNotPresent_ShouldThrowCustomException() {
            try {
                  parkingLot.unParkVehicle("GA-08-A-2323");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT);
            }
      }

      @Test
      public void givenParkingLotWithSize_WhenFull_ShouldInformOwnerAndReturnTrue() {
            parkingLot.parkingLotSize(3);
            try {
                  parkingLot.parkedVehicle("GA-08-A-2323");
                  parkingLot.parkedVehicle("MH-08-A-3455");
                  parkingLot.parkedVehicle("GJ-08-A-4567");
                  parkingLot.parkedVehicle("AP-08-A-4557");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            }
      }

      @Test
      public void givenParkingLotWithSize_WhenFullInformOwner_ShouldInformOwnerAndReturnTrue() throws ParkingLotException {
            parkingLot.parkingLotSize(3);
            parkingLot.parkedVehicle("GA-08-A-2323");
            parkingLot.parkedVehicle("MH-08-A-3455");
            parkingLot.parkedVehicle("GJ-08-A-4567");
            boolean informedOwner = parkingLot.owner.isParkingLotFull();

            Assert.assertTrue(informedOwner);
      }

      @Test
      public void givenParkingLotWithSize_WhenFullInformAirportSecurity_ShouldInformOwnerAndReturnTrue() throws ParkingLotException {
            parkingLot.parkingLotSize(3);
            parkingLot.parkedVehicle("GA-08-A-2323");
            parkingLot.parkedVehicle("MH-08-A-3455");
            parkingLot.parkedVehicle("GJ-08-A-4567");
            boolean informedAirportSecurity = parkingLot.airportSecurity.isParkingLotFull();
            Assert.assertTrue(informedAirportSecurity);
      }

      @Test
      public void givenParkingLotWithExactSize_WhenSpaceAvailableInformOwner_ShouldInformOwnerAndReturnTrue() throws ParkingLotException {
            parkingLot.parkingLotSize(2);
            parkingLot.parkedVehicle("GA-08-A-2323");
            parkingLot.parkedVehicle("GJ-08-A-4567");
            parkingLot.unParkVehicle("GJ-08-A-4567");
            boolean informedOwner = parkingLot.owner.isParkingLotFull();
            Assert.assertFalse(informedOwner);
      }
}