package com.projekat.projekat_mrs_isa.controller;


import com.projekat.projekat_mrs_isa.dto.JwtAuthenticationRequestDTO;
import com.projekat.projekat_mrs_isa.dto.PasswordResetDTO;
import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.dto.UserTokenStateDTO;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.service.*;
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
@CrossOrigin
@RequestMapping(value = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VacationHouseOwnerService vacationHouseOwnerService;

    @Autowired
    private ShipOwnerService shipOwnerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

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
        if(!utilityService.validateUserData(userMap))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        String username = (String) userMap.get("username");
        String email = (String) userMap.get("email");
        if(userService.isUsernameAvailable(username) && userService.isUsernameAvailable(email.toLowerCase())) {
            Client clientTemp = clientService.addClient(
                    email.toLowerCase(),
                    username,
                    (String) userMap.get("password"),
                    "pictures/user_pictures/0.png",
                    (String) userMap.get("firstName"),
                    (String) userMap.get("lastName"),
                    (String) userMap.get("address"),
                    (String) userMap.get("city"),
                    (String) userMap.get("country"),
                    (String) userMap.get("phoneNum")
            );
            emailService.sendVerificationMail(new UserDTO(clientTemp));
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value ="/addVacationHouseOwner",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addVacationHouseOwner(@RequestBody Map<String,Object> userMap){
        if(!utilityService.validateUserData(userMap) ||
                !userMap.containsKey("registrationReason"))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        String registrationReason = (String) userMap.get("registrationReason");
        if(registrationReason.equals(""))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        if(!(userService.isUsernameAvailable((String) userMap.get("username"))
                && userService.isUsernameAvailable(((String) userMap.get("email")).toLowerCase()))) {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        VacationHouseOwner vho = vacationHouseOwnerService.addVacationHouseOwner(userMap);
        Request registrationRequest = new Request(vho, registrationReason, RequestType.BECOME_VH_OWNER);
        requestService.save(registrationRequest);
        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }

    @PostMapping(value = "/addShipOwner", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addShipOwner(@RequestBody Map<String, Object> userMap){
        if(!utilityService.validateOwnerData(userMap))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        if(!(userService.isUsernameAvailable((String) userMap.get("username"))
                && userService.isUsernameAvailable(((String) userMap.get("email")).toLowerCase()))) {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        ShipOwner so = shipOwnerService.addShipOwner(userMap);
        String registrationReason = (String) userMap.get("registrationReason");
        Request registrationRequest = new Request(so, registrationReason, RequestType.BECOME_SH_OWNER);
        requestService.save(registrationRequest);
        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }

    @PostMapping(value = "/addFishingInstructor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addFishingInstructor(@RequestBody Map<String, Object> userMap){
        if(!utilityService.validateOwnerData(userMap))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

        if(!(userService.isUsernameAvailable((String) userMap.get("username"))
                && userService.isUsernameAvailable(((String) userMap.get("email")).toLowerCase()))) {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        FishingInstructor fi = fishingInstructorService.addFishingInstructor(userMap);
        String registrationReason = (String) userMap.get("registrationReason");
        Request registrationRequest = new Request(fi, registrationReason, RequestType.BECOME_INSTRUCTOR);
        requestService.save(registrationRequest);
        return new ResponseEntity<>(true,HttpStatus.CREATED);
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

    @GetMapping(value = "/isMailAvailable/{mail}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isMailAvailableRequest(@PathVariable("mail") String mail){
        return new ResponseEntity<>(userService.isMailAvailable(mail),HttpStatus.OK);
    }

    @GetMapping(value = "/isUsernameAvailable/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUsernameAvailableRequest(@PathVariable("username") String username){
        return new ResponseEntity<>(userService.isUsernameAvailable(username),HttpStatus.OK);
    }

    @GetMapping(value = "/sendResetPassword/{mail}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> sendResetPasswordMail(@PathVariable("mail") String mail){
        return new ResponseEntity<>(userService.sendResetPassword(mail),HttpStatus.OK);
    }

    @PutMapping(value = "/resetPassword" , produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO){
        if(!utilityService.validatePasswords(passwordResetDTO.getNewPassword(),passwordResetDTO.getConfirmPassword()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.resetPassword(passwordResetDTO),HttpStatus.OK);
    }
}
