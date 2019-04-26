package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.BoatRepository;
import com.capgemini.molveno.BootRental.Controller.TripService;
import com.capgemini.molveno.BootRental.Model.Boat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BoatEndPoint {

    @Autowired
    private BoatRepository boatRepository;

    TripService tripService =new TripService();

    @RequestMapping(value = "/get-all-boats", method = RequestMethod.GET)
    public List<Boat> getAllBoats() {
        return boatRepository.findAll();
    }


    @RequestMapping(value = "/add-boat", method = RequestMethod.POST,consumes = "application/json")
    public void addBoat(@RequestBody Boat boat){
        boatRepository.save(boat);
    }

//    @RequestMapping(value = "/edit-boat",method = RequestMethod.POST,consumes = "application/json")
//    public void editBoat(@RequestBody Boat boat) {
//        Boat boat1 = boatRepository.findById(boat.getId());
//        if (!boat.getBoatNumber().equals("")){
//        boat1.setBoatNumber(boat.getBoatNumber());}
//        boat1.setCountOfUsed(boat.getCountOfUsed());
//        boat1.setBoatType(boat.getBoatType());
//        boat1.setNumberOfSeats(boat.getNumberOfSeats());
//        boat1.setBoatAvailability(boat.isBoatAvailability());
//        boatRepository.save(boat1);
//    }

    @RequestMapping(value = "/edit-boat/{id}/{boatAvailability}", method = RequestMethod.GET)
    public void editBoat(@PathVariable long id , @PathVariable boolean boatAvailability) {
        Boat boat1 = boatRepository.findById(id);
        boat1.setBoatAvailability(boatAvailability);
        boatRepository.save(boat1);
    }

    @RequestMapping(value = "/delete-boat",method = RequestMethod.POST)
    public void deleteBoat(@RequestBody Boat boat){
        boatRepository.deleteById(boat.getId());
    }

    @RequestMapping(value = "/get-one-boat/{id}", method = RequestMethod.GET)
    public Boat getOneTripById(@PathVariable long id) {
        return boatRepository.findById(id);
    }

    @RequestMapping(value = "/check-boatName",method = RequestMethod.GET)
    public boolean checkBoatName(@RequestParam String boatNumber){
        List<Boat> boats=boatRepository.findAll();
        for(Boat boat:boats){

            if (boat.getBoatNumber().equals(boatNumber))
                return false;
        }

        return true;

    }



}
