//package com.projekat.projekat_mrs_isa.service;
//
//import com.projekat.projekat_mrs_isa.model.Client;
//import com.projekat.projekat_mrs_isa.model.VacationHouse;
//import com.projekat.projekat_mrs_isa.repository.ClientRepository;
//import com.projekat.projekat_mrs_isa.repository.RentingEntityRepository;
//import com.projekat.projekat_mrs_isa.service.impl.ClientServiceImpl;
//import com.projekat.projekat_mrs_isa.service.impl.RentingEntityServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static com.projekat.projekat_mrs_isa.constants.ClientConstants.*;
//import static com.projekat.projekat_mrs_isa.constants.RentingEntityConstants.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ClientServiceTest  {
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    @Mock
//    private RentingEntityRepository rentingEntityRepository;
//
//    @Mock
//    private Client clientMock;
//
//    @InjectMocks
//    private ClientServiceImpl clientService;
//
//
//    @Test
//    public void testFindAll() {
//
//        Client client=new Client(DB_CLIENT_EMAIL,DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD,DB_CLIENT_PICTURE,
//                DB_CLIENT_NAME,DB_CLIENT_LASTNAME,DB_CLIENT_ADDRESS,DB_CLIENT_CITY,DB_CLIENT_COUNTRY,DB_CLIENT_PHONE_NUM);
//        when(clientRepository.findAll()).thenReturn(Arrays.asList(client));
//
//        List<Client> clients = clientService.findAll();
//
//
//        assertThat(clients).hasSize(1);
//        assertEquals(clients.get(0).getFirstName(), DB_CLIENT_NAME);
//
//        verify(clientRepository, times(1)).findAll();
//        verifyNoMoreInteractions(clientRepository);
//    }
//
//    @Test
//    public void testSubscribe(){
//        Client client=new Client(DB_CLIENT_EMAIL,DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD,DB_CLIENT_PICTURE,
//                DB_CLIENT_NAME,DB_CLIENT_LASTNAME,DB_CLIENT_ADDRESS,DB_CLIENT_CITY,DB_CLIENT_COUNTRY,DB_CLIENT_PHONE_NUM);
//        VacationHouse vacationHouseTemp = new VacationHouse(
//                DB_RE_NAME,
//                DB_RE_ADDRESS,
//                DB_RE_PROMO_DESC,
//                new ArrayList<>(),
//                DB_RE_BR,
//                DB_RE_PRICE,
//                DB_RE_ADD_INFO,
//                DB_RE_CANC_COND,
//                DB_RE_ROOMS,
//                DB_RE_BEDS
//        );
//        when(rentingEntityRepository.findById(1L)).thenReturn(Optional.of(vacationHouseTemp));
//
//        Boolean subscribed=clientService.subscribe(client,1L);
//        Boolean isSubscribed=clientService.isSubscribed(client,1L);
//
//        assertEquals(subscribed,true);
//        assertEquals(subscribed,isSubscribed);
//
//        verify(rentingEntityRepository, times(2)).findById(1L);
//        verifyNoMoreInteractions(rentingEntityRepository);
//    }
//
//
//
//
//
//}
