package com.capgemini.molveno.BootRental.API;


import com.capgemini.molveno.BootRental.Controller.BoatRepository;
import com.capgemini.molveno.BootRental.Controller.GuestRepository;
import com.capgemini.molveno.BootRental.Controller.TripRepository;
import com.capgemini.molveno.BootRental.Controller.TripService;
import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.capgemini.molveno.BootRental.Controller.TripService.*;

@RestController
@CrossOrigin(origins = "*")

public class TripEndPoint {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private BoatRepository boatRepository;

    @RequestMapping(value = "/get-all-trips")
    public List<Trip> getAllTrips() {

        return tripRepository.findAll();
    }

    @RequestMapping(value = "/get-all-startedTrips")
    public List<Trip> getStartedTrips() {
        List<Trip> allTrips = tripRepository.findAll();
        List<Trip> allStartedTrip = getAllStartedTrips(allTrips);
        return allStartedTrip;
    }

    @RequestMapping(value = "/get-all-endedTrips")
    public List<Trip> getEndedTrips() {
        List<Trip> allTrips = tripRepository.findAll();
        List<Trip> allEndedTrips = getAllEndedTrips(allTrips);
        return allEndedTrips;
    }

    @RequestMapping(value = "/get-trip", method = RequestMethod.GET, consumes = "application/json")
    public Trip getTrip(@RequestBody long id) {
        Trip trip1 = tripRepository.findById(id);
        return trip1;
    }

    @RequestMapping(value = "/get-one-trip/{id}", method = RequestMethod.GET)
    public Trip getOneTripById(@PathVariable long id) {
        return tripRepository.findById(id);
    }

    @RequestMapping(value = "/get-allTrips-contain-unuseableBoat/{id}", method = RequestMethod.GET)
    public List<Trip> getAllTripsContainUnuseableBoat(@PathVariable long id) {
        List<Trip> allTrips = getAllTrips();
        List<Trip> allTripsContainUnuseableBoat = TripService.getAllTripsContainUnuseableBoat(id , allTrips);
        try {
            if (!allTripsContainUnuseableBoat.equals(null))
                return allTripsContainUnuseableBoat;

        }catch (NullPointerException e){
            return null;
        }
        return allTripsContainUnuseableBoat;
    }

    @RequestMapping(value = "/add-trip", method = RequestMethod.POST, consumes = "application/json")
    public void addTrip(@RequestBody Trip trip) {
        guestRepository.save(trip.getGuest());
        tripRepository.save(trip);

    }
    @RequestMapping(value = "/delete-trip", method = RequestMethod.POST)
    public void deleteTrip(@RequestBody Trip trip) {
        tripRepository.deleteById(trip.getId());
    }

    @RequestMapping(value = "/get-all-boats-inPlannedandStartedTrip")
    public List<Boat> getAllBoatsInPlanned() {
//        List<Boat> allUseableBoats = getAllUsableBoats(boatRepository.findAll());
        List<Trip> allPlannedAndStartedTrips = getAllPlannedAndStartedTrips(tripRepository.findAll());
        List<Boat> allBoatsInStartedAndPlannedTrip = getAllBoatsInStartedAndPlannedTrip(allPlannedAndStartedTrips);
        return allBoatsInStartedAndPlannedTrip;
    }

    @RequestMapping(value = "/get-availableBoatsToReserve", method = RequestMethod.GET, consumes = "application/json")
    public List<Boat> getAllAvailableBoatsToReserveForTrip(@RequestBody Trip trip) {

        List<Boat> allUseableBoats = getAllUsableBoats(boatRepository.findAll());
        List<Trip> allPlannedAndStartedTrips = getAllPlannedAndStartedTrips(tripRepository.findAll());
        List<Boat> allAvailableBoatsToReserve = getAllAvailableBoatsToReserve(trip.getStartTime(), trip.getEndTime(), allUseableBoats, allPlannedAndStartedTrips);
//        int numberOfPeople = trip.getNumberOfPersons();
//        List<Boat> newBoatList = new ArrayList<>();
//        List<Boat> boatsForTrip = getListOfBoatsForTripAutomatically(trip.getNumberOfPersons(),allAvailableBoatsToReserve,trip.getBoats().get(0).getBoatType().getType());
        return allAvailableBoatsToReserve;
    }


    @RequestMapping(value = "/get-boats-fortrip", method = RequestMethod.GET, consumes = "application/json")
    public List<Boat> getAllBoatsForTrip(@RequestBody Trip trip) {
        List<Boat> boatsForTrip = getListOfBoatsForTripAutomatically(trip.getNumberOfPersons(), getAllAvailableBoatsToReserveForTrip(trip), trip.getBoats().get(0).getBoatType().getType());

        return boatsForTrip;
    }

