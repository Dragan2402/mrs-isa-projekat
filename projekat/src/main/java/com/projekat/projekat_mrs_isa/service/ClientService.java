package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.SubscriptionDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ClientService {


    Client findById(long id);

    List<UserDTO> findAllDTO();

    UserDTO findUserDTO(Long id) ;

    List<Client> findAll();

    Page<Client> findAll(Pageable page);

    Client save(Client client);

    void flush();

    void remove(long id) ;

    Client findByEmail(String email);

    Client findByUsername(String name);

    Client addClient(String email, String username, String password, String picture, String firstName, String lastName, String address, String city, String country, String phoneNum);

    void resetPenalties();

    Boolean subscribe(Client client,Long reId);

    Boolean unSubscribe(Client client, Long id);

    Boolean isSubscribed(Client client, Long id);

    List<SubscriptionDTO> getSubscriptions(Client client);
}
