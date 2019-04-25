package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.BoatTypeRepository;
import com.capgemini.molveno.BootRental.Model.Boat;
import com.capgemini.molveno.BootRental.Model.BoatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BoatTypeEndPoint {

    @Autowired
    private BoatTypeRepository boatTypeRepository;
//    @CrossOrigin(origins = "http://localhoast:4200")
    @RequestMapping(value = "/get-all-boattypes" , method = RequestMethod.GET)
    public List<BoatType> getAllBoatTypes(){
        return boatTypeRepository.findAll();
    }

    @RequestMapping(value = "/get-boattypebyid/{id}",method = RequestMethod.GET)
    public BoatType getBoatTypeById(@PathVariable long id){
        return boatTypeRepository.findById(id);

    }



//    @CrossOrigin(origins = "http://localhoast:4200")
    @RequestMapping(value = "/add-boattype",method = RequestMethod.POST,consumes = "application/json")
    public void addBoatType(@RequestBody BoatType boatType){
        boatTypeRepository.save(boatType);
    }
//    @CrossOrigin(origins = "http://localhoast:4200")
    @RequestMapping(value = "delete-boattype",method = RequestMethod.POST)
    public void deleteBoatType (@RequestBody BoatType boatType){
        BoatType boatType1 =boatTypeRepository.findById(boatType.getId());
        boatTypeRepository.deleteById(boatType1.getId());
    }
//    @CrossOrigin(origins = "http://localhoast:4200")
    @RequestMapping(value = "/edit-boattype",method = RequestMethod.POST,consumes = "application/json")
    public void editBoatType(@RequestBody BoatType boatType){
        BoatType boatType1 =boatTypeRepository.findById(boatType.getId());
        boatType1.setType(boatType.getType());
        boatType1.setRentalPrice(boatType.getRentalPrice());
        boatTypeRepository.save(boatType1);
    }




    @RequestMapping(value = "/check-boattypetype",method = RequestMethod.GET)
    public boolean checkBoatTypeType(@RequestParam String type){
        List<BoatType> boatTypes=boatTypeRepository.findAll();
        for(BoatType boatType:boatTypes){

            if (boatType.getType().equals(type))
                return false;
        }

        return true;

    }

}
