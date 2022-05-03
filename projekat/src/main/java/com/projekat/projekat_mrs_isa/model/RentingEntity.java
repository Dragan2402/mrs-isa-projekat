package com.projekat.projekat_mrs_isa.model;

import com.projekat.projekat_mrs_isa.dto.RentingEntityDTO;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Where(clause = "deleted = false")
public abstract class RentingEntity {
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

//    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//    private Set<Slot> slots = new HashSet<Slot>();

    @Column(name = "available_from")
    private LocalDateTime availableFrom;

    @Column(name = "available_to")
    private LocalDateTime availableTo;

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(mappedBy = "rentingEntity" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @Column(name = "behaviour_rules", nullable = false)
    private String behaviourRules;

    @Column(name = "price_list", nullable = false)
    private String priceList;

    @Column(name = "additional_info", nullable = false)
    private String additionalInfo;

    @Column(name = "cancellation_conditions", nullable = false)
    private String cancellationConditions;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public RentingEntity() {}

    public RentingEntity(String name, String address, String promoDescription, List<String> pictures,
                         String behaviourRules, String priceList, String additionalInfo, String cancellationConditions) {
        this.name = name;
        this.address = address;
        this.promoDescription = promoDescription;
        this.pictures = pictures;
        this.behaviourRules = behaviourRules;
        this.priceList = priceList;
        this.additionalInfo = additionalInfo;
        this.cancellationConditions = cancellationConditions;
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
    }
    public void removeOffer(Offer offer) {
        offers.remove(offer);
    }
    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
    public String getBehaviourRules() {
        return behaviourRules;
    }
    public void setBehaviourRules(String behaviourRules) {
        this.behaviourRules = behaviourRules;
    }
    public String getPriceList() {
        return priceList;
    }
    public void setPriceList(String priceList) {
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
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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