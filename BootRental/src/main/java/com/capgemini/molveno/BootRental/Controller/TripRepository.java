package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip,Long> {
    List<Trip> findAll();
    Trip findById(long id);
}
