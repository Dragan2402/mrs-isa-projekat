//package com.projekat.projekat_mrs_isa.service;
//
//import com.projekat.projekat_mrs_isa.repository.UserRepository;
//import com.projekat.projekat_mrs_isa.service.impl.UserServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Test
//    public void isTokenExpiredTest(){
//        LocalDateTime dateExpired = LocalDateTime.of(2022,5,26,1,2);
//        Timestamp tokenExpirationDate= Timestamp.valueOf(dateExpired);
//        Boolean expired=userService.isTokenExpired(tokenExpirationDate);
//        LocalDateTime dateNotExpired=LocalDateTime.now();
//        Timestamp notExpired=Timestamp.valueOf(dateNotExpired);
//        Boolean notExpiredBool=userService.isTokenExpired(notExpired);
//        assertEquals(expired,true);
//        assertEquals(notExpiredBool,false);
//    }
//}
