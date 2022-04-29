package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.service.ClientService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ResourceLoader resourceLoader;

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

    @GetMapping(value = "/{clientId}/picture", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public ResponseEntity<String> getPicture(@PathVariable("clientId") Long clientId) {
        String picturePath= clientService.findById(clientId).getPicture();
        System.out.println(picturePath);
        Resource r = resourceLoader
                .getResource("classpath:" + picturePath);
        System.out.println(r);
        try {
            File file = r.getFile();
            byte[] picture = FileUtils.readFileToByteArray(file);
            String encodedPicture = Base64.encodeBase64String(picture);
            return new ResponseEntity<>(encodedPicture, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
