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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.*;

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

        Client clientToUpdate = clientService.findById(2L);
        clientToUpdate.update(userDTO);
        Client updatedCLient = clientService.save(clientToUpdate);


        if (updatedCLient == null)
            return new  ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new  ResponseEntity<UserDTO>(new UserDTO(updatedCLient),HttpStatus.OK);
    }

    @GetMapping(value = "loggedClient/picture", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public ResponseEntity<String> getPicture() {
        String picturePath= clientService.findById(2L).getPicture();

        Resource r = resourceLoader
                .getResource("classpath:" + picturePath);

        try {
            File file = r.getFile();
            byte[] picture = FileUtils.readFileToByteArray(file);
            String encodedPicture = Base64.encodeBase64String(picture);
            return new ResponseEntity<>(encodedPicture, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "loggedClient/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @Transactional
    public ResponseEntity<String> createNewObjectWithImage(@RequestPart("image") MultipartFile image) throws IOException {
        String pictureName="pictures/user_pictures/2.png";
        Client clientToUpdate = clientService.findById(2L);
        clientToUpdate.setPicture(pictureName);
        Client updatedClient=clientService.save(clientToUpdate);
        String picturePath=updatedClient.getPicture();
        boolean resp=saveFile("src/main/resources/",pictureName,image);
        boolean resp2=saveFile("target/classes/",pictureName,image);
        if (resp && resp2){
            return getPicture();
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean saveFile(String uploadDir, String fileName,MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            try {
                Files.delete(filePath);
            }
            catch (Exception ignored){

            }
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);

        }
    }

}
