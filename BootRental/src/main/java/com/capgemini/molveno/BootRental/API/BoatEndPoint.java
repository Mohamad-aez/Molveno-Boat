package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.BoatRepository;
import com.capgemini.molveno.BootRental.Model.Boat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoatEndPoint {

    @Autowired
    private BoatRepository boatRepository;

    @RequestMapping(value = "/get-boat", method = RequestMethod.GET)
    public List<Boat> getAllBoats() {
        return boatRepository.findAll();
    }

    @RequestMapping(value = "/add-boat", method = RequestMethod.POST,consumes = "application/json")
    public void addBoat(@RequestBody Boat boat){
        boatRepository.save(boat);
    }

    @RequestMapping(value = "/edit-boat",method = RequestMethod.POST,consumes = "application/json")
    public void editBoat(@RequestBody Boat boat) {
        Boat boat1 = boatRepository.findById(boat.getId());
        boat1.setBoatNumber(boat.getBoatNumber());
        boat1.setBoatType(boat.getBoatType());
        boat1.setNumberOfSeats(boat.getNumberOfSeats());
        boatRepository.save(boat1);
    }

    @RequestMapping(value = "/delete-boat",method = RequestMethod.POST)
    public void deleteBoat(@RequestBody Boat boat){
        boatRepository.deleteById(boat.getId());
    }


}
