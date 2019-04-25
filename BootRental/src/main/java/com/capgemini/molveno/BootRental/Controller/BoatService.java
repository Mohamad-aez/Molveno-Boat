package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.Boat;

import java.util.List;

public class BoatService {

    public boolean checkBoatNumber (String boatNumber , List<String> allBoatsNumber){
        if (allBoatsNumber.contains(boatNumber))
            return false;
        return true;
    }

}
