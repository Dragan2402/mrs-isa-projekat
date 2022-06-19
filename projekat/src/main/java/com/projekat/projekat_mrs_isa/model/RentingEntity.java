package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.RentingEntityDTO;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Where(clause = "deleted = false")
public abstract class RentingEntity  implements Serializable {
    @Id
    @SequenceGenerator(name = "rentingEntitiesSeqGen", sequenceName = "rentingEntitiesSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentingEntitiesSeqGen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "promo_description", nullable = false)
    private String promoDescription;

    @Column(name = "pictures", nullable = false)
    @ElementCollection(targetClass=String.class)
    private List<String> pictures;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<Client> subscribers = new HashSet<Client>();

//    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//    private Set<Slot> slots = new HashSet<Slot>();

    @Column(name = "available_from")
    private LocalDateTime availableFrom;

    @Column(name = "available_to")
    private LocalDateTime availableTo;

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @Column(name = "reservations_counter")
    private Integer reservationsCounter;

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Complaint> complaints = new HashSet<>();

    @Column(name = "behaviour_rules", nullable = false)
    private String behaviourRules;

    @Column(name = "price_list", nullable = false)
    private Double priceList;

    @Column(name = "additional_info", nullable = false)
    private String additionalInfo;

    @Column(name = "cancellation_conditions", nullable = false)
    private String cancellationConditions;

    @Column(name="rating",nullable = false)
    private Double rating;

    @Column(name = "reviews_number",nullable = false)
    private Integer reviewsNumber;

    @Version
    @Column(name = "version",nullable = false,unique = false)
    private Integer version;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public RentingEntity() {}

    public RentingEntity(String name, String address, String promoDescription, List<String> pictures,
                         String behaviourRules, Double priceList, String additionalInfo, String cancellationConditions) {
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.pictures = pictures;
        this.behaviourRules = behaviourRules;
        this.priceList = priceList;
        this.additionalInfo = additionalInfo;
        this.cancellationConditions = cancellationConditions;
        this.rating=0.0;
        this.reviewsNumber=0;
        reservationsCounter=0;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPromoDescription() {
        return promoDescription;
    }
    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }
    public List<String> getPictures() {
        return pictures;
    }
    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }
    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }
    public LocalDateTime getAvailableTo() {
        return availableTo;
    }
    public void setAvailableTo(LocalDateTime availableTo) {
        this.availableTo = availableTo;
    }
    public Set<Offer> getOffers() {
        return offers;
    }
    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
    public void addOffer(Offer offer) {
        offers.add(offer);
        offer.setRentingEntity(this);
    }
    public void removeOffer(Offer offer) {
        offers.remove(offer);
        offer.setRentingEntity(null);
    }
    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservationsCounter++;
        reservation.setRentingEntity(this);
    }
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservationsCounter--;
        reservation.setRentingEntity(null);
    }
    public void addReview(Review review){
        reviews.add(review);
        double ratingSum= this.rating*this.reviewsNumber;
        this.reviewsNumber++;
        this.rating=(ratingSum+review.getRating()) / this.reviewsNumber;
        review.setRentingEntity(this);
    }

    public void removeReview(Review review){
        reviews.remove(review);
        review.setRentingEntity(null);
    }

    public void addComplaint(Complaint complaint){
        complaints.add(complaint);
        complaint.setRentingEntity(this);
    }

    public void removeComplaint(Complaint complaint){
        complaints.remove(complaint);
        complaint.setRentingEntity(null);
    }

    public void removeSubscription(Client client) {
        subscribers.remove(client);
        client.removeSubscription(this);
    }

    public void addSubscription(Client client) {
       subscribers.add(client);
       client.addSubscription(this);
    }

    public Set<Client> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Client> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        this.complaints = complaints;
    }

    public String getBehaviourRules() {
        return behaviourRules;
    }
    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
    }
    public Double getPriceList() {
        return priceList;
    }
    public void setPriceList(Double priceList) {
        this.priceList = priceList;
    }
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    public String getCancellationConditions() {
        return cancellationConditions;
    }
    public void setCancellationConditions(String cancellationConditions) {
        this.cancellationConditions = cancellationConditions;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReviewsNumber() {
        return reviewsNumber;
    }

    public void setReviewsNumber(Integer reviewsNumber) {
        this.reviewsNumber = reviewsNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    abstract public String getREType();

    public void update(RentingEntityDTO rentingEntityDTO)
    {
        this.name = rentingEntityDTO.getName();
        this.address = rentingEntityDTO.getAddress();
        this.promoDescription = rentingEntityDTO.getPromoDescription();
        this.availableFrom = rentingEntityDTO.getAvailableFrom();
        this.availableTo = rentingEntityDTO.getAvailableTo();
        this.behaviourRules = rentingEntityDTO.getBehaviourRules();
        this.availableFrom = rentingEntityDTO.getAvailableFrom();
        this.availableTo = rentingEntityDTO.getAvailableTo();
        this.additionalInfo = rentingEntityDTO.getAdditionalInfo();
        this.cancellationConditions = rentingEntityDTO.getCancellationConditions();
    }
}