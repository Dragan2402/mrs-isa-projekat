package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/loggedUser")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT','SHIP_OWNER','VH_OWNER','FC_INSTRUCTOR')")
    public ResponseEntity<UserDTO> getLoggedUser(Principal userP){
        User user= this.userService.findByUsername(userP.getName());
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
}
