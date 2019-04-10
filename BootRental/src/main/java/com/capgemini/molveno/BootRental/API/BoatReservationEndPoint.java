package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.BoatRepository;
import com.capgemini.molveno.BootRental.Controller.BoatReservationRepository;
import com.capgemini.molveno.BootRental.Controller.GuestRepository;
import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.BoatReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoatReservationEndPoint {

    @Autowired
    private BoatReservationRepository boatReservationRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private BoatRepository boatRepository;

    @RequestMapping(value = "/get-all-reservation")
    public List<BoatReservation> getAllBoatsReservation() {
        return boatReservationRepository.findAll();
    }

    @RequestMapping(value = "/add-boatreservation", method = RequestMethod.POST,consumes = "application/json")
    public void addBoatReservation(@RequestBody BoatReservation boatReservation){
        for (int i=0;i<boatReservation.getBoats().size();i++){
            Boat boat1 = boatRepository.findById(boatReservation.getBoats().get(i).getId());
            boat1.setBoatAvailability(false);
            boatRepository.save(boat1);
        }

        guestRepository.save(boatReservation.getGuest());
        boatReservationRepository.save(boatReservation);
    }

//    @RequestMapping(value = "/edit-boatreservation",method = RequestMethod.POST,consumes = "application/json")
//    public void editBoatReservation(@RequestBody BoatReservation boatReservation) {
//        BoatReservation boatReservation1 = boatReservationRepository.findById(boatReservation.getId());
//        boatReservation1.setReservationDate(boatReservation.getReservationDate());
//        boatReservation1.setStartTime(boatReservation.getStartTime());
//        boatReservation1.setEndTime(boatReservation.getEndTime());
//        boatReservation1.setNumberOfPersons(boatReservation.getNumberOfPersons());
//        boatReservation1.setBoats(boatReservation.getBoats());
//        boatReservation1.setGuest(boatReservation.getGuest());
//        boatReservationRepository.save(boatReservation1);
//    }

    @RequestMapping(value = "/delete-boatreservation",method = RequestMethod.POST)
    public void deleteBoat(@RequestBody BoatReservation boatReservation){
        boatReservationRepository.deleteById(boatReservation.getId());
    }

}
