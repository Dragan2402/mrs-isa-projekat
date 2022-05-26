package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "client")
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
public class Client extends User {

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "subscribed", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "renting_entity_id", referencedColumnName = "id"))
    private Set<RentingEntity> subscriptions = new HashSet<RentingEntity>();

    public Client() {
    }

    public Client(String email, String username, String password, String picture, String firstName, String lastName, String address, String city,
                  String country, String phoneNum) {
        super(email, username, password, picture, firstName, lastName, address, city, country, phoneNum);
        super.setType(AccountType.CLIENT);

    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setClient(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setClient(null);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setClient(this);
    }

    public void removeSubscription(RentingEntity rentingEntity) {
        subscriptions.remove(rentingEntity);
    }

    public void addSubscription(RentingEntity rentingEntity) {
        subscriptions.add(rentingEntity);
    }

    public Boolean isSubscribed(RentingEntity rentingEntity){
        return subscriptions.contains(rentingEntity);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setClient(null);
    }

    public Set<RentingEntity> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<RentingEntity> subscriptions) {
        this.subscriptions = subscriptions;
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
