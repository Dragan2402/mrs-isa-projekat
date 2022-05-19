package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Role;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.repository.ClientRepository;
import com.projekat.projekat_mrs_isa.repository.RoleRepository;
import com.projekat.projekat_mrs_isa.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Client findById(long id) { return clientRepository.findById(id).orElse(null) ;}
    @Override
    public List<UserDTO> findAllDTO() { return clientRepository.findAllDTO(); }
    @Override
    public UserDTO findUserDTO(Long id) { return  clientRepository.findUserDTO(id) ; }
    @Override
    public List<Client> findAll() { return clientRepository.findAll(); }
    @Override
    public Page<Client> findAll(Pageable page) { return clientRepository.findAll(page);}
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
    public Client findByUsername(String name) {
        return clientRepository.findByUsername(name);

    }
}
