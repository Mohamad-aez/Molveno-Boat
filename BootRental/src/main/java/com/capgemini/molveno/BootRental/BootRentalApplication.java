package com.capgemini.molveno.BootRental;

import com.capgemini.molveno.BootRental.API.BoatEndPoint;
import com.capgemini.molveno.BootRental.API.BoatReservationEndPoint;
import com.capgemini.molveno.BootRental.Controller.BoatReservationRepository;
import com.capgemini.molveno.BootRental.Controller.TripService;
import com.capgemini.molveno.BootRental.Model.BoatReservation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BootRentalApplication {
//	static BoatReservationRepository boatReservationRepository;
	public static void main(String[] args) {
		SpringApplication.run(BootRentalApplication.class, args);

	}

}
