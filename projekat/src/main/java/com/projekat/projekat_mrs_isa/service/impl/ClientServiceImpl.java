package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.*;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.ClientRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Autowired
    private RentingEntityService rentingEntityService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmailService emailService;


    @Autowired
    private UtilityService utilityService;

    @Override
    public Client findById(long id) { return clientRepository.findById(id).orElse(null) ;}
    @Override
    public List<UserDTO> findAllDTO() { return clientRepository.findAllDTO(); }
    @Override
    public UserDTO findUserDTO(Long id) { return  clientRepository.findUserDTO(id) ; }
    @Override
    public List<Client> findAll() { return clientRepository.findAll(); }
    @Override
    public Client save(Client client) { return clientRepository.save(client); }
    @Override
    public void flush() {  clientRepository.flush(); }
    @Override
    public void remove(long id) {  clientRepository.deleteById(id);}
    @Override
    public Client findByEmail(String email) { return  clientRepository.findByEmail(email); }

    @Override
    public Client addClient(String email, String username, String password, String picture, String firstName, String lastName, String address, String city, String country, String phoneNum) {
        Client clientTemp= new Client(email,username,passwordEncoderComponent.encode(password),picture,firstName,lastName,address,city,country,phoneNum);
        List<Role> rolesClient = roleRepository.findByName("ROLE_CLIENT");
        clientTemp.setRoles(rolesClient);
        clientRepository.save(clientTemp);
        return clientTemp;
    }

    @Override
    public void resetPenalties() {
        for(Client client : clientRepository.findAll()){
            client.setPenalties(0);
            clientRepository.save(client);
        }
    }

    @Override
    public Boolean subscribe(Client client, Long reId) {
        RentingEntity rentingEntity = rentingEntityService.findById(reId);
        if (rentingEntity == null)
            return false;
        rentingEntity.addSubscription(client);
        save(client);
        return true;

    }

    @Override
    public Boolean unSubscribe(Client client, Long id) {
        RentingEntity rentingEntity = rentingEntityService.findById(id);
        if (rentingEntity == null)
            return false;
        rentingEntity.removeSubscription(client);
        save(client);
        return true;
    }

    @Override
    public Boolean isSubscribed(Client client, Long id) {
        RentingEntity rentingEntity = rentingEntityService.findById(id);
        if (rentingEntity == null)
            return false;
        return client.isSubscribed(rentingEntity);
    }

    @Override
    public List<SubscriptionDTO> getSubscriptions(Client client) {
        List<SubscriptionDTO> subscriptions=new ArrayList<>();
        for(RentingEntity rentingEntity : client.getSubscriptions()){
            SubscriptionDTO subscriptionDTO=new SubscriptionDTO(rentingEntity);
            String picturePath="pictures/renting_entities/0.png";
            if(rentingEntity.getPictures().size()>0){
                picturePath=rentingEntity.getPictures().get(0);
            }
            subscriptionDTO.setImg(utilityService.getPictureEncoded(picturePath));
            subscriptions.add(subscriptionDTO);
        }
        return subscriptions;
    }

    @Override
    public List<TakenPeriodDTO> rentingEntityAvailability(Client client, Long id) {
        RentingEntity rentingEntity = rentingEntityService.findById(id);
        if (rentingEntity == null)
            return null;
        List<TakenPeriodDTO> takenPeriod = new ArrayList<>();
        for(Reservation reservation : rentingEntity.getReservations()){
            TakenPeriodDTO takenPeriodDTO=new TakenPeriodDTO(reservation.getStart(),reservation.getStart().plus(reservation.getDuration()),"Reservation");
            takenPeriod.add(takenPeriodDTO);
        }
        for(Offer offer : rentingEntity.getOffers()){
            TakenPeriodDTO takenPeriodDTO=new TakenPeriodDTO(offer.getStart(),offer.getStart().plus(offer.getDuration()),"Offer");
            takenPeriod.add(takenPeriodDTO);
        }
        return takenPeriod;
    }

    @Override
    public Boolean makeClientReservation(Client logged, ReservationRequestDTO reservationRequestDTO) {
        reservationRequestDTO.setStart(reservationRequestDTO.getStart().plusDays(1));
        reservationRequestDTO.setEnd(reservationRequestDTO.getEnd().plusDays(1));
        Duration duration=Duration.between(reservationRequestDTO.getStart(), reservationRequestDTO.getEnd());
        RentingEntity rentingEntity=rentingEntityService.findById(reservationRequestDTO.getRentingEntityId());
        if (rentingEntity==null)
            return false;
        Reservation reservation= new Reservation(reservationRequestDTO.getPlace(), reservationRequestDTO.getClientLimit(),
                reservationRequestDTO.getAdditionalServices(),reservationRequestDTO.getPrice(),rentingEntity,
                logged,reservationRequestDTO.getStart(),duration);
        logged.addReservation(reservation);
        rentingEntity.addReservation(reservation);
        reservationService.save(reservation);
        emailService.confirmReservationMail(logged,reservation);
        return true;
    }

    @Override
    public Boolean updatePassword(Client logged, PasswordChangeDTO passwordChangeDTO) {
        if(!passwordEncoderComponent.decode(passwordChangeDTO.getOldPassword(),logged.getPassword()))
            return false;
        logged.setPassword(passwordEncoderComponent.encode(passwordChangeDTO.getNewPassword()));
        save(logged);
        return true;
    }

    @Override
    public Client findByUsername(String name) {
        return clientRepository.findByUsername(name);

    }

}
