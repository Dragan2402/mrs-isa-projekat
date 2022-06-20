package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.*;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.ClientRepository;
import com.projekat.projekat_mrs_isa.repository.RentingEntityRepository;
import com.projekat.projekat_mrs_isa.repository.ReservationRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Autowired
    private RentingEntityService rentingEntityService;

    @Autowired
    private RentingEntityRepository rentingEntityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VacationHouseService vacationHouseService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private FishingClassService fishingClassService;

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
        RentingEntity rentingEntity = rentingEntityRepository.findById(reId).orElse(null);
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
        RentingEntity rentingEntity = rentingEntityRepository.findById(id).orElse(null);
        if (rentingEntity == null)
            return false;
        return client.isSubscribed(rentingEntity);
    }

    @Override
    public List<SubscriptionDTO> getSubscriptions(Client client) {
        List<SubscriptionDTO> subscriptions=new ArrayList<>();
        for(RentingEntity rentingEntity : rentingEntityRepository.getSubscribedRentingEntitiesByClient(client.getId())){
            SubscriptionDTO subscriptionDTO=new SubscriptionDTO(rentingEntity);
            String picturePath="src/main/resources/pictures/renting_entities/0.png";
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
                if(!reservation.isDeleted()){
                TakenPeriodDTO takenPeriodDTO=new TakenPeriodDTO(reservation.getStart(),reservation.getStart().plus(reservation.getDuration()),"Reservation");
                takenPeriod.add(takenPeriodDTO);
            }
        }
        for(Offer offer : rentingEntity.getOffers()){
            TakenPeriodDTO takenPeriodDTO=new TakenPeriodDTO(offer.getStart(),offer.getStart().plus(offer.getDuration()),"Offer");
            takenPeriod.add(takenPeriodDTO);
        }
        return takenPeriod;
    }

    @Override
    @Transactional
    public Boolean makeClientReservation(Client logged, ReservationRequestDTO reservationRequestDTO) throws ObjectOptimisticLockingFailureException {
        reservationRequestDTO.setStart(reservationRequestDTO.getStart().plusHours(2));
        reservationRequestDTO.setEnd(reservationRequestDTO.getEnd().plusHours(2));
        Duration duration=Duration.between(reservationRequestDTO.getStart(), reservationRequestDTO.getEnd());
        RentingEntity rentingEntity=rentingEntityService.findById(reservationRequestDTO.getRentingEntityId());
        if (rentingEntity==null)
            return false;
        Reservation reservation= new Reservation(reservationRequestDTO.getPlace(), reservationRequestDTO.getClientLimit(),
                reservationRequestDTO.getAdditionalServices(),reservationRequestDTO.getPrice(),rentingEntity,
                logged,reservationRequestDTO.getStart(),duration);
        if(haveNotMadeReservationBefore(logged,reservation)){
            rentingEntity.addReservation(reservation);
            reservationRepository.save(reservation);
            emailService.confirmReservationMail(logged,reservation);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean haveNotMadeReservationBefore(Client logged, Reservation reservation) {
        for(Reservation reservation_made : findAllCanceledReservationClient(logged)) {
            if (Objects.equals(reservation_made.getRentingEntity().getId(), reservation.getRentingEntity().getId()) &&  reservation_made.getDuration().equals(reservation.getDuration()))
            {
                LocalDateTime reservationHourBefore = reservation.getStart().minusHours(1);
                LocalDateTime reservationHourAfter = reservation.getStart().plusHours(1);
                System.out.println(reservationHourBefore);
                System.out.println(reservationHourAfter);
                System.out.println(reservation_made.getStart());
                if(reservation_made.getStart().compareTo(reservationHourBefore)>=0 && reservation_made.getStart().compareTo(reservationHourAfter)<=0)
                    return false;
            }
        }
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
    @Transactional
    public Boolean makeQuickReservation(Client clientLogged, Offer offer) throws ObjectOptimisticLockingFailureException {
        Reservation reservation= new Reservation(offer.getPlace(),offer.getClientLimit(), new ArrayList<>(offer.getAdditionalServices()),
                offer.getPrice(),offer.getRentingEntity(),clientLogged,offer.getStart(),offer.getDuration());
        offer.getRentingEntity().addReservation(reservation);
        reservationRepository.save(reservation);
        offer.setDeleted(true);
        offerService.save(offer);
        emailService.confirmReservationMail(clientLogged,reservation);
        return  true;
    }

    @Override
    public List<Reservation> findAllCanceledReservationClient(Client clientLogged) {
        return reservationRepository.findAllCanceledReservationClient(clientLogged);
    }

    @Override
    public List<ReservationDTO> getClientReservationHistory(Client client) {
        List<ReservationDTO> reservationsDtos = new ArrayList<>();
        for (Reservation reservation : reservationRepository.getByClient(client)) {
            if (reservation.getStart().plus(reservation.getDuration()).compareTo(LocalDateTime.now()) < 0 && !reservation.isDeleted()) {
                ReservationDTO reservationDTO = new ReservationDTO(reservation);
                reservationDTO.setRentingEntityOwnerId(getRentingEntityOwnerId(reservation.getRentingEntity()));
                reservationDTO.setRentingEntityOwner(getRentingEntityOwner(reservation.getRentingEntity()));
                String picturePath = "pictures/renting_entities/0.png";
                if (reservation.getRentingEntity().getPictures().size() > 0) {
                    picturePath = reservation.getRentingEntity().getPictures().get(0);
                }
                reservationDTO.setImg(utilityService.getPictureEncoded(picturePath));
                reservationsDtos.add(reservationDTO);
            }
        }
        return reservationsDtos;
    }

    @Override
    public List<ReservationDTO> getClientReservations(Client client) {
        List<ReservationDTO> reservationsDTOs = new ArrayList<>();
        for (Reservation reservation : reservationRepository.getByClient(client)) {
            if (reservation.getStart().plus(reservation.getDuration()).compareTo(LocalDateTime.now()) >= 0 && !reservation.isDeleted()) {
                ReservationDTO reservationDTO = new ReservationDTO(reservation);
                reservationDTO.setRentingEntityOwnerId(getRentingEntityOwnerId(reservation.getRentingEntity()));
                reservationDTO.setRentingEntityOwner(getRentingEntityOwner(reservation.getRentingEntity()));
                String picturePath = "pictures/renting_entities/0.png";
                if (reservation.getRentingEntity().getPictures().size() > 0) {
                    picturePath = reservation.getRentingEntity().getPictures().get(0);
                }
                reservationDTO.setImg(utilityService.getPictureEncoded(picturePath));
                reservationsDTOs.add(reservationDTO);
            }
        }
        return reservationsDTOs;
    }

    @Override
    public Client findByUsername(String name) {
        return clientRepository.findByUsername(name);

    }
    private String getRentingEntityOwner(RentingEntity rentingEntity) {


        if (rentingEntity.getREType().equals("VH")) {
            return vacationHouseService.findById(rentingEntity.getId()).getVacationHouseOwner().getUsername();
        } else if (rentingEntity.getREType().equals("FC")) {
            return fishingClassService.findById(rentingEntity.getId()).getFishingInstructor().getUsername();
        } else {
            return shipService.findById(rentingEntity.getId()).getShipOwner().getUsername();
        }

    }

    private Long getRentingEntityOwnerId(RentingEntity rentingEntity) {


        if (rentingEntity.getREType().equals("VH")) {
            return vacationHouseService.findById(rentingEntity.getId()).getVacationHouseOwner().getId();
        } else if (rentingEntity.getREType().equals("FC")) {
            return fishingClassService.findById(rentingEntity.getId()).getFishingInstructor().getId();
        } else {
            return shipService.findById(rentingEntity.getId()).getShipOwner().getId();
        }

    }

}
