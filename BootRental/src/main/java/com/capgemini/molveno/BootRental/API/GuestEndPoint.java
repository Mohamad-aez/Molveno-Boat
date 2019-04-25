package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.GuestRepository;
import com.capgemini.molveno.BootRental.Model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class GuestEndPoint {

    @Autowired
    private GuestRepository guestRepository;


    @RequestMapping(value = "/get-all-guests",method = RequestMethod.GET)
    private List<Guest> getAllGuests(){
        return guestRepository.findAll();
    }



    @RequestMapping(value = "/add-guest",method = RequestMethod.POST,consumes = "application/json")
    private void addGuest(@RequestBody Guest guest){
        guestRepository.save(guest);
    }

    @RequestMapping(value = "/get-one-guest/{id}", method = RequestMethod.GET)
    public Guest getOneTripById(@PathVariable long id) {
        return guestRepository.findById(id);
    }

//    @RequestMapping(value = "/edit-guest",method = RequestMethod.POST,consumes = "application/json")
//    private void editGuest(@RequestBody Guest guest){
//        Guest guest1 = guestRepository.findById(guest.getId());
//        guest1.setName(guest.getName());
//        guest1.setIdType(guest.getIdType());
//        guest1.setIdNumber(guest.getIdNumber());
//        guest1.setPhone(guest.getPhone());
//        guestRepository.save(guest1);
//    }

}
