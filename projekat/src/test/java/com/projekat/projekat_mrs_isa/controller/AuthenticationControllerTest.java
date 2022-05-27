package com.projekat.projekat_mrs_isa.controller;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.constants.ClientConstants;
import com.projekat.projekat_mrs_isa.dto.JwtAuthenticationRequestDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.repository.ClientRepository;
import com.projekat.projekat_mrs_isa.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.projekat.projekat_mrs_isa.constants.ClientConstants.*;
import static com.projekat.projekat_mrs_isa.constants.ClientConstants.DB_CLIENT_PHONE_NUM;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {
    private static final String URL_PREFIX = "/api/auth";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Before
    public void addUsers(){
        Client client=new Client(DB_CLIENT_EMAIL,DB_CLIENT_USERNAME, passwordEncoderComponent.encode(DB_CLIENT_PASSWORD),DB_CLIENT_PICTURE,
                DB_FIRST_NAME,DB_LAST_NAME,DB_CLIENT_ADDRESS,DB_CLIENT_CITY,DB_CLIENT_COUNTRY,DB_CLIENT_PHONE_NUM);
        Client clientTemp1= new Client("jovo@maildrop.cc","jovo",passwordEncoderComponent.encode("jovo123"),"pictures/user_pictures/2.png","Jovo"
                ,"Jovic","Jovanova 14","Novi Sad","Serbia","+38165652565");
        clientTemp1.setVerified(true);
        clientRepository.save(client);
        clientRepository.save(clientTemp1);
    }


    @Test
    public void testLogin() throws Exception {
        String contentType = String.valueOf(MediaType.APPLICATION_JSON);
        JwtAuthenticationRequestDTO login=new JwtAuthenticationRequestDTO("jovo","jovo123");
        String json= TestUtil.json(login);
        String authzToken = mockMvc.perform(MockMvcRequestBuilders.post(URL_PREFIX+"/login").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.accessToken").value(is(notNullValue())))
                .andReturn().getResponse().getContentAsString();


    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteStudent() throws Exception {
        int id=1*41+105;
        String url=URL_PREFIX+"/verify/"+id;
        String status=this.mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andReturn().getResponse().getContentAsString();
        Boolean statusBool= Boolean.valueOf(status);
        assertEquals(statusBool,true);
    }
}
