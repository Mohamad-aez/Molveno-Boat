package com.capgemini.molveno.BootRental.Model;


import javax.persistence.*;


@Entity
@Table(name = "boat")
public class Boat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;

    @Column(name = "boatnumber")
    private String boatNumber;

    @OneToOne
    @JoinColumn(name = "boattypeid", referencedColumnName = "id")
    private BoatType boatType;

    @Column(name = "numberofseats")
    private int numberOfSeats;

    @Column(name = "boatavailability")
    private boolean boatAvailability = true;

    @Column(name = "countofused")
    private int countOfUsed = 0;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoatNumber() {
        return boatNumber;
    }

    public void setBoatNumber(String boatNumber) {
        this.boatNumber = boatNumber;
    }

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isBoatAvailability() {
        return boatAvailability;
    }

    public void setBoatAvailability(boolean boatAvailability) {
        this.boatAvailability = boatAvailability;
    }

    public int getCountOfUsed() {
        return countOfUsed;
    }

    public void setCountOfUsed(int countOfUsed) {
        this.countOfUsed = countOfUsed;
    }
}
