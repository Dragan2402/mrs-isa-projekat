package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.*;


public interface EmailService {
    void confirmReservationMail(Client client, Reservation reservation);

    void sendVerificationMail(UserDTO userDTO);

    void sendResetPasswordMail(String mailP, String token);

    void sendReview(User client, User owner, Review review);

    void sendNewOfferMail(Client client, RentingEntity rentingEntity, Offer newOffer);
}
