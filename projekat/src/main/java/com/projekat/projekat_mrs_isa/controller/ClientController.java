package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllClients() {
        List<UserDTO> clients = clientService.findAllDTO();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getClientDTOById(@PathVariable("id") Long id) {
        UserDTO clientDTO = clientService.findUserDTO(id);
        if (clientDTO == null)
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<UserDTO>(clientDTO,HttpStatus.OK);
    }

    @GetMapping(value = "loggedClient",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getLoggedClient() throws Exception{
        UserDTO clientDTO = clientService.findUserDTO(2L);
        if (clientDTO == null)
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<UserDTO>(clientDTO,HttpStatus.OK);
    }

    @PutMapping(value = "loggedClient",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateLoggedClient(@RequestBody UserDTO userDTO) throws Exception{
        System.out.println(userDTO.getCountry());
        Client clientToUpdate = clientService.findById(2L);
        clientToUpdate.update(userDTO);
        Client updatedCLient = clientService.save(clientToUpdate);


        if (updatedCLient == null)
            return new  ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new  ResponseEntity<UserDTO>(new UserDTO(updatedCLient),HttpStatus.OK);
    }

}
