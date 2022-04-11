package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client findById(long id) { return clientRepository.findById(id).orElse(null) ;}

    public List<UserDTO> findAllDTO() { return clientRepository.findAllDTO(); }

    public List<Client> findAll() { return clientRepository.findAll(); }

    public Page<Client> findAll(Pageable page) { return clientRepository.findAll(page);}

    public Client save(Client client) { return clientRepository.save(client); }

    public void remove(long id) {  clientRepository.deleteById(id);}

    public Client findByEmail(String email) { return  clientRepository.findByEmail(email); }


}
