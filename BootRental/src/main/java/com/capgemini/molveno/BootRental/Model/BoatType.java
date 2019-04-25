package com.capgemini.molveno.BootRental.Model;

import javax.persistence.*;

@Entity
@Table(name = "boattype")
public class BoatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "rentalprice")
    private long rentalPrice;

    @Column(name = "timetoready")
    private int timeToReady = 1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(long rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public int getTimeToReady() {
        return timeToReady;
    }

    public void setTimeToReady(int timeToReady) {
        this.timeToReady = timeToReady;
    }
}
