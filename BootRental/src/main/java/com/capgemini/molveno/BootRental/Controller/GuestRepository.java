package com.capgemini.molveno.BootRental.Controller;

import com.capgemini.molveno.BootRental.Model.Guest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuestRepository extends CrudRepository<Guest,Long> {
    List<Guest> findAll();
    Guest findById(long id);
}
