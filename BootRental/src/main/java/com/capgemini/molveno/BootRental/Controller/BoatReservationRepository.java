package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.BoatReservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoatReservationRepository extends CrudRepository <BoatReservation,Long> {
    List<BoatReservation> findAll();
    BoatReservation findById(long id);
}
