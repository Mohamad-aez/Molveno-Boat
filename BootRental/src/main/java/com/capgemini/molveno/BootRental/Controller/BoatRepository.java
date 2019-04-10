package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.Boat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoatRepository extends CrudRepository<Boat, Long> {
    List<Boat> findAll();
    Boat findById(long id);
}
