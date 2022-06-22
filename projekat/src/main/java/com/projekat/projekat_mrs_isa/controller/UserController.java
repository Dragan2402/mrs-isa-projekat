package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.PasswordChangeDTO;
import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.dto.FeeDTO;
import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.Fee;
import com.projekat.projekat_mrs_isa.model.Request;
import com.projekat.projekat_mrs_isa.model.User;
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
import java.util.List;


@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VacationHouseOwnerService vacationHouseOwnerService;

    @Autowired
    private ShipOwnerService shipOwnerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;


    @GetMapping(value = "/userPreview/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserDto(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserDTO userDTO=new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/userPreview/{username}/picture", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserPicture(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String picturePath=user.getPicture();
        return new ResponseEntity<>(utilityService.getPictureEncoded(picturePath), HttpStatus.OK);
    }

    @GetMapping(value = "/loggedUser")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getLoggedUser(Principal userP) {
        User user = this.userService.findByUsername(userP.getName());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }


    //    @PostMapping(value = "/addReviewRO", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasRole('CLIENT')")
//    @Transactional
//    public ResponseEntity<Boolean> addReviewRO(Principal clientP, @RequestBody ReviewDTO reviewDTO) {
//
    @PostMapping(value = "/submitRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<Boolean> createRequest(Principal userP, @RequestBody RequestDTO requestDTO) {
        User submitter = this.userService.findByUsername(userP.getName());
        if (submitter == null) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Request request = new Request(submitter, requestDTO.getText(), requestDTO.getType());
        submitter.addRequest(request);
        Request requestSaved = requestService.save(request);
        if (requestSaved == null) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }


    @GetMapping(value = "/loggedUser/picture", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<String> getPicture(Principal userP) {

        String picturePath = userService.findByUsername(userP.getName()).getPicture();
        return new ResponseEntity<>(utilityService.getPictureEncoded(picturePath), HttpStatus.OK);

    }

    @PutMapping(value = "/loggedUser/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<String> updateLoggedUserPicture(Principal userP, @RequestPart("image") MultipartFile image) throws IOException {
        User userToUpdate = userService.findByUsername(userP.getName());
        String pictureName = "pictures/user_pictures/" + userToUpdate.getId().toString() + ".png";
        userToUpdate.setPicture(pictureName);
        User updatedUser = userService.save(userToUpdate);
        boolean resp = utilityService.saveFile("src/main/resources/", pictureName, image);
        boolean resp2 = utilityService.saveFile("target/classes/", pictureName, image);
        if (resp && resp2) {
            return new ResponseEntity<>(utilityService.getPictureEncoded(updatedUser.getPicture()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/loggedUser/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    @Transactional
    public ResponseEntity<Boolean> changePassword(Principal userP, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        User logged = userService.findByUsername(userP.getName());
        if (logged == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!utilityService.validatePasswords(passwordChangeDTO.getNewPassword(),passwordChangeDTO.getNewPasswordConfirm()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Boolean success = userService.updatePassword(logged, passwordChangeDTO);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = clientService.findAllDTO();
        users.addAll(vacationHouseOwnerService.findAllDTO());
        users.addAll(shipOwnerService.findAllDTO());
        users.addAll(fishingInstructorService.findAllDTO());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<Boolean> deleteUser(@RequestBody UserDTO userDTO) {
        User user = userService.findById(userDTO.getId());
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        user.setDeleted(true);
        userService.save(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
