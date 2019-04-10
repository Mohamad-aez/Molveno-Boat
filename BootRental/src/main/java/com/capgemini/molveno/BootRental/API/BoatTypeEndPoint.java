package com.capgemini.molveno.BootRental.API;

import com.capgemini.molveno.BootRental.Controller.BoatTypeRepository;
import com.capgemini.molveno.BootRental.Model.BoatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoatTypeEndPoint {

    @Autowired
    private BoatTypeRepository boatTypeRepository;

    @RequestMapping(value = "/get-boattype" , method = RequestMethod.GET)
    public List<BoatType> getAllBoatTypes(){
        return boatTypeRepository.findAll();
    }

    @RequestMapping(value = "/add-boattype",method = RequestMethod.POST,consumes = "application/json")
    public void addBoatType(@RequestBody BoatType boatType){
        boatTypeRepository.save(boatType);
    }

    @RequestMapping(value = "delete-boattype",method = RequestMethod.POST)
    public void deleteBoatType (@RequestBody BoatType boatType){
        boatTypeRepository.deleteById(boatType.getId());
    }

    @RequestMapping(value = "/edit-boattype",method = RequestMethod.POST,consumes = "application/json")
    public void editBoatType(@RequestBody BoatType boatType){
        BoatType boatType1 =boatTypeRepository.findById(boatType.getId());
        boatType1.setType(boatType.getType());
        boatType1.setRentalPrice(boatType.getRentalPrice());
    }

}
