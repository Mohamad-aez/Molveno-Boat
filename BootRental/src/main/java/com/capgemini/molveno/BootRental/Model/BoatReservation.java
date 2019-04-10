package com.capgemini.molveno.BootRental.Model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "boatreservation")
public class BoatReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;

    @Column(name = "reservationdate")
    private Date reservationDate;

    @Column(name = "starttime")
    private Time startTime;

    @Column(name = "endtime")
    private Time endTime;

    @Column(name = "reservationduration")
    private double reservationDuration;

    @Column(name = "numberofpersons")
    private String numberOfPersons;

    @Column(name = "cost")
    private double cost;

    @ManyToMany
    @JoinTable(name = "reservationsofboats", joinColumns = {@JoinColumn(name = "reservationid")}, inverseJoinColumns = {@JoinColumn(name = "boatid")})
    private List<Boat> boats;

    @OneToOne
    @JoinColumn(name = "guestid", referencedColumnName = "id")
    private Guest guest;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public double getReservationDuration() {
        return reservationDuration;
    }

    public void setReservationDuration(double reservationDuration) {
        this.reservationDuration = reservationDuration;
    }

    public String getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
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
}
