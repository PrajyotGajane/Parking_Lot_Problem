package com.bridgelabz.ParkingLot;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.Vehicle;
import com.bridgelabaz.parkinglot.observer.AirportSecurity;
import com.bridgelabaz.parkinglot.observer.Owner;
import com.bridgelabaz.parkinglot.service.ParkingLot;
import com.bridgelabaz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ParkingLotTest {

      ParkingLotSystem parkingLotSystem;
      Vehicle vehicle;
      ParkingLot parkingLot;
      ParkingLot firstParkingLot;
      ParkingLot secondParkingLot;
      Vehicle firstVehicle;
      Vehicle secondVehicle;
      Vehicle thirdVehicle;


      @Before
      public void init() {
            vehicle = new Vehicle();
            this.parkingLot = new ParkingLot(2);
            this.firstParkingLot = new ParkingLot(3);
            this.secondParkingLot = new ParkingLot(3);
            this.firstVehicle = new Vehicle();
            this.secondVehicle = new Vehicle();
            this.thirdVehicle = new Vehicle();
            this.parkingLotSystem = new ParkingLotSystem(firstParkingLot, secondParkingLot);
      }

      @Test
      public void givenVehicle_WhenParked_ShouldReturnTrue() {
            try {
                  parkingLotSystem.park(vehicle);
                  boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
                  Assert.assertTrue(isParked);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicleNull_WhenParked_ShouldReturnTrue() {
            try {
                  parkingLotSystem.park(null);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.INVALID_VEHICLE);
            }
      }

      @Test
      public void givenVehicle_WhenAlreadyParked_ShouldThrowCustomException() {
            try {
                  parkingLotSystem.park(vehicle);
                  parkingLotSystem.park(vehicle);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(e.type, ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
      }

      @Test
      public void givenVehicle_WhenUnParkedAndCheckedIfParked_ShouldThrowException() {
            try {
                  parkingLotSystem.park(vehicle);
                  parkingLotSystem.unPark(vehicle);
                  parkingLotSystem.isVehicleParked(vehicle);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT, e.type);
            }
      }

      @Test
      public void givenVehicleToUnPark_WhenNotPresent_ShouldThrowCustomException() {
            try {
                  parkingLot.unParkVehicle(vehicle);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT, e.type);
            }
      }

      @Test
      public void givenVehicleToUnPark_WhenNull_ShouldThrowCustomException() {
            try {
                  parkingLot.unParkVehicle(null);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.INVALID_VEHICLE, e.type);
            }
      }

      //UC----4
      @Test
      public void givenVehicles_WhenParkingLotIsFull_ShouldInformOwner() {
            Owner owner = new Owner();
            parkingLot.registerParkingLotObserver(owner);
            try {
                  parkingLot.parkVehicle(vehicle);
                  parkingLot.parkVehicle(firstVehicle);
                  Assert.assertEquals("Capacity is Full", owner.getParkingLotStatus());
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----4
      @Test
      public void givenVehicles_WhenParkingLotIsFull_ShouldInformSecurity() {
            AirportSecurity airportSecurityService = new AirportSecurity();
            parkingLot.registerParkingLotObserver(airportSecurityService);
            try {
                  parkingLot.parkVehicle(vehicle);
                  parkingLot.parkVehicle(firstVehicle);
                  Assert.assertEquals("Capacity is Full", airportSecurityService.getParkingLotStatus());
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----5
      @Test
      public void givenParkingLotSize_IfSpaceAvailable_ShouldInformTheOwner() {
            Owner owner = new Owner();
            parkingLot.registerParkingLotObserver(owner);
            try {
                  parkingLot.parkVehicle(vehicle);
                  parkingLot.unParkVehicle(vehicle);
                  Assert.assertEquals("Capacity Available", owner.getParkingLotStatus());
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----6

      @Test
      public void givenVehicle_WhenOwnerProvidesSlot_ShouldReturnTrue() {
            try {
                  parkingLot.parkVehicle(vehicle);
                  parkingLot.parkVehicle(2, thirdVehicle);
                  boolean isVehicleParked = parkingLot.isVehiclePresent(thirdVehicle);
                  Assert.assertTrue(isVehicleParked);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
      @Test
      public void givenVehicleAlreadyPresent_WhenOwnerProvidesSlot_ShouldThrowException() {
            try {
                  parkingLot.parkVehicle(vehicle);
                  parkingLot.parkVehicle(firstVehicle);
                  parkingLot.parkVehicle(0, vehicle);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
            }
      }

      //UC----7
      @Test
      public void givenVehicle_WhenDriverAskPositionToUnPark_ShouldReturn() {
            try {
                  parkingLot.parkVehicle(vehicle);
                  int slotNumber = parkingLot.findVehicle(vehicle);
                  Assert.assertEquals(1, slotNumber);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----8
      @Test
      public void givenVehicle_WhenParked_ShouldReturnParkingTime() {
            try {
                  LocalDateTime localTime = LocalDateTime.now().withSecond(0).withNano(0);
                  parkingLotSystem.park(vehicle);
                  LocalDateTime parkedTime = parkingLotSystem.getVehicleParkedTime(vehicle);
                  Assert.assertEquals(localTime, parkedTime);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----9
      @Test
      public void givenVehicles_WhenParkedInParkingSystem_ShouldDistributedEvenlyInParkingLots() {
            ParkingLot firstParkedLotToBeChecked = parkingLotSystem.parkingLots.get(0);
            ParkingLot secondParkedLotToBeChecked = parkingLotSystem.parkingLots.get(1);
            Vehicle thirdVehicle = new Vehicle();
            try {
                  parkingLotSystem.park(firstVehicle);
                  parkingLotSystem.park(secondVehicle);
                  parkingLotSystem.park(thirdVehicle);
                  ParkingLot firstParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(firstVehicle);
                  ParkingLot secondParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(secondVehicle);
                  ParkingLot thirdVehicleParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(thirdVehicle);
                  Assert.assertEquals(firstParkedLotToBeChecked, firstParkedLot);
                  Assert.assertEquals(secondParkedLotToBeChecked, secondParkedLot);
                  Assert.assertEquals(firstParkedLotToBeChecked, thirdVehicleParkedLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenParkedVehicle_WhenFound_ShouldReturnSlotInParkingLot() {
            ParkingLot lotPositionToBeChecked = parkingLotSystem.parkingLots.get(1);
            try {
                  parkingLotSystem.park(vehicle);
                  parkingLotSystem.park(firstVehicle);
                  parkingLotSystem.park(secondVehicle);
                  ParkingLot position = parkingLotSystem.getParkingLotOfParkedVehicle(firstVehicle);
                  Assert.assertEquals(lotPositionToBeChecked, position);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenNotFoundInParkingSystem_ShouldThrowException() {
            try {
                  parkingLotSystem.getParkingLotOfParkedVehicle(vehicle);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT, e.type);
            }
      }

      @Test
      public void givenVehicle_WhenParkedAndUnParkedInParkingSystem_ShouldDistributeEvenlyAfterUnPark() {
            ParkingLot firstParkedLotToBeChecked = parkingLotSystem.parkingLots.get(0);
            ParkingLot secondParkedLotToBeChecked = parkingLotSystem.parkingLots.get(1);
            Vehicle thirdVehicle = new Vehicle();
            try {
                  parkingLotSystem.park(firstVehicle);
                  parkingLotSystem.unPark(firstVehicle);
                  parkingLotSystem.park(secondVehicle);
                  parkingLotSystem.park(thirdVehicle);
                  ParkingLot secondParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(secondVehicle);
                  ParkingLot thirdVehicleParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(thirdVehicle);
                  Assert.assertEquals(firstParkedLotToBeChecked, secondParkedLot);
                  Assert.assertEquals(secondParkedLotToBeChecked, thirdVehicleParkedLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenParkedThroughParkingSystem_ShouldReturnParkingSpotFromParkingLot() {
            try {
                  parkingLotSystem.park(firstVehicle);
                  parkingLotSystem.park(secondVehicle);
                  int slotPositionInLot = parkingLotSystem.getVehicleSpot(firstVehicle);
                  Assert.assertEquals(1,slotPositionInLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
}