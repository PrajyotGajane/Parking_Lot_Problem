package com.bridgelabz.ParkingLot;

import com.bridgelabaz.parkinglot.enums.DriverType;
import com.bridgelabaz.parkinglot.enums.VehicleBrand;
import com.bridgelabaz.parkinglot.enums.VehicleColor;
import com.bridgelabaz.parkinglot.enums.VehicleSize;
import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.models.Vehicle;
import com.bridgelabaz.parkinglot.models.VehicleDetails;
import com.bridgelabaz.parkinglot.observer.AirportSecurity;
import com.bridgelabaz.parkinglot.observer.Owner;
import com.bridgelabaz.parkinglot.service.ParkingLot;
import com.bridgelabaz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotTest {

      ParkingLotSystem parkingLotSystem;
      VehicleDetails vehicle;
      ParkingLot parkingLot;
      ParkingLot firstParkingLot;
      ParkingLot secondParkingLot;
      Vehicle firstVehicle;
      Vehicle secondVehicle;
      Vehicle thirdVehicle;
      Vehicle vehicleDetail;
      ParkingLot thirdParkingLot;
      VehicleDetails firstVehicleDetail;
      VehicleDetails secondVehicleDetail;
      VehicleDetails thirdVehicleDetail;

      @Before
      public void init() {
            this.parkingLot = new ParkingLot(2);
            this.firstParkingLot = new ParkingLot(3);
            this.secondParkingLot = new ParkingLot(3);
            this.thirdParkingLot = new ParkingLot(3);
            this.firstVehicle = new Vehicle("GA-08-A-1212", VehicleBrand.TOYOTA, VehicleColor.WHITE);
            this.secondVehicle = new Vehicle("GA-08-A-2345", VehicleBrand.BMW, VehicleColor.WHITE);
            this.thirdVehicle = new Vehicle("GA-08-A-2345", VehicleBrand.TOYOTA, VehicleColor.WHITE);
            this.vehicle = new VehicleDetails(vehicleDetail, DriverType.NORMAL,VehicleSize.SMALL,"Parag");
            this.vehicleDetail = new Vehicle("GA-09-P-2365", VehicleBrand.TOYOTA, VehicleColor.BLUE);
            this.firstVehicleDetail = new VehicleDetails(firstVehicle, DriverType.NORMAL,VehicleSize.SMALL,"Parag");
            this.secondVehicleDetail = new VehicleDetails(secondVehicle, DriverType.NORMAL,VehicleSize.SMALL,"Rishab");
            this.thirdVehicleDetail = new VehicleDetails(thirdVehicle, DriverType.NORMAL, VehicleSize.LARGE, "Roshan");
            this.parkingLotSystem = new ParkingLotSystem(firstParkingLot, secondParkingLot, thirdParkingLot);
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
            VehicleDetails vehicleDetails = new VehicleDetails(null, DriverType.NORMAL, VehicleSize.SMALL, "Prajyot");
            try {
                  parkingLotSystem.park(vehicleDetails);
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
                  parkingLot.parkVehicle(firstVehicleDetail);
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
                  parkingLot.parkVehicle(firstVehicleDetail);
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
                  parkingLot.parkVehicle(2, secondVehicleDetail);
                  boolean isVehicleParked = parkingLot.isVehiclePresent(secondVehicleDetail);
                  Assert.assertTrue(isVehicleParked);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicleAlreadyPresent_WhenOwnerProvidesSlot_ShouldThrowException() {
            try {
                  parkingLot.parkVehicle(vehicle);
                  parkingLot.parkVehicle(firstVehicleDetail);
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
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  ParkingLot parkingLot = parkingLotSystem.getParkingLotOfParkedVehicle(firstVehicleDetail);
                  Assert.assertEquals(firstParkingLot, parkingLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenSecondVehicles_WhenParkedInParkingSystem_ShouldDistributedEvenlyInParkingLots() {
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  ParkingLot parkingLot = parkingLotSystem.getParkingLotOfParkedVehicle(secondVehicleDetail);
                  Assert.assertEquals(secondParkingLot, parkingLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenParkedVehicle_WhenFound_ShouldReturnSlotInParkingLot() {
            ParkingLot lotPositionToBeChecked = parkingLotSystem.parkingLots.get(1);
            try {
                  parkingLotSystem.park(vehicle);
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  ParkingLot position = parkingLotSystem.getParkingLotOfParkedVehicle(firstVehicleDetail);
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
            VehicleDetails thirdVehicle = new VehicleDetails(vehicleDetail, DriverType.NORMAL, VehicleSize.SMALL, "Parag");
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.unPark(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  parkingLotSystem.park(thirdVehicle);
                  ParkingLot secondParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(secondVehicleDetail);
                  ParkingLot thirdVehicleParkedLot = parkingLotSystem.getParkingLotOfParkedVehicle(thirdVehicle);
                  Assert.assertEquals(firstParkingLot, secondParkedLot);
                  Assert.assertEquals(secondParkingLot, thirdVehicleParkedLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehicle_WhenParkedThroughParkingSystem_ShouldReturnParkingSpotFromParkingLot() {
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  int slotPositionInLot = parkingLotSystem.getVehicleSpot(firstVehicleDetail);
                  Assert.assertEquals(1, slotPositionInLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenAVehicleWithHandicappedDriver_IfFirstLotHasEmptySlotsTheVehicle_ShouldParkedInTheFirstParkingLot() {
            try {
                  VehicleDetails vehicleWithHandicapDriver = new VehicleDetails(vehicleDetail, DriverType.HANDICAPPED, VehicleSize.SMALL, "Harsh");
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(vehicleWithHandicapDriver);
                  ParkingLot presentLot = parkingLotSystem.getParkingLotOfParkedVehicle(vehicleWithHandicapDriver);
                  Assert.assertEquals(firstParkingLot, presentLot);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenLargeVehicle_ShouldGetParkedInTheLotWithMostEmptySlots() {
            try {
                  VehicleDetails largeVehicle = new VehicleDetails(vehicleDetail, DriverType.NORMAL, VehicleSize.LARGE, "John");
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  parkingLotSystem.park(largeVehicle);
                  ParkingLot parkingLotWithMostFreeSpace = parkingLotSystem.getParkingLotOfParkedVehicle(largeVehicle);
                  Assert.assertEquals(thirdParkingLot, parkingLotWithMostFreeSpace);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenLargeVehicleWithHandicappedDriver_ShouldGetParkedInTheLotWithMostEmptySlots() {
            try {
                  VehicleDetails largeVehicle = new VehicleDetails(vehicleDetail, DriverType.HANDICAPPED, VehicleSize.SMALL, "Henry");
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(largeVehicle);
                  ParkingLot parkingLotWithMostFreeSpace = parkingLotSystem.getParkingLotOfParkedVehicle(largeVehicle);
                  Assert.assertEquals(firstParkingLot, parkingLotWithMostFreeSpace);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenVehiclesColour_WhenFound_ShouldReturnListOfWhitVehicleWithSlotNumber() {
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  Map<ParkingLot, List<Integer>> slotNumberListOfVehiclesByColor = parkingLotSystem.getLotAndSlotListOfVehiclesByColor(VehicleColor.WHITE);
                  Assert.assertEquals(1, slotNumberListOfVehiclesByColor.get(firstParkingLot).get(0).intValue());
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      @Test
      public void givenBlueToyotaVehicle_WhenFound_ShouldReturnSlotDetails() {
            Vehicle vehicle = new Vehicle("GA-08-A-2456", VehicleBrand.TOYOTA, VehicleColor.BLUE);
            VehicleDetails vehicleDetails = new VehicleDetails(vehicle, DriverType.NORMAL, VehicleSize.LARGE, "Harsh");
            try {

                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(vehicleDetails);
                  Map<ParkingLot, List<String>> slotNumbersByCompanyAndColor =
                          parkingLotSystem.getVehicleNumberAndAttendantName(VehicleBrand.TOYOTA, VehicleColor.BLUE);
                  Assert.assertEquals("GA-08-A-2456 Harsh", slotNumbersByCompanyAndColor.get(secondParkingLot).get(0));
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
      //UC----14
      @Test
      public void givenBMWVehicle_WhenFound_ShouldReturnListOfSlotNumber() {
            Vehicle vehicle = new Vehicle("MH04 AB 9999", VehicleBrand.BMW, VehicleColor.WHITE);
            VehicleDetails vehicleDetails = new VehicleDetails(vehicle, DriverType.NORMAL, VehicleSize.LARGE, "Harsh");
            try {
                  parkingLotSystem.park(secondVehicleDetail);
                  parkingLotSystem.park(vehicleDetails);
                  Map<ParkingLot, List<Integer>> slotNumbersByCompany =
                          parkingLotSystem.getSlotNumbersOfVehiclesByCompany(VehicleBrand.BMW);
                  Assert.assertEquals(2, slotNumbersByCompany.size());
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
      //UC--15
      @Test
      public void givenVehicles_WhenParked_ShouldReturnListOfParkedInLast30Minutes() {
            List<Integer> expectedVehicleToBeInFirstLot = new ArrayList<>();
            expectedVehicleToBeInFirstLot.add(1);
            List<Integer> expectedVehicleToBeInSecondLot = new ArrayList<>();
            expectedVehicleToBeInSecondLot.add(1);
            Map<ParkingLot, List<Integer>> expectedBMWInParkingLot = new HashMap<ParkingLot, List<Integer>>() {{
                  put(firstParkingLot, expectedVehicleToBeInFirstLot);
                  put(secondParkingLot, expectedVehicleToBeInSecondLot);
            }};
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(thirdVehicleDetail);
                  Map<ParkingLot, List<Integer>> slotNumbersVehiclesByTime =
                          parkingLotSystem.getVehiclesParkedTime(30);
                  Assert.assertEquals(expectedBMWInParkingLot, slotNumbersVehiclesByTime);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
}