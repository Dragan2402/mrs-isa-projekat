package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
public class Client extends User {

    @OneToMany(mappedBy = "client" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews= new HashSet<>();

    public Client() {}

    public Client( String email,String username,String password, String picture,String firstName, String lastName, String address, String city,
                   String country, String phoneNum) {
        super( email,username, password, picture,firstName, lastName, address, city, country, phoneNum);
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

    public void addReview(Review review){
        reviews.add(review);
        review.setClient(this);
    }

    public void removeReview(Review review){
        reviews.remove(review);
        review.setClient(null);
    }

    public void update(UserDTO userDTO) {
        this.setFirstName(userDTO.getFirstName());
        this.setLastName(userDTO.getLastName());
        this.setAddress(userDTO.getAddress());
        this.setCity(userDTO.getCity());
        this.setCountry(userDTO.getCountry());
        this.setPhoneNum(userDTO.getPhoneNum());
    }
}
