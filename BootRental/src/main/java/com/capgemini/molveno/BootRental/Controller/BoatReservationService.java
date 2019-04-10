package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.API.BoatEndPoint;
import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.BoatReservation;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BoatReservationService {

    // defining new arrays for boats
    List<Boat> allBoats,allReservedBoats ,allAvailableBoats = new ArrayList<Boat>();

    // get all reserved boats
    public List<Boat> getAllReservedBoats (List<BoatReservation> allBoatsReservation){
        for(int i=0;i<allBoatsReservation.size();i++) {
            for (int j = 0; j < allBoatsReservation.get(i).getBoats().size(); j++) {
                allReservedBoats.add( allBoatsReservation.get(i).getBoats().get(j));
            }
        }
        return allReservedBoats;
    }

    // define new object from BoatEndPoint
    BoatEndPoint boatEndPoint = new BoatEndPoint();

    // get all boats in system
    public List<Boat> getAllBoats (){
        allBoats = boatEndPoint.getAllBoats();
        return allBoats;
    }


    // get all available boats in system
    public List<Boat> getAllAvailableBoats(List<Boat> allReservedBoats , List<Boat> allBoats){
        for(int i=0;i<allBoats.size();i++) {
            for (int j = 0; j < allReservedBoats.size(); j++) {
                if (allBoats.get(i).getId()!= allReservedBoats.get(j).getId()){
                    allAvailableBoats.add(allBoats.get(i));
                }
            }
        }
        return allAvailableBoats;
    }

    // calculate the totalCost of reservation
    public double totalCost(LocalDateTime start, LocalDateTime end, int numberOfBoats , double rentalPrice){

        long durationOfReservation = Duration.between(start, end).toHours();
        return (durationOfReservation*numberOfBoats*rentalPrice);
    }
}
