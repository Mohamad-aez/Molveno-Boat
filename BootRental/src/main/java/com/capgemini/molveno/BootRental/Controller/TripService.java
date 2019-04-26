package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.Guest;
import com.capgemini.molveno.BootRental.Model.Trip;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class TripService {

    public static List<Boat> boatsForOneTrip = new ArrayList<Boat>();
    List<Trip> allPlannedAndStartedTrips = new ArrayList<>();


    // get all useable boats
    public static List<Boat> getAllUsableBoats(List<Boat> allBoats) {
        List<Boat> allUseableBoats = new ArrayList<>();
        for (Boat boat : allBoats) {
            if (boat.isBoatAvailability())
                allUseableBoats.add(boat);
        }
        return allUseableBoats;
    }

    public static List<Boat> getAllBlockedBoats(List<Boat> allBoats) {
        List<Boat> allBlockedBoats = new ArrayList<Boat>();
        for (Boat boat : allBoats) {
            if (boat.isBoatAvailability() == false)
                allBlockedBoats.add(boat);
        }
        return allBlockedBoats;
    }


    // get all planned and started trips
    public static List<Trip> getAllPlannedAndStartedTrips(List<Trip> trips) {
        List<Trip> allPlannedAndStartedTrips = new ArrayList<>();
        for (Trip trip : trips) {
            if (!trip.getStatus().equals("Ended")) {
                allPlannedAndStartedTrips.add(trip);
            }
        }
        return allPlannedAndStartedTrips;
    }

    public static List<Trip> getAllStartedTrips(List<Trip> trips) {
        List<Trip> allStartedTrips = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.getStatus().equals("Started")) {
                allStartedTrips.add(trip);
            }
        }
        return allStartedTrips;
    }

    //get all Ended trips
    public static List<Trip> getAllEndedTrips(List<Trip> trips) {
        List<Trip> allEndedTrips = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.getStatus().equals("Ended")) {
                allEndedTrips.add(trip);
            }
        }
        return allEndedTrips;
    }

    public static List<Boat> getAllBoatsInStartedAndPlannedTrip(List<Trip> allPlannedAndStartedTrips) {
        List<Boat> allBoatsInStartedAndPlannedTrip = new ArrayList<>();


        for (Trip trip1 : allPlannedAndStartedTrips) {
            for (Boat boat1 : trip1.getBoats()) {
                allBoatsInStartedAndPlannedTrip.add(boat1);
            }
        }

        return allBoatsInStartedAndPlannedTrip;
    }

    public static List<Boat> getBoatsForStartingTrip(LocalDateTime startTime, int numberOfPerson, String boatType, List<Boat> allUseableBoats, List<Trip> allPlannedAndStartedTrips) {
        List<Boat> allAvailableBoatsToAllocate = new ArrayList<>();
        List<Boat> boatsForNewStartingTrip = new ArrayList<>();
        for (Boat boat : allUseableBoats) {
            boolean isBoatAvailable = true;
            for (Trip trip : allPlannedAndStartedTrips) {
                if (!trip.getBoats().contains(boat)) {
                    continue;
                } else {
                    if (Duration.between(trip.getStartTime(), startTime).toDays() == 0) {
                        isBoatAvailable = false;
                        continue;
                    } else {
                        continue;
                    }
                }
            }
            if (isBoatAvailable == true) {
                if (!allAvailableBoatsToAllocate.contains(boat))
                    allAvailableBoatsToAllocate.add(boat);
            }
        }
        // filtered based on boat type and sorted based on count of used
        List<Boat> allSuitableBoatsTypeForTrip = allAvailableBoatsToAllocate.stream()
                .filter(boat -> boat.getBoatType().getType().equals(boatType)).sorted(Comparator.comparing(Boat::getCountOfUsed))
                .collect(Collectors.toList());

        for (Boat boat1 : allSuitableBoatsTypeForTrip) {
            if (numberOfPerson == boat1.getNumberOfSeats()) {
                boatsForNewStartingTrip.add(boat1);
                break;
            } else if (numberOfPerson < boat1.getNumberOfSeats()) {
                boatsForNewStartingTrip.add(boat1);
                break;
            }
            boatsForNewStartingTrip.add(boat1);
            numberOfPerson = numberOfPerson - boat1.getNumberOfSeats();
            continue;
        }

        return boatsForNewStartingTrip;
    }


    // get all available boat from available plannedAndStartedTrips
    public static List<Boat> getAllAvailableBoatsToReserve(LocalDateTime start, LocalDateTime endTime, List<Boat> allUseableBoats, List<Trip> allPlannedAndStartedTrips) {
        List<Boat> allBoatsInStartedAndPlannedTrip = getAllBoatsInStartedAndPlannedTrip(allPlannedAndStartedTrips);
        List<Boat> allAvailableBoatsToReserve = new ArrayList<>();
        List<Boat> allUseableBoat2 = allUseableBoats;
        List<Trip> allTripsUsedsameBoat = new ArrayList<>();
        List<Trip> allTripsUsedsameBoatAtSameDate = new ArrayList<>();
        List<Trip> allTripsUsedsameBoatAtDifSameDate = new ArrayList<>();

        for (Boat boat : allUseableBoats) {
            boolean isBoatAvailable = true;
            for (Trip trip : allPlannedAndStartedTrips) {
//                if (!endTime.equals(null)) {
                if (!trip.getBoats().contains(boat)) {
                    continue;
                } else {
                    if (boat.getBoatType().getType().equals("Electric")) {
                        if (start.isAfter(trip.getEndTime().plusHours(2)) | (endTime.plusHours(2)).isBefore(trip.getStartTime())) {
                            continue;
                        } else {
                            isBoatAvailable = false;
                            continue;
                        }
                    } else {
                        if (start.isAfter(trip.getEndTime().plusHours(1)) | (endTime.plusHours(1)).isBefore(trip.getStartTime())) {
                            continue;
                        } else {
                            isBoatAvailable = false;
                            continue;
                        }
                    }

                }

            }
            if (isBoatAvailable == true) {
                if (!allAvailableBoatsToReserve.equals(boat)) {
                    allAvailableBoatsToReserve.add(boat);
                }
            }
        }

        return allAvailableBoatsToReserve;
    }


    // get list of boats for trip automatically
    public static List<Boat> getListOfBoatsForTripAutomatically(/*Trip trip */ int numberOfPerson, List<Boat> allAvailableBoatsToReserve, String boatType) {
        List<Boat> boatsOfTrip = new ArrayList<>();
        //filtering all available boats based on boat type requested and then sorted based on number of seats
        List<Boat> allSuitableBoatsTypeForTrip = allAvailableBoatsToReserve.stream()
                .filter(boat -> boat.getBoatType().getType().equals(boatType)).sorted(Comparator.comparing(Boat::getNumberOfSeats))
                .collect(Collectors.toList());
        List<Boat> allSuitableBoatsTypeForTrip2 = allSuitableBoatsTypeForTrip;
        int totalNumOfSeatsInAllSuitableBoatsForTrip = 0;
        for (Boat boat1 : allSuitableBoatsTypeForTrip) {
            totalNumOfSeatsInAllSuitableBoatsForTrip = totalNumOfSeatsInAllSuitableBoatsForTrip + boat1.getNumberOfSeats();
        }
        if (totalNumOfSeatsInAllSuitableBoatsForTrip >= numberOfPerson) {
            Boat biggestBoat = allSuitableBoatsTypeForTrip2.get(allSuitableBoatsTypeForTrip.size() - 1);
            Boat smallestBoat = allSuitableBoatsTypeForTrip2.get(0);
            if (numberOfPerson == biggestBoat.getNumberOfSeats()) {
                boatsOfTrip.add(biggestBoat);
                return boatsOfTrip;
            } else if (numberOfPerson > biggestBoat.getNumberOfSeats()) {
                boatsOfTrip.add(biggestBoat);
                numberOfPerson = numberOfPerson - biggestBoat.getNumberOfSeats();
                for (int i = 0; i < allSuitableBoatsTypeForTrip.size() - 1; i++) {
                    if (numberOfPerson <= allSuitableBoatsTypeForTrip.get(i).getNumberOfSeats()) {
                        boatsOfTrip.add(allSuitableBoatsTypeForTrip.get(i));
                        break;
                    }
                    boatsOfTrip.add(allSuitableBoatsTypeForTrip.get(i));
                    numberOfPerson = numberOfPerson - allSuitableBoatsTypeForTrip.get(i).getNumberOfSeats();
                    continue;
                }
                return boatsOfTrip;

            } else {
                for (Boat boat : allSuitableBoatsTypeForTrip2) {

                    if (numberOfPerson == boat.getNumberOfSeats()) {
                        boatsOfTrip.add(boat);
                        break;
                    } else if (numberOfPerson < boat.getNumberOfSeats()) {
                        boatsOfTrip.add(boat);
                        break;
                    }
                    boatsOfTrip.add(boat);
                    numberOfPerson = numberOfPerson - boat.getNumberOfSeats();
                    continue;

                }
                return boatsOfTrip;
            }
        }
        return boatsOfTrip;
    }


    //getting List of boatsOfSuitableType
    public static List<Boat> getListOfSuitableBoatsByTypeForTrip(List<Boat> allAvailableBoatsToReserve, String boatType) {
        List<Boat> boatsOfSuitableType = new ArrayList<>();
        for (Boat boat : allAvailableBoatsToReserve) {
            if (boat.getBoatType().getType().equals(boatType)) {
                boatsOfSuitableType.add(boat);
            }
        }
        return boatsOfSuitableType;
    }


    public static List<Boat> checkListOfBoatsForOneTrip(List<Boat> boatsForOneTrip, int countOfGuest) {
        int countOfSeats = 0;
        if (boatsForOneTrip != null) {
            for (Boat boat : boatsForOneTrip) {
                countOfSeats = countOfSeats + boat.getNumberOfSeats();
            }
            if (boatsForOneTrip.size() <= countOfGuest) {
                if (countOfSeats >= countOfGuest) {
                    return boatsForOneTrip;
                }//?????
            }
        } else {
            return boatsForOneTrip;
        }

        return boatsForOneTrip;
    }

    public static List<Boat> getBoatsForTrip(int countOfGuest, Boat boat) {
        List<Boat> boatsForTrip = new ArrayList<>();
        int countOfSeats = 0;

        boatsForTrip.add(boat);


        for (Boat boat1 : boatsForTrip) {
            countOfSeats = countOfSeats + boat1.getNumberOfSeats();
        }
        if (countOfSeats >= countOfGuest) {
            if (boatsForTrip.size() <= countOfGuest) {
                return boatsForTrip;
            }
        } else {

        }


        return null;
    }

    public static List<Trip> getAllTripsContainUnuseableBoat (long id , List<Trip> trips){
        List<Trip> allStartedAndPlannedTrips = getAllPlannedAndStartedTrips(trips);
        List<Trip> allTripsContainUnuseableBoat = new ArrayList<>();

        for (Trip trip : allStartedAndPlannedTrips) {
            for (Boat boat : trip.getBoats()) {
                if (boat.getId()==id){
                    allTripsContainUnuseableBoat.add(trip);
                }
            }
        }
        return allTripsContainUnuseableBoat;
    }

    /* */
    public static List<Boat> getListOfBoatsForTripManually(Trip trip, Boat boat) {
        List<Boat> boatsOfTrip = new ArrayList<>();

        int numberOfBoatsSeatsInTrip = 0;

        do {
            for (Boat boat1 : trip.getBoats()) {
                numberOfBoatsSeatsInTrip = numberOfBoatsSeatsInTrip + boat1.getNumberOfSeats();
                boatsOfTrip.add(boat);
                trip.setBoats(boatsOfTrip);
            }
        } while (trip.getNumberOfPersons() >= numberOfBoatsSeatsInTrip && trip.getBoats().size() <= trip.getNumberOfPersons())
                ;


        return boatsOfTrip;
    }

    // calculate duration
    public static long calculateTripDuration(LocalDateTime startTime, LocalDateTime endTime) {

        long durationOfTrip = Duration.between(startTime, endTime).toMinutes() / 60;

        return durationOfTrip;

    }

    // calculate the totalCost of reservation

    public static double totalCost(long durationOfTrip, int numberOfBoats, double rentalPrice) {
        // calculate duration

        // check duration if less than 60 mins make it 60 and return the cost
        if (durationOfTrip < 1)
            durationOfTrip = 1;

        return (durationOfTrip * numberOfBoats * rentalPrice);
    }

    public static boolean checkBoatsAvailability(Trip trip, List<Boat> allBoats, List<Trip> allTrips) {  // ??? check if better to use start and end time instead of trip
        List<Boat> allUseableBoats = getAllUsableBoats(allBoats);
        List<Trip> allPlannedAndStartedTrips = getAllPlannedAndStartedTrips(allTrips);
        List<Boat> boatsOfTrip = new ArrayList<>();

        try {
            if (trip.getEndTime().equals(null)) {  // will throw nullPointerException so will go to catch
            } else {
                List<Boat> allAvailableBoatsToReserve = getAllAvailableBoatsToReserve(trip.getStartTime(), trip.getEndTime(), allUseableBoats, allPlannedAndStartedTrips);
                boatsOfTrip = getListOfBoatsForTripAutomatically(trip.getNumberOfPersons(), allAvailableBoatsToReserve, trip.getBoatType());
                boatsOfTrip = boatsForOneTrip;
            }

        } catch (NullPointerException e) {
            boatsOfTrip = getBoatsForStartingTrip(trip.getStartTime(), trip.getNumberOfPersons(), trip.getBoatType(), allUseableBoats, allPlannedAndStartedTrips);
            boatsOfTrip = boatsForOneTrip;
        }

        int numOfSeatsOfBoatsOfTrip = 0;
        for (Boat boat2 : boatsOfTrip) {
            numOfSeatsOfBoatsOfTrip = numOfSeatsOfBoatsOfTrip + boat2.getNumberOfSeats();
        }
        if (numOfSeatsOfBoatsOfTrip >= trip.getNumberOfPersons()) {
            return true;
        } else
            return false;
    }


    public static List<Boat> checkBoatsAvailability2(Trip trip, List<Boat> allBoats, List<Trip> allTrips) {  // ??? check if better to use start and end time instead of trip
        List<Boat> allUseableBoats = getAllUsableBoats(allBoats);
        List<Trip> allPlannedAndStartedTrips = getAllPlannedAndStartedTrips(allTrips);
        List<Boat> boatsOfTrip = new ArrayList<>();

        try {
            if (trip.getEndTime().equals(null)) {  // will throw nullPointerException so will go to catch
            } else {
                List<Boat> allAvailableBoatsToReserve = getAllAvailableBoatsToReserve(trip.getStartTime(), trip.getEndTime(), allUseableBoats, allPlannedAndStartedTrips);
                boatsOfTrip = getListOfBoatsForTripAutomatically(trip.getNumberOfPersons(), allAvailableBoatsToReserve, trip.getBoatType());
                boatsForOneTrip = boatsOfTrip;
            }

        } catch (NullPointerException e) {
            boatsOfTrip = getBoatsForStartingTrip(trip.getStartTime(), trip.getNumberOfPersons(), trip.getBoatType(), allUseableBoats, allPlannedAndStartedTrips);
            boatsForOneTrip = boatsOfTrip;
        }

        int numOfSeatsOfBoatsOfTrip = 0;
        for (Boat boat2 : boatsOfTrip) {
            numOfSeatsOfBoatsOfTrip = numOfSeatsOfBoatsOfTrip + boat2.getNumberOfSeats();
        }
        if (numOfSeatsOfBoatsOfTrip >= trip.getNumberOfPersons()) {
            return boatsOfTrip;
        } else
            return boatsOfTrip;
    }

    // add trip
    /* check the start time and end time and update the cost of trip and the status of trip based on them*/
    public static Trip createTrip(Trip trip, List<Boat> allBoats, List<Trip> allTrips) {  // ??? check if better to use start and end time instead of trip
        LocalDateTime rightNow = LocalDateTime.now();
        Trip newTrip = new Trip();
        Guest newGuest = new Guest();
        newGuest.setName(trip.getGuest().getName());
        newGuest.setPhone(trip.getGuest().getPhone());
        newGuest.setIdType(trip.getGuest().getIdType());
        newGuest.setIdNumber(trip.getGuest().getIdNumber());
        List<Boat> allUseableBoats = getAllUsableBoats(allBoats);
        List<Trip> allPlannedAndStartedTrips = getAllPlannedAndStartedTrips(allTrips);
        List<Boat> boatsOfTrip = new ArrayList<>();

        /////

        try {
            if (!trip.getEndTime().equals(null)) {
                List<Boat> allAvailableBoatsToReserve = getAllAvailableBoatsToReserve(trip.getStartTime(), trip.getEndTime(), allUseableBoats, allPlannedAndStartedTrips);
                boatsOfTrip = getListOfBoatsForTripAutomatically(trip.getNumberOfPersons(), allAvailableBoatsToReserve, trip.getBoatType());

            }
        } catch (NullPointerException e) {
            boatsOfTrip = getBoatsForStartingTrip(trip.getStartTime(), trip.getNumberOfPersons(), trip.getBoatType(), allUseableBoats, allPlannedAndStartedTrips);
        }


        int numOfSeatsOfBoatsOfTrip = 0;
        for (Boat boat2 : boatsOfTrip) {
            numOfSeatsOfBoatsOfTrip = numOfSeatsOfBoatsOfTrip + boat2.getNumberOfSeats();
        }
        // checking if number of seats in boatsOfTrip is greater or equal the number of persons
        if (numOfSeatsOfBoatsOfTrip >= trip.getNumberOfPersons()) {
            newTrip.setStartTime(trip.getStartTime());
            try {
                if (!trip.getEndTime().equals(null)) {
                    newTrip.setEndTime(trip.getEndTime());
                }
            } catch (NullPointerException e) {
            }
            newTrip.setBoats(boatsOfTrip);
            newTrip.setNumberOfPersons(trip.getNumberOfPersons());
            newTrip.setGuest(newGuest);
            newTrip.setBoatType(trip.getBoatType());
            if (trip.getStartTime().isEqual(rightNow) | trip.getStartTime().isBefore(rightNow)) {
                try {
                    if (trip.getEndTime().equals(null)) {

                    } else if (trip.getEndTime().isAfter(rightNow)) {
                        long tripDuration = calculateTripDuration(trip.getStartTime(), trip.getEndTime());
                        newTrip.setCost(totalCost(tripDuration, newTrip.getBoats().size(), newTrip.getBoats().get(0).getBoatType().getRentalPrice()));
                        newTrip.setReservationDuration(tripDuration);
                        newTrip.setStatus("Started");


                        return newTrip;
                    }
                    long tripDuration = calculateTripDuration(trip.getStartTime(), trip.getEndTime());
                    newTrip.setCost(totalCost(tripDuration, newTrip.getBoats().size(), newTrip.getBoats().get(0).getBoatType().getRentalPrice()));
                    newTrip.setReservationDuration(tripDuration);
                    newTrip.setStatus("Ended");

                    return newTrip;
                } catch (NullPointerException e) {
                    newTrip.setCost(0);
                    newTrip.setReservationDuration(0);
                    newTrip.setStatus("Started");
                    return newTrip;
                }

            }

            long tripDuration = calculateTripDuration(trip.getStartTime(), trip.getEndTime());
            newTrip.setCost(totalCost(tripDuration, newTrip.getBoats().size(), newTrip.getBoats().get(0).getBoatType().getRentalPrice()));
            newTrip.setReservationDuration(tripDuration);
            newTrip.setStatus("Planned");

            return newTrip;
        }
        return null;
    }

    public List<Trip> checkStatusOfTrips(List<Trip> allTrips) {
        LocalDateTime rightNow = LocalDateTime.now();
        for (Trip trip : allTrips) {
            if (!(trip.getStatus().equals("Ended")) && trip.getEndTime().equals(rightNow)) {
                trip.setStatus("Ended");
                allTrips.remove(trip);

                trip.setCost(trip.getBoats().get(0).getBoatType().getRentalPrice() * trip.getReservationDuration()); //????
            }
        }
        return null;
    }


    public static String checkTripStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime rightNow = LocalDateTime.now();
        if (startTime.isEqual(rightNow) | startTime.isBefore(rightNow)) {
            try {
                if (endTime.equals(null)) {

                } else if (endTime.isAfter(rightNow)) {
                    return "Started";
                }
                return "Ended";

            } catch (NullPointerException e) {
                return "Started";
            }
        }
        return "Planned";
    }


    // get all ready boats at the same time of startTrip
//    public List<Boat> getAllReadyBoats(List<Trip> allTrips, Trip trip) {
//        for (int i = 0; i < allTrips.size(); i++) {
//            for (int j = 0; j < allTrips.get(i).getBoats().size(); j++) {
//                if (allTrips.get(i).getStatus() .equals("Ended") )
//
//                    allReservedBoats.add(allTrips.get(i).getBoats().get(j));
//            }
//        }
//        return allReservedBoats;
//    }

    //    public List<Boat> getAllReservedBoats(List<Trip> trips) {
//        List<Boat> allReservedBoats = new ArrayList<>();
//        for (Trip trip : trips) {
//            if (!trip.getStatus().equals("Ended")) {
//                for (Boat boat : trip.getBoats()) {
//                    allReservedBoats.add(boat);
//                }
//            }
//        }
//        return allReservedBoats;
//    }
//
}
