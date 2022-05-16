package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.JwtAuthenticationRequestDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.dto.UserTokenStateDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.service.ClientService;
import com.projekat.projekat_mrs_isa.service.EmailService;
import com.projekat.projekat_mrs_isa.service.UserService;
import com.projekat.projekat_mrs_isa.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequestDTO authenticationRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));


        User userTemp= (User) authentication.getPrincipal();
        if(!userTemp.isVerified()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }

    @PostMapping(value ="/addClient",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addUser(@RequestBody Map<String,Object> userMap){
        String firstName,lastName,email,password,confirmPassword,address,city,country,phoneNum,username;
        if(!UtilityController.containsAll(userMap))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        firstName = (String) userMap.get("firstName");
        lastName = (String) userMap.get("lastName");
        email = (String) userMap.get("email");
        username = (String) userMap.get("username");
        password = (String) userMap.get("password");
        confirmPassword = (String) userMap.get("confirmPassword");
        address = (String) userMap.get("address");
        city = (String) userMap.get("city");
        country = (String) userMap.get("country");
        phoneNum = (String) userMap.get("phoneNum");
        if(UtilityController.validateName(firstName) && UtilityController.validateName(lastName)&& userService.isUsernameAvailable(username) && UtilityController.validateEmail(email) && userService.isUsernameAvailable(email.toLowerCase())
                && UtilityController.validatePasswords(password,confirmPassword) && address.length()>2 &&
                city.length()>2 && country.length()>2 && UtilityController.validatePhoneNum(phoneNum)){
            Client clientTemp=clientService.addClient(email.toLowerCase(),username,password,"pictures/user_pictures/0.png",firstName,lastName,address,city,country,phoneNum);
            emailService.sendVerificationMail(new UserDTO(clientTemp));
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }else {
            System.out.println("DATA IS BAD");
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/verify/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> verifyAccount(@PathVariable("id") Long customId){
        long id=(customId-105)/41;
        User user=userService.findById(id);
        if(user == null)
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
        user.setVerified(true);
        User userSaved=userService.save(user);
        if(userSaved==null)
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    // Endpoint za registraciju novog korisnika
//    @PostMapping("/signup")
//    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {
//
//        User existUser = this.userService.findByUsername(userRequest.getUsername());
//
//        if (existUser != null) {
//            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
//        }
//
//        User user = this.userService.save(userRequest);
//
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }
}
