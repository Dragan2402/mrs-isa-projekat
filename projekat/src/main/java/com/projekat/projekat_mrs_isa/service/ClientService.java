package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.*;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Reservation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;


public interface ClientService {


    Client findById(long id);

    List<UserDTO> findAllDTO();

    UserDTO findUserDTO(Long id) ;

    List<Client> findAll();

    Client save(Client client);

    void flush();

    void remove(long id) ;

    Client findByEmail(String email);

    @Cacheable(value = "user",key = "#name",unless="#result == null")
    Client findByUsername(String name);

    Client addClient(String email, String username, String password, String picture, String firstName, String lastName, String address, String city, String country, String phoneNum);

    void resetPenalties();

    Boolean subscribe(Client client,Long reId);

    Boolean unSubscribe(Client client, Long id);

    Boolean isSubscribed(Client client, Long id);

    List<SubscriptionDTO> getSubscriptions(Client client);

    List<TakenPeriodDTO> rentingEntityAvailability(Client client, Long id);

    Boolean makeClientReservation(Client logged, ReservationRequestDTO reservationRequestDTO) throws ObjectOptimisticLockingFailureException;

    Boolean updatePassword(Client logged, PasswordChangeDTO passwordChangeDTO);

    Boolean makeQuickReservation(Client clientLogged, Offer offer) throws ObjectOptimisticLockingFailureException;

    List<Reservation> findAllCanceledReservationClient(Client clientLogged);

    List<ReservationDTO> getClientReservationHistory(Client client);

    List<ReservationDTO> getClientReservations(Client client);

    void notifySubscribersByRentingEntity(RentingEntity rentingEntity, Offer newOffer);
}
