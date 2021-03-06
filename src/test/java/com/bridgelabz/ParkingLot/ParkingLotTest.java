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
import java.util.*;

public class ParkingLotTest {

      ParkingLotSystem parkingLotSystem;
      VehicleDetails vehicle;
      ParkingLot parkingLot;
      ParkingLot firstParkingLot;
      ParkingLot secondParkingLot;
      Vehicle firstVehicle;
      Vehicle secondVehicle;
      Vehicle thirdVehicle;
      Vehicle forthVehicle;
      Vehicle fifthVehicle;
      Vehicle vehicleDetail;
      ParkingLot thirdParkingLot;
      VehicleDetails firstVehicleDetail;
      VehicleDetails secondVehicleDetail;
      VehicleDetails thirdVehicleDetail;
      VehicleDetails forthVehicleDetail;
      VehicleDetails fifthVehicleDetail;

      @Before
      public void init() {
            this.parkingLot = new ParkingLot(2);
            this.firstParkingLot = new ParkingLot(3);
            this.secondParkingLot = new ParkingLot(3);
            this.thirdParkingLot = new ParkingLot(3);
            this.firstVehicle = new Vehicle("GA-08-A-1212", VehicleBrand.TOYOTA, VehicleColor.WHITE);
            this.secondVehicle = new Vehicle("GA-08-A-2245", VehicleBrand.TOYOTA, VehicleColor.BLUE);
            this.thirdVehicle = new Vehicle("GA-08-A-2005", VehicleBrand.TOYOTA, VehicleColor.WHITE);
            this.forthVehicle = new Vehicle("GA-08-A-2345", VehicleBrand.TOYOTA, VehicleColor.WHITE);
            this.fifthVehicle = new Vehicle("GA-08-A-1245", VehicleBrand.TOYOTA, VehicleColor.WHITE);
            this.vehicle = new VehicleDetails(vehicleDetail, DriverType.NORMAL, VehicleSize.SMALL, "Parag");
            this.vehicleDetail = new Vehicle("GA-09-P-2365", VehicleBrand.TOYOTA, VehicleColor.BLUE);
            this.firstVehicleDetail = new VehicleDetails(firstVehicle, DriverType.NORMAL, VehicleSize.SMALL, "Parag");
            this.secondVehicleDetail = new VehicleDetails(secondVehicle, DriverType.HANDICAPPED, VehicleSize.SMALL, "Rishab");
            this.thirdVehicleDetail = new VehicleDetails(thirdVehicle, DriverType.NORMAL, VehicleSize.LARGE, "Roshan");
            this.forthVehicleDetail = new VehicleDetails(forthVehicle, DriverType.HANDICAPPED, VehicleSize.LARGE, "Roshan");
            this.fifthVehicleDetail = new VehicleDetails(fifthVehicle, DriverType.NORMAL, VehicleSize.LARGE, "Rishab");
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
                  parkingLotSystem.park(thirdVehicleDetail);
                  ParkingLot parkingLot = parkingLotSystem.getParkingLotOfParkedVehicle(thirdVehicleDetail);
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
                  Assert.assertEquals(secondParkingLot, parkingLotWithMostFreeSpace);
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

      //UC----12
      @Test
      public void givenVehiclesColour_WhenFound_ShouldReturnListOfWhitVehicleWithSlotNumber() {
            List<Integer> firstLotExpectedVehicles = new ArrayList<>();
            firstLotExpectedVehicles.add(1);
            firstLotExpectedVehicles.add(3);
            List<Integer> secondLotExpectedVehicles = new ArrayList<>();
            secondLotExpectedVehicles.add(1);
            Map<ParkingLot, List<Integer>> expectedVehiclesInParkingLots = new HashMap<ParkingLot, List<Integer>>(){{
                  put(firstParkingLot, firstLotExpectedVehicles);
                  put(secondParkingLot,secondLotExpectedVehicles);
            }};
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);//Handicapped Vehicle
                  parkingLotSystem.park(thirdVehicleDetail);//Large
                  parkingLotSystem.park(forthVehicleDetail);
                  Map<ParkingLot, List<Integer>> slotNumberListOfVehiclesByColor = parkingLotSystem.getLotAndSlotListOfVehiclesByColor(VehicleColor.WHITE);
                  Assert.assertEquals(expectedVehiclesInParkingLots, slotNumberListOfVehiclesByColor);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----13
      @Test
      public void givenBlueToyotaVehicle_WhenFound_ShouldReturnSlotDetails() {
            Vehicle vehicleToyota = new Vehicle("GA-08-A-2456", VehicleBrand.TOYOTA, VehicleColor.BLUE);
            VehicleDetails vehicleDetails = new VehicleDetails(vehicleToyota, DriverType.NORMAL, VehicleSize.LARGE, "Harsh");
            List<String> secondLotInformation = new ArrayList<>();
            secondLotInformation.add("GA-08-A-2456 Harsh");
            Map<ParkingLot, List<String>> expectedVehiclesList = new HashMap<ParkingLot, List<String>>(){{
                  put(secondParkingLot, secondLotInformation);
            }};
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(vehicleDetails);
                  Map<ParkingLot, List<String>> slotNumbersByCompanyAndColor =
                          parkingLotSystem.getVehicleBrandAndColor(VehicleBrand.TOYOTA, VehicleColor.BLUE);
                  Assert.assertEquals(expectedVehiclesList, slotNumbersByCompanyAndColor);
            } catch (ParkingLotException e) {
                  Assert.fail();
                  e.printStackTrace();
            }
      }

