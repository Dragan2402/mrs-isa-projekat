package com.projekat.projekat_mrs_isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Client extends User {

    @OneToMany(mappedBy = "client" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    public Client() {
    }

    public Client( String email, String password, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        super( email, password, firstName, lastName, address, city, country, phoneNum);
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
        reservation.setClient(this);
    }

    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
        reservation.setClient(null);
    }
}
