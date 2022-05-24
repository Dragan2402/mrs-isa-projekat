package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Reservation;



public interface EmailService {


    void confirmReservationMail(Client client, Reservation reservation);

    void sendVerificationMail(UserDTO userDTO);
}
