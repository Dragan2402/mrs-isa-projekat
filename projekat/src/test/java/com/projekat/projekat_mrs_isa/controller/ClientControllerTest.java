//package com.projekat.projekat_mrs_isa.controller;
//
//import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
//import com.projekat.projekat_mrs_isa.constants.ClientConstants;
//import com.projekat.projekat_mrs_isa.model.Client;
//import com.projekat.projekat_mrs_isa.repository.ClientRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.nio.charset.Charset;
//
//import static com.projekat.projekat_mrs_isa.constants.ClientConstants.*;
//import static com.projekat.projekat_mrs_isa.constants.ClientConstants.DB_CLIENT_PHONE_NUM;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ClientControllerTest {
//    private static final String URL_PREFIX = "/api/clients";
//
//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype());
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private PasswordEncoderComponent passwordEncoderComponent;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Before
//    public void addUsers(){
//        Client client=new Client(DB_CLIENT_EMAIL,DB_CLIENT_USERNAME, passwordEncoderComponent.encode(DB_CLIENT_PASSWORD),DB_CLIENT_PICTURE,
//                DB_FIRST_NAME,DB_LAST_NAME,DB_CLIENT_ADDRESS,DB_CLIENT_CITY,DB_CLIENT_COUNTRY,DB_CLIENT_PHONE_NUM);
//        Client clientTemp1= new Client("jovo@maildrop.cc","jovo",passwordEncoderComponent.encode("jovo123"),"pictures/user_pictures/2.png","Jovo"
//                ,"Jovic","Jovanova 14","Novi Sad","Serbia","+38165652565");
//        clientRepository.save(client);
//        clientRepository.save(clientTemp1);
//    }
//
//    @Test
//    public void testGetAllClients()throws Exception{
//        mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
//                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$.[*].id").value(hasItem(ClientConstants.DB_ID.intValue())))
//                .andExpect(jsonPath("$.[*].username").value(hasItem(DB_CLIENT_USERNAME)))
//                .andExpect(jsonPath("$.[*].firstName").value(hasItem(ClientConstants.DB_FIRST_NAME)))
//                .andExpect(jsonPath("$.[*].lastName").value(hasItem(ClientConstants.DB_LAST_NAME)))
//                .andExpect(jsonPath("$.[*].email").value(hasItem(ClientConstants.DB_CLIENT_EMAIL)));
//    }
//
//
//}
