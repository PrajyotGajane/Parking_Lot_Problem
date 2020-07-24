package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.observer.AirportSecurity;
import com.bridgelabaz.parkinglot.observer.Owner;
import com.bridgelabaz.parkinglot.utility.Location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotService {
      private final int givenParkingLots;
      private final int givenParkingLotSLots;
      public ArrayList<ParkingLot> numberOFParkingLots;

      public ParkingLotService(int givenParkingLots, int givenParkingLotSLots) {
            this.givenParkingLots = givenParkingLots;
            this.givenParkingLotSLots = givenParkingLotSLots;
            numberOFParkingLots = new ArrayList<>();
            IntStream.range(0, givenParkingLots)
                    .forEach(lot -> this.numberOFParkingLots.add(new ParkingLot(this.givenParkingLotSLots, new Owner(), new AirportSecurity())));
      }

      public void parkCar(String vehicleNumber) throws ParkingLotException {
            boolean isVehiclePresent;
            for (ParkingLot lots : numberOFParkingLots) {
                  isVehiclePresent = lots.isVehiclePresent(vehicleNumber);
                  if (isVehiclePresent)
                        throw new ParkingLotException("Already Parked",
                                ParkingLotException.ExceptionType.ALREADY_PARKED);
            }
            ParkingLot lot = getLotToPark();
            lot.parkVehicle(vehicleNumber);
      }

      public void unParkCar(String vehicleNumber) throws ParkingLotException {
            ParkingLot lot = getLotToPark();
            lot.unParkVehicle(vehicleNumber);
      }

      private ParkingLot getLotToPark() throws ParkingLotException {
            int totalCarsInAllLots = IntStream.range(0, givenParkingLots)
                    .map(slot -> this.numberOFParkingLots.get(slot).getCarCount()).sum();
            if (totalCarsInAllLots >= (givenParkingLots * givenParkingLotSLots))
                  throw new ParkingLotException("Parking Lot Full", ParkingLotException.ExceptionType.PARKING_LOT_FULL);
            ParkingLot parkingLot;
            List<ParkingLot> selectLot = new ArrayList<>(this.numberOFParkingLots);
            selectLot.sort(Comparator.comparing(ParkingLot::getCarCount));
            parkingLot = selectLot.get(0);
            return parkingLot;
      }

      public boolean isVehiclePresent(String carNumber) {
            return this.numberOFParkingLots.stream().anyMatch(lot -> lot.isVehiclePresent(carNumber));
      }

      public ParkingLot getVehicleLocation(String vehicleNumber) throws ParkingLotException {
            return this.numberOFParkingLots.stream()
                    .filter(lot -> lot.isVehiclePresent(vehicleNumber))
                    .findFirst()
                    .orElseThrow(() -> new ParkingLotException(" Vehicle not present in lot",
                            ParkingLotException.ExceptionType.VEHICLE_NOT_PRESENT));
      }

      public Location getLocation(String vehicleNumber) throws ParkingLotException {
            ParkingLot parkingLot = getVehicleLocation(vehicleNumber);
            Location location = new Location();
            location.setParkingLot(parkingLot);
            location.setSlotNumber(parkingLot.getVehicleSpot(vehicleNumber));
            return location;
      }
}