      @Test
      public void givenBlueToyotaVehicle_WhenNotFound_ShouldThrowException() {
            Vehicle vehicleToyota = new Vehicle("GA-08-A-2456", VehicleBrand.BMW, VehicleColor.BLUE);
            Vehicle vehicleToyota2 = new Vehicle("GA-08-A-3456", VehicleBrand.BMW, VehicleColor.BLUE);
            VehicleDetails vehicleDetails = new VehicleDetails(vehicleToyota, DriverType.NORMAL, VehicleSize.LARGE, "Harsh");
            VehicleDetails vehicleDetails2 = new VehicleDetails(vehicleToyota2, DriverType.NORMAL, VehicleSize.LARGE, "Harsh");
            List<String> secondLotInformation = new ArrayList<>();
            secondLotInformation.add("GA-08-A-2456 Harsh");
            Map<ParkingLot, List<String>> expectedVehiclesList = new HashMap<ParkingLot, List<String>>(){{
                  put(secondParkingLot, secondLotInformation);
            }};
            try {
                  parkingLotSystem.park(vehicleDetails2);
                  parkingLotSystem.park(vehicleDetails);
                  Map<ParkingLot, List<String>> slotNumbersByCompanyAndColor =
                          parkingLotSystem.getVehicleBrandAndColor(VehicleBrand.TOYOTA, VehicleColor.BLUE);
                  Assert.assertEquals(expectedVehiclesList, slotNumbersByCompanyAndColor);
            } catch (ParkingLotException e) {
                  Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT, e.type);

            }
      }

      //UC----14
      @Test
      public void givenBMWVehicle_WhenFound_ShouldReturnListOfSlotNumber() {
            Vehicle vehicle = new Vehicle("GA-08-A-2456", VehicleBrand.BMW, VehicleColor.WHITE);
            VehicleDetails vehicleDetails = new VehicleDetails(vehicle, DriverType.NORMAL, VehicleSize.LARGE, "Harsh");
            List<Integer> expectedVehicleToBeInFirstLot = new ArrayList<>();
            expectedVehicleToBeInFirstLot.add(1);
            List<Integer> expectedVehicleToBeInSecondLot = new ArrayList<>();
            expectedVehicleToBeInSecondLot.add(1);
            Map<ParkingLot, List<Integer>> expectedBMWInParkingLot = new HashMap<ParkingLot, List<Integer>>() {{
                  put(firstParkingLot, expectedVehicleToBeInFirstLot);
                  put(secondParkingLot, expectedVehicleToBeInSecondLot);
            }};
            try {
                  parkingLotSystem.park(secondVehicleDetail);
                  parkingLotSystem.park(vehicleDetails);
                  Map<ParkingLot, List<Integer>> slotNumbersByCompany =
                          parkingLotSystem.getSlotNumbersOfVehiclesByBrand(VehicleBrand.BMW);
                  Assert.assertEquals(expectedBMWInParkingLot, slotNumbersByCompany);
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

      //UC----16
      @Test
      public void givenVehicles_WhenFoundHandicappedDriversWithSmallVehicleSize_ShouldReturnListOfSlotNumber() {
            VehicleDetails firstVehicleDetail = new VehicleDetails(firstVehicle, DriverType.HANDICAPPED,
                    VehicleSize.SMALL, "Harsh");
            VehicleDetails secondVehicleDetail = new VehicleDetails(secondVehicle, DriverType.HANDICAPPED,
                    VehicleSize.SMALL, "Prajyot");
            List<String> firstLotInformation = new ArrayList<>(Arrays.asList(
                    "Slot: 1 Brand: TOYOTA Color:WHITE Size: SMALL Parking Attendant Name: Harsh",
                    "Slot: 2 Brand: BMW Color:BLUE Size: SMALL Parking Attendant Name: Prajyot"));
            Map<ParkingLot, List<String>> expectedVehiclesList = new HashMap<ParkingLot, List<String>>(){{
                  put(firstParkingLot, firstLotInformation);
            }};
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  Map<ParkingLot, List<String>> slotNumberBySizeAndDriverType =
                          parkingLotSystem.getSlotNumbersBySizeAndDriverType(DriverType.HANDICAPPED, VehicleSize.SMALL);
                  Assert.assertEquals(expectedVehiclesList, slotNumberBySizeAndDriverType);
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }

      //UC----17
      @Test
      public void givenAllParkedVehicles_WhenFound_ShouldReturnListOfSlotNumber() {
            ArrayList<Integer> firstLot = new ArrayList<>(Arrays.asList(1, 2, 3));
            ArrayList<Integer> secondLot = new ArrayList<>(Collections.singletonList(1));
            try {
                  parkingLotSystem.park(firstVehicleDetail);
                  parkingLotSystem.park(secondVehicleDetail);
                  parkingLotSystem.park(thirdVehicleDetail);
                  parkingLotSystem.park(forthVehicleDetail);
                  Map<ParkingLot, List<Integer>> slotNumberList =
                          parkingLotSystem.getAllVehiclesParkedInParkingAllLot();
                  Assert.assertEquals(firstLot, slotNumberList.get(firstParkingLot));
                  Assert.assertEquals(secondLot, slotNumberList.get(secondParkingLot));
            } catch (ParkingLotException e) {
                  e.printStackTrace();
            }
      }
}