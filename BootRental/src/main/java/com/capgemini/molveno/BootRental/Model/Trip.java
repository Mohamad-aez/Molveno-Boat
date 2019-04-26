package com.capgemini.molveno.BootRental.Model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;

    @Column(name = "status")
    private String status;

    ///
    @Column(name = "boattype")
    private String boatType;
    ////

    @Column(name = "starttime")
    private LocalDateTime startTime;

    @Column(name = "endtime")
    private LocalDateTime endTime = null;

    @Column(name = "reservationduration")
    private long reservationDuration;

    @Column(name = "numberofpersons")
    private int numberOfPersons;

    @Column(name = "cost")
    private double cost;

    @ManyToMany
    @JoinTable(name = "boatsoftrip", joinColumns = {@JoinColumn(name = "tripid")}, inverseJoinColumns = {@JoinColumn(name = "boatid")})
    private List<Boat> boats;


    @JoinColumn(name = "guestid", referencedColumnName = "id")
    @OneToOne
    private Guest guest;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public long getReservationDuration() {
        return reservationDuration;
    }

    public void setReservationDuration(long reservationDuration) {
        this.reservationDuration = reservationDuration;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }
}
