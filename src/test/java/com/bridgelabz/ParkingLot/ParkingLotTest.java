package com.bridgelabz.ParkingLot;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.observer.AirportSecurity;
import com.bridgelabaz.parkinglot.observer.Owner;
import com.bridgelabaz.parkinglot.service.ParkingLot;
import com.bridgelabaz.parkinglot.service.ParkingLotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class ParkingLotTest {
      ParkingLot parkingLot;

      @Before
      public void setUp() {
            parkingLot = new ParkingLot(5, new Owner(), new AirportSecurity());
      }

      @Test
      public void givenVehicle_WhenParked_ShouldReturnTrue() {
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  boolean isParked = parkingLot.isVehiclePresent("GA-08-A-2323");
                  Assert.assertTrue(isParked);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicleNull_WhenParked_ShouldReturnTrue() {
            try {
                  parkingLot.parkVehicle(null);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.INVALID_VEHICLE);
            }
      }

      @Test
      public void givenVehicle_WhenAlreadyParked_ShouldThrowCustomException() {
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.parkVehicle("GA-08-A-2323");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
      }

      @Test
      public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.unParkVehicle("GA-08-A-2323");
                  boolean isUnParked = parkingLot.isVehiclePresent("GA-08-A-2323");
                  Assert.assertFalse(isUnParked);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
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
            parkingLot = new ParkingLot(3, new Owner(), new AirportSecurity());
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.parkVehicle("MH-08-A-3455");
                  parkingLot.parkVehicle("GJ-08-A-4567");
                  parkingLot.parkVehicle("AP-08-A-4557");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            }
      }

      @Test
      public void givenParkingLotWithSize_WhenFullInformOwner_ShouldInformOwnerAndReturnTrue() {
            parkingLot = new ParkingLot(3, new Owner(), new AirportSecurity());
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.parkVehicle("MH-08-A-3455");
                  parkingLot.parkVehicle("GJ-08-A-4567");
                  boolean informedOwner = parkingLot.owner.isParkingLotFull();
                  Assert.assertTrue(informedOwner);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenParkingLotWithSize_WhenFullInformAirportSecurity_ShouldInformOwnerAndReturnTrue() {
            parkingLot = new ParkingLot(3, new Owner(), new AirportSecurity());
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.parkVehicle("MH-08-A-3455");
                  parkingLot.parkVehicle("AP-08-A-4557");
                  parkingLot.unParkVehicle("MH-08-A-3455");
                  parkingLot.parkVehicle("GJ-08-A-4544");
                  boolean informedAirportSecurity = parkingLot.airportSecurity.isParkingLotFull();
                  Assert.assertTrue(informedAirportSecurity);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenParkingLotWithExactSize_WhenSpaceAvailableInformOwner_ShouldInformOwnerAndReturnTrue() {
            parkingLot = new ParkingLot(2, new Owner(), new AirportSecurity());
            try {
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.parkVehicle("GJ-08-A-4567");
                  parkingLot.unParkVehicle("GJ-08-A-4567");
                  boolean informedOwner = parkingLot.owner.isParkingLotFull();
                  Assert.assertFalse(informedOwner);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenOwnerProvidesSlot_ShouldReturnTrue() {
            try {
                  parkingLot.parkAtOwnerProvidedSlot(1, "GA-08-A-2323");
                  boolean vehicleSlot = parkingLot.isVehiclePresent("GA-08-A-2323");
                  Assert.assertTrue(vehicleSlot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenOwnerProvidesSlot_IfVehicleAlreadyPresentOnSlot_ShouldThrowException() {
            try {
                  parkingLot.parkVehicle("GJ-08-A-4567");
                  parkingLot.parkAtOwnerProvidedSlot(0, "GA-08-A-2323");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_NOT_EMPTY, e.type);
            }
      }

      @Test
      public void givenParkedVehicle_WhenFound_ShouldReturnSpotInParkingLot() {
            try {
                  parkingLot.parkVehicle("MH-08-A-4567");
                  parkingLot.parkVehicle("GA-08-A-2323");
                  parkingLot.parkVehicle("GJ-08-A-4567");
                  int position = parkingLot.getVehicleSpot("GJ-08-A-4567");
                  Assert.assertEquals(2, position);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenParkedVehicle_WhenNotFound_ShouldThrowException() {
            try {
                  parkingLot.vehicleSpotInLot("MH-08-A-4567");
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT, e.type);
            }
      }

      @Test
      public void givenVehicle_WhenParked_ShouldReturnParkingTime() {
            try {
                  LocalTime localTime = LocalTime.now().withNano(0);
                  parkingLot.parkVehicle("AP-08-A-4557");
                  LocalTime parkedTime = parkingLot.getParkTime("AP-08-A-4557");
                  Assert.assertEquals(localTime, parkedTime);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenParkedInLot_ShouldReturnTrue() {
            ParkingLotService parkingService = new ParkingLotService(1, 1);
            try {
                  parkingService.parkCar("GA-08-A-4348");
                  boolean isVehiclePresent = parkingService.isVehiclePresent("GA-08-A-4348");
                  Assert.assertTrue(isVehiclePresent);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenParkedInLot_ShouldReturnLotAndSpotInLot() {
            ParkingLotService parkingService = new ParkingLotService(3, 3);
            try {
                  parkingService.parkCar("GA-08-A-4348");
                  parkingService.parkCar("GA-08-A-9767");
                  int parkedLotNumber = parkingService.getCarLocation("GA-08-A-9767");
                  Assert.assertEquals(1,parkedLotNumber);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenUnParkedInLot_ShouldReturnFalse() {
            ParkingLotService parkingService = new ParkingLotService(1, 1);
            try {
                  parkingService.parkCar("GA-08-A-4348");
                  parkingService.unParkCar("GA-08-A-4348");
                  boolean isVehiclePresent = parkingService.isVehiclePresent("GA-08-A-4348");
                  Assert.assertFalse(isVehiclePresent);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
}