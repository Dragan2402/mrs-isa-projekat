package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.RequestDTO;
import com.projekat.projekat_mrs_isa.dto.ReviewDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Request;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.service.RequestService;
import com.projekat.projekat_mrs_isa.service.UserService;
import com.projekat.projekat_mrs_isa.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import java.security.Principal;


@RestController
@CrossOrigin
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UtilityService utilityService;

    @GetMapping(value = "/loggedUser")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getLoggedUser(Principal userP) {
        User user = this.userService.findByUsername(userP.getName());
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
}
