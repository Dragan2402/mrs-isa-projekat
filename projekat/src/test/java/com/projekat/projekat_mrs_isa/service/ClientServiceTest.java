package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Client;
import com.projekat.projekat_mrs_isa.repository.ClientRepository;
import com.projekat.projekat_mrs_isa.service.impl.ClientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.projekat.projekat_mrs_isa.constants.ClientConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest implements CommandLineRunner {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private Client clientMock;

    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    public void testFindAll() {

//public Client(String email, String username, String password, String picture, String firstName, String lastName, String address, String city,
//              String country, String phoneNum) {
        Client client=new Client(DB_CLIENT_EMAIL,DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD,DB_CLIENT_PICTURE,
                DB_CLIENT_NAME,DB_CLIENT_LASTNAME,DB_CLIENT_ADDRESS,DB_CLIENT_CITY,DB_CLIENT_COUNTRY,DB_CLIENT_PHONE_NUM);
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));

        List<Client> clients = clientService.findAll();


        assertThat(clients).hasSize(1);
        assertEquals(clients.get(0).getFirstName(), DB_CLIENT_NAME);

        verify(clientRepository, times(1)).findAll();
        verifyNoMoreInteractions(clientRepository);
    }


    @Override
    public void run(String... args) throws Exception {
        Client clientTemp1= new Client("peropero@maildrop.cc","peroPero","Pero123","pictures/user_pictures/2.png","Pero"
                ,"Peric","Jovanova 14","Novi Sad","Serbia","+38165656565");
        clientTemp1.setVerified(true);
        clientRepository.save(clientTemp1);
    }
}