    @RequestMapping(value = "/check-boats-availability-for-trip", method = RequestMethod.POST, consumes = "application/json")
    public boolean checkBoatsAvailabilityForTrip(@RequestBody Trip trip) {

        List<Boat> allBoats = boatRepository.findAll();
        List<Trip> allTrips = tripRepository.findAll();
        boolean checkAvailability = checkBoatsAvailability(trip, allBoats, allTrips);
        return checkAvailability;
    }

    @RequestMapping(value = "/check-boats-availability-for-trip2", method = RequestMethod.POST, consumes = "application/json")
    public List<Boat> checkBoatsAvailabilityForTrip2(@RequestBody Trip trip) {
        trip.setStartTime(LocalDateTime.now());
        List<Boat> allBoats = boatRepository.findAll();
        List<Trip> allTrips = tripRepository.findAll();
        List<Boat> boats = checkBoatsAvailability2(trip, allBoats, allTrips);
        System.out.println(trip.getStartTime());
        return boats;
    }


    @RequestMapping(value = "/edit-started-trip", method = RequestMethod.POST, consumes = "application/json")
    public void editTrip(@RequestBody Trip trip) {
        Trip trip1 = tripRepository.findById(trip.getId());
        try {
            if (trip.getEndTime().equals(null)) {

            }
        } catch (NullPointerException e) {

            trip1.setEndTime(LocalDateTime.now());
            long tripDuration = calculateTripDuration(trip1.getStartTime(), trip1.getEndTime());
            trip1.setReservationDuration(tripDuration);
            trip1.setCost(totalCost(tripDuration, trip1.getBoats().size(), trip1.getBoats().get(0).getBoatType().getRentalPrice()));
            String status = checkTripStatus(trip1.getStartTime(), trip1.getEndTime());
            trip1.setStatus(status);
            tripRepository.save(trip1);

        }


    }

    @RequestMapping(value = "/edit-planned-trip", method = RequestMethod.POST, consumes = "application/json")
    public void editPlannedTrip(@RequestBody Trip trip) {
        Trip trip1 = tripRepository.findById(trip.getId());
        try {
            if (!trip.getEndTime().equals(null)) {
                trip1.setEndTime(trip.getEndTime());
                trip1.setStartTime(trip.getStartTime());
                long tripDuration = calculateTripDuration(trip1.getStartTime(), trip1.getEndTime());
                trip1.setReservationDuration(tripDuration);
                trip1.setCost(totalCost(tripDuration, trip1.getBoats().size(), trip1.getBoats().get(0).getBoatType().getRentalPrice()));
                String status = checkTripStatus(trip1.getStartTime(), trip1.getEndTime());
                trip1.setStatus(status);
                tripRepository.save(trip1);
            }
        } catch (NullPointerException e) {
            trip1.setStartTime(trip.getStartTime());
            trip1.setEndTime(trip.getEndTime());
            trip1.setReservationDuration(0);
            trip1.setCost(0);
            if (trip1.getStartTime().isBefore(LocalDateTime.now()) | trip1.getStartTime().equals(LocalDateTime.now())) {
                trip1.setStatus("Started");
            } else {
                trip1.setStatus("Planned");
            }
            tripRepository.save(trip1);
        }


    }

    @RequestMapping(value = "/edit-cost-ended-trip", method = RequestMethod.POST, consumes = "application/json")
    public void editcostForEndedTrip(@RequestBody Trip trip) {
        Trip trip1 = tripRepository.findById(trip.getId());
        trip1.setCost(trip.getCost());
        tripRepository.save(trip1);
    }


    @RequestMapping(value = "/addingtrip", method = RequestMethod.POST, consumes = "application/json")
    public Trip addingTrip(@RequestBody Trip trip) {
        List<Boat> allBoats = boatRepository.findAll();
        List<Trip> allTrips = tripRepository.findAll();
        try {
            if (trip.getStartTime().equals(null)) {

            }
        } catch (NullPointerException e3) {
            trip.setStartTime(LocalDateTime.now());

        }

        Trip trip1 = createTrip(trip, boatRepository.findAll(), tripRepository.findAll());
        List<Boat> newList = new ArrayList<>();

        try {
            if (!trip1.equals(null)) {
                for (Boat boat2 : trip1.getBoats()) {
                    boat2.setCountOfUsed(boat2.getCountOfUsed() + 1);
                }
                guestRepository.save(trip1.getGuest());
                tripRepository.save(trip1);
                return trip1;
            }
        } catch (NullPointerException e) {
            return null;
        }

        return null;

    }

}
