package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.ReservationDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
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
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VacationHouseService vacationHouseService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private FishingClassService fishingClassService;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<List<UserDTO>> getAllClients() {
        List<UserDTO> clients = clientService.findAllDTO();

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getClientDTOById(@PathVariable("id") Long id) {
        UserDTO clientDTO = clientService.findUserDTO(id);
        if (clientDTO == null)
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<UserDTO>(clientDTO,HttpStatus.OK);
    }




    @GetMapping(value = "/loggedClient",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UserDTO> getLoggedClient(Principal clientP) throws Exception{
        UserDTO clientDTO = new UserDTO(clientService.findByUsername(clientP.getName()));
        if (clientDTO == null)
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<UserDTO>(clientDTO,HttpStatus.OK);
    }
    @GetMapping(value = "/loggedClient/reservationHistory",produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getLoggedClientReservationHistory(Principal clientP) throws Exception{
        Client client = clientService.findByUsername(clientP.getName());
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<ReservationDTO> reservationsDtos= new ArrayList<>();
        for(Reservation reservation : client.getReservations()){
            if(reservation.getStart().plus(reservation.getDuration()).compareTo(LocalDateTime.now())<0){
                ReservationDTO reservationDTO=new ReservationDTO(reservation);
                reservationDTO.setRentingEntityOwmer(getRentingEntityOwner(reservation.getRentingEntity()));
                reservationsDtos.add(reservationDTO);
                System.out.println(reservation);
            }
        }
        return new ResponseEntity<>(reservationsDtos,HttpStatus.OK);
    }


    @PostMapping(value = "/loggedClient",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<UserDTO> updateLoggedClient(Principal clientP,@RequestBody UserDTO userDTO) throws Exception{

        if(utilityService.validateName(userDTO.getFirstName()) && utilityService.validateName(userDTO.getLastName())
               && userDTO.getAddress().length()>2 &&
                userDTO.getCity().length()>2 && userDTO.getCountry().length()>2 && utilityService.validatePhoneNum(userDTO.getPhoneNum())){
            Client clientToUpdate = clientService.findByUsername(clientP.getName());
            clientToUpdate.update(userDTO);
            Client updatedCLient = clientService.save(clientToUpdate);



            if (updatedCLient == null)
                return new  ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            return new  ResponseEntity<UserDTO>(new UserDTO(updatedCLient),HttpStatus.OK);}
        else{
            System.out.println("DATA IS BAD");
            return new ResponseEntity<>(new UserDTO(),HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping(value = "/loggedClient/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional
    public ResponseEntity<String> updateLoggedClientPicture(Principal clientP,@RequestPart("image") MultipartFile image) throws IOException {

        Client clientToUpdate = clientService.findByUsername(clientP.getName());
        String pictureName="pictures/user_pictures/"+clientToUpdate.getId().toString()+".png";
        clientToUpdate.setPicture(pictureName);
        Client updatedClient=clientService.save(clientToUpdate);

        boolean resp=saveFile("src/main/resources/",pictureName,image);
        boolean resp2=saveFile("target/classes/",pictureName,image);
        if (resp && resp2){
            return new ResponseEntity<>(utilityService.getPictureEncoded(updatedClient.getPicture()),HttpStatus.OK);
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

    private String getRentingEntityOwner(RentingEntity rentingEntity){


            if(rentingEntity.getREType().equals("VH")){
                return vacationHouseService.findById(rentingEntity.getId()).getVacationHouseOwner().getUsername();
            }else if(rentingEntity.getREType().equals("FC")){
                return fishingClassService.findById(rentingEntity.getId()).getFishingInstructor().getUsername();
            }
            else{
                return shipService.findById(rentingEntity.getId()).getShipOwner().getUsername();
            }

    }

}
