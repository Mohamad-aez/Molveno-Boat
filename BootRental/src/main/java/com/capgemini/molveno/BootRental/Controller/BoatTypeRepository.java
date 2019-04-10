package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.BoatType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoatTypeRepository extends CrudRepository<BoatType,Long> {
    List<BoatType> findAll();
    BoatType findById(long id);
}
