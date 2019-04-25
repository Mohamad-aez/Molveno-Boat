package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.BoatRepository;
import com.capgemini.molveno.BootRental.Controller.BoatReservationRepository;
import com.capgemini.molveno.BootRental.Controller.GuestRepository;
import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.BoatReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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
@CrossOrigin(origins = "http://localhoast:4200")
    @RequestMapping(value = "/delete-boatreservation",method = RequestMethod.POST)
    public void deleteBoat(@RequestBody BoatReservation boatReservation){
        boatReservationRepository.deleteById(boatReservation.getId());
    }

}
