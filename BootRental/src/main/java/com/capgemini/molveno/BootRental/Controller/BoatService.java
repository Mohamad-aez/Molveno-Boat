package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.Trip;

import java.util.ArrayList;
import java.util.List;

public class BoatService {

    public boolean checkBoatNumber (String boatNumber , List<String> allBoatsNumber){
        if (allBoatsNumber.contains(boatNumber))
            return false;
        return true;
    }

//    public List<Trip> getAllTripsContainUnuseableBoat (long id , List<Trip> trips){
//        List<Trip> allStartedAndPlannedTrips = TripService.getAllPlannedAndStartedTrips(trips);
//        List<Trip> allTripsContainUnuseableBoat = new ArrayList<>();
//
//        for (Trip trip : allStartedAndPlannedTrips) {
//            for (Boat boat : trip.getBoats()) {
//                if (boat.getId()==id){
//                    allTripsContainUnuseableBoat.add(trip);
//                }
//            }
//        }
//        return allTripsContainUnuseableBoat;
//    }

}
