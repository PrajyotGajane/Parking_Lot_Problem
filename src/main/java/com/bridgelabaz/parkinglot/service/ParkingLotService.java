package com.bridgelabaz.parkinglot.service;

import com.bridgelabaz.parkinglot.exception.ParkingLotException;
import com.bridgelabaz.parkinglot.observer.AirportSecurity;
import com.bridgelabaz.parkinglot.observer.Owner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotService {
      private int givenParkingLots;
      private int givenParkingLotSLots;
      public ArrayList<ParkingLot> numberOFParkingLots;

      public ParkingLotService(int givenParkingLots, int givenParkingLotSLots) {
            this.givenParkingLots = givenParkingLots;
            this.givenParkingLotSLots = givenParkingLotSLots;
            numberOFParkingLots = new ArrayList<>();
            IntStream.range(0, givenParkingLots)
                    .forEach(lot -> this.numberOFParkingLots.add(new ParkingLot(this.givenParkingLotSLots, new Owner(), new AirportSecurity())));
      }

      public void parkCar(String carNumber) throws ParkingLotException {
            ParkingLot lot = getLotToPark();
            lot.parkVehicle(carNumber);
      }

      private ParkingLot getLotToPark() throws ParkingLotException {
            int totalCarsInAllLots = IntStream.range(0, givenParkingLots)
                    .map(i -> this.numberOFParkingLots.get(i).getCarCount()).sum();
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

//      public String getCarLocation(String carNumber) {
//            ParkingLot parkingLot = this.parkingLots.stream()
//                    .filter(lot -> lot.isCarPresent(carNumber))
//                    .findFirst().get();
//            return String.format("Parking Lot: %d  Parking Slot: %d", parkingLots.indexOf(parkingLot) + 1,
//                    parkingLot.carLocation(carNumber));
//      }
}
