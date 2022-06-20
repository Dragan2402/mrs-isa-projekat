package com.projekat.projekat_mrs_isa;

import com.projekat.projekat_mrs_isa.config.PasswordEncoderComponent;
import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.*;
import com.projekat.projekat_mrs_isa.service.ComplaintService;
import com.projekat.projekat_mrs_isa.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableAsync
public class ProjekatMrsIsaApplication implements CommandLineRunner  {

    @Autowired
    private ClientRepository clientRep;

    @Autowired
    private VacationHouseOwnerRepository vacHouseRep;

    @Autowired
    private AdminRepository adminRep;

    @Autowired
    private ShipOwnerRepository shipOwnerRep;

    @Autowired
    private FishingInstructorRepository fishingInstructorRep;

    @Autowired
    private OfferRepository offerRep;

    @Autowired
    private ShipRepository shipRep;

    @Autowired
    private FishingClassRepository fishingClassRep;

    @Autowired
    private VacationHouseRepository vacationHouseRep;

    @Autowired
    private ReservationRepository reservationRep;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoderComponent passwordEncoderComponent;

    @Autowired
    private ComplaintService complaintService;



    public static void main(String[] args) {
        SpringApplication.run(ProjekatMrsIsaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin=new Role("ROLE_ADMIN");
        Role roleClient= new Role("ROLE_CLIENT");
        Role roleShipOwner= new Role("ROLE_SHIP_OWNER");
        Role roleVhOwner= new Role("ROLE_VH_OWNER");
        Role roleFcInstructor= new Role("ROLE_FC_INSTRUCTOR");

        roleRepository.save(roleAdmin);
        roleRepository.save(roleClient);
        roleRepository.save(roleShipOwner);
        roleRepository.save(roleVhOwner);
        roleRepository.save(roleFcInstructor);

        List<Role> rolesAdmin = roleRepository.findByName("ROLE_ADMIN");
        List<Role> rolesClient = roleRepository.findByName("ROLE_CLIENT");
        List<Role> rolesShipOwner = roleRepository.findByName("ROLE_SHIP_OWNER");
        List<Role> rolesVHOwner = roleRepository.findByName("ROLE_VH_OWNER");
        List<Role> rolesFCInstructor = roleRepository.findByName("ROLE_FC_INSTRUCTOR");


        Admin adminTemp= new Admin("email@maildrop.cc","gageMadjija",passwordEncoderComponent.encode("Gage123"),"pictures/user_pictures/0.png","Dragan"
        ,"Mirkovic","Hopovska 4","Novi Sad","Serbia","+381692402000");
        adminTemp.setVerified(true);
        adminTemp.setRoles(rolesAdmin);
        adminRep.save(adminTemp);


        Client clientTemp1= new Client("peropero@maildrop.cc","peroPero",passwordEncoderComponent.encode("Pero123"),"pictures/user_pictures/2.png","Pero"
                ,"Peric","Jovanova 14","Novi Sad","Serbia","+38165656565");
        clientTemp1.setVerified(true);
        clientTemp1.setRoles(rolesClient);
        clientTemp1.addPenalty();
        clientTemp1.addPenalty();
        clientTemp1.addPenalty();
        clientTemp1.addPenalty();
        clientRep.save(clientTemp1);

        Client clientTemp2= new Client("jovo@maildrop.cc","jovo",passwordEncoderComponent.encode("Jovo123"),"pictures/user_pictures/0.png","Jovo"
                ,"Jovic","Jovanovska 19","Novi Sad","Serbia","+381656565222");
        clientTemp2.setVerified(true);
        clientTemp2.setRoles(rolesClient);
        clientRep.save(clientTemp2);

        List<String> vacHouseOwner1AddSrvc=new ArrayList<>();
        vacHouseOwner1AddSrvc.add("Book a Grill master");
        vacHouseOwner1AddSrvc.add("Rent a Quad");
        vacHouseOwner1AddSrvc.add("AC");
        VacationHouseOwner vacHouseOwnerTemp1= new VacationHouseOwner("dimpet96@maildrop.cc","dimitrije",passwordEncoderComponent.encode("Dimpet123"),"pictures/user_pictures/0.png","Dimitrije"
                ,"Petrov","Somborska 55","Novi Sad","Serbia","+381650000000",vacHouseOwner1AddSrvc);
        vacHouseOwnerTemp1.setVerified(true);
        vacHouseOwnerTemp1.setRoles(rolesVHOwner);
        vacHouseRep.save(vacHouseOwnerTemp1);


        List<String> shipOwnerServices=new ArrayList<>();
        shipOwnerServices.add("Sandwiches");
        shipOwnerServices.add("Sailor outfits");
        ShipOwner shipOwnerTemp1=new ShipOwner("krtolinat@maildrop.cc","tkrtolina",passwordEncoderComponent.encode("Tomo123"),"pictures/user_pictures/0.png","Tomo"
                ,"Krtolina","Balkan","Subotica","Serbia","+381653232322",shipOwnerServices);
        shipOwnerTemp1.setVerified(true);
        shipOwnerTemp1.setRoles(rolesShipOwner);
        shipOwnerRep.save(shipOwnerTemp1);


        List<String> fishingInstructorAddSrvcs=new ArrayList<>();
        fishingInstructorAddSrvcs.add("Fishing Rods");
        fishingInstructorAddSrvcs.add("Bites");
        FishingInstructor fishingInstructorTemp=new FishingInstructor("aki96@maildrop.cc","AkiKavasaki",passwordEncoderComponent.encode("Asiasi123"),"pictures/user_pictures/0.png","Andrej","Culjak","Futoska 70","Novi Sad","Serbia","+381611155777",fishingInstructorAddSrvcs);
        fishingInstructorTemp.setVerified(true);
        fishingInstructorTemp.setRoles(rolesFCInstructor);
        fishingInstructorRep.save(fishingInstructorTemp);

        List<String> vacHouseOwner2AddSrvc=new ArrayList<>();
        vacHouseOwner2AddSrvc.add("TV");
        vacHouseOwner2AddSrvc.add("Bikes");
        vacHouseOwner2AddSrvc.add("AC");
        VacationHouseOwner vacHouseOwnerTemp2= new VacationHouseOwner("bokiboki@maildrop.cc","Boris",passwordEncoderComponent.encode("Boki222"),"pictures/user_pictures/0.png","Boris"
                ,"Brejca","Tuzna ulica","Tuzni Grad","Serbia","+381650000111",vacHouseOwner2AddSrvc);
        vacHouseOwnerTemp2.setVerified(true);
        vacHouseOwnerTemp2.setRoles(rolesVHOwner);
        vacHouseRep.save(vacHouseOwnerTemp2);


        List<String> pics1 = new ArrayList<>();
        pics1.add("pictures/renting_entities/3/1.jpg");
        pics1.add("pictures/renting_entities/3/2.jpg");
        pics1.add("pictures/renting_entities/3/3.jpg");
        pics1.add("pictures/renting_entities/3/4.jpg");

        Set<String> nav1 = new HashSet<>();
        Set<String> fisheq1 = new HashSet<>();

        List<String> picsShip=new ArrayList<>();
        picsShip.add("pictures/renting_entities/1/1.jpg");
        picsShip.add("pictures/renting_entities/1/2.jpg");

        Ship shipTemp = new Ship(
                "Takanik",
                "Porto Montenegro",
                "Skupo je",
                picsShip,
                "Zabranjeno skakanje bombe sa broda",
                100d,
                "Zabranjeni kerovi",
                "Nema para nazad",
                "Ledolomac",
                90,
                "1234412541",
                550,
                115,
                nav1,
                55,
                fisheq1
        );
        shipTemp.setAvailableFrom(LocalDateTime.now());
        shipTemp.setAvailableTo(LocalDateTime.now().plus(Duration.ofDays(20)));
        shipOwnerTemp1.addShip(shipTemp);

        List<String> services=new ArrayList<>();
        services.add("None");
        Offer offerTemp5 = new Offer(
                "Budva",
                2,
                services,
                300.0,
                shipTemp,
                LocalDateTime.of(2022,7,24,20,10),
                Duration.ofHours(20)
        );

        shipTemp.addOffer(offerTemp5);

        shipRep.save(shipTemp);

        List<String> fishingPics=new ArrayList<>();
        fishingPics.add("pictures/renting_entities/2/1.jpg");
        fishingPics.add("pictures/renting_entities/2/2.jpg");

        FishingClass fishingClassTemp = new FishingClass(
                "Pecajmo s Milosem",
                "Palicko jezero",
                "Bice interesantno",
                fishingPics,
                "Zabranjeno loviti dinamitom",
                150d,
                "Pice obezbedjeno",
                "Nema para nazad",
                "Instruktor upitne biografije",
                10,
                fisheq1
        );

        fishingClassTemp.setAvailableFrom(LocalDateTime.of(2021,9,1, 20, 15));
        fishingClassTemp.setAvailableTo(LocalDateTime.of(2021,11,1, 20, 30));

        fishingInstructorTemp.addFishingClass(fishingClassTemp);

        Offer offerTemp4 = new Offer(
                "Jezero",
                5,
                services,
                300.0,
                fishingClassTemp,
                LocalDateTime.of(2022,7,24,20,10),
                Duration.ofHours(20)
        );

        fishingClassTemp.addOffer(offerTemp4);
        fishingClassRep.save(fishingClassTemp);

        VacationHouse vacationHouseTemp = new VacationHouse(
                "Golden Rose",
                "Rakovac",
                "Glavni kuvar Milos pravi gulas od cevapa",
                pics1,
                "Sve je dozvoljeno",
                75d,
                "Ponesite svoje pice",
                "Nema para nazad",
                10,
                5
        );

        vacationHouseTemp.setAvailableFrom(LocalDateTime.of(2022,8,2, 20, 15));
        vacationHouseTemp.setAvailableTo(LocalDateTime.of(2022,10,1, 20, 30));

        List<String> pics2 = new ArrayList<>();
        pics2.add("pictures/renting_entities/4/1.jpg");
        pics2.add("pictures/renting_entities/4/2.jpg");
        pics2.add("pictures/renting_entities/4/3.jpg");
        pics2.add("pictures/renting_entities/4/4.jpg");
        pics2.add("pictures/renting_entities/4/5.jpg");
        pics2.add("pictures/renting_entities/4/6.jpg");
        VacationHouse vacationHouseTemp2 = new VacationHouse(
                "Angel’s Nest",
                "Beograd",
                "Uzivajte u full vili" ,
                pics2,
                "Sve je dozvoljeno",
                80d,
                "Spa je ukljucen u cenu!!!",
                "3 Dana ranije",
                10,
                2
        );
        vacationHouseTemp2.setAvailableFrom(LocalDateTime.of(2022,7,1, 20, 15));
        vacationHouseTemp2.setAvailableTo(LocalDateTime.of(2022,10,1, 20, 30));

        List<String> pics3=new ArrayList<>();
        pics3.add("pictures/renting_entities/5/1.jpg");
        pics3.add("pictures/renting_entities/5/2.jpg");
        pics3.add("pictures/renting_entities/5/3.jpg");
        pics3.add("pictures/renting_entities/5/4.jpg");

        VacationHouse vacationHouseTemp3= new VacationHouse("Spa Vila Avala","Suplja stena","Spa Vila Avala se nalazi u podnožju istoimene planine u vikend naselju Šuplja stena koje je poznato po čistoći vazduha, " +
                        "usled uvek prisutne ruže vetrova.",pics3,"NO DRINKING",50d,"Mini bar ne radi","3 dana ranije",2,4);


        vacationHouseTemp3.setAvailableFrom(LocalDateTime.of(2022,9,1, 20, 15));
        vacationHouseTemp3.setAvailableTo(LocalDateTime.of(2022,11,30, 20, 30));

        vacHouseOwnerTemp2.addVacationHouse(vacationHouseTemp3);

        vacationHouseRep.save(vacationHouseTemp3);
        Offer offerTemp = new Offer(
                "Brcko",
                5,
                services,
                500.0,
                vacationHouseTemp,
                LocalDateTime.of(2022,6,24,20,10),
                Duration.ofHours(4)
        );

        Offer offerTemp2 = new Offer(
                "Brcko",
                5,
                services,
                500.0,
                vacationHouseTemp,
                LocalDateTime.of(2022,1,24,20,10),
                Duration.ofHours(3)
        );

        Offer offerTemp3 = new Offer(
                "Brcko Ficibajer",
                5,
                services,
                300.0,
                vacationHouseTemp,
                LocalDateTime.of(2022,7,24,20,10),
                Duration.ofHours(20)
        );

        Offer offerTemp6 = new Offer(
                "Brcko Ficibajer",
                5,
                new ArrayList<>(),
                300.0,
                vacationHouseTemp,
                LocalDateTime.of(2022,7,24,20,10),
                Duration.ofHours(20)
        );


        vacationHouseTemp.addOffer(offerTemp3);
        vacationHouseTemp.addOffer(offerTemp);
        vacationHouseTemp.addOffer(offerTemp2);
        vacationHouseTemp.addOffer(offerTemp6);
        vacHouseOwnerTemp1.addVacationHouse(vacationHouseTemp);
        vacHouseOwnerTemp1.addVacationHouse(vacationHouseTemp2);



        vacationHouseRep.save(vacationHouseTemp);
        vacationHouseRep.save(vacationHouseTemp2);
        Review reviewTemp1=new Review(vacationHouseTemp2,clientTemp1,4.6);
        Review reviewTemp2=new Review(vacationHouseTemp2,clientTemp1,5.0,"Sve u fullu, ispostovano");
        Review reviewTemp3=new Review(vacationHouseTemp2,clientTemp1,4.8);
        Review reviewTemp4=new Review(vacHouseOwnerTemp1,clientTemp1,5.0,"NAJJACI");
        clientTemp1.addReview(reviewTemp4);
        vacHouseOwnerTemp1.addReview(reviewTemp4);
        vacHouseRep.save(vacHouseOwnerTemp1);

        Reservation r = new Reservation( "Rakovac", 5, new ArrayList<>(), 100.0,
                vacationHouseTemp, clientTemp2,
                LocalDateTime.of(2022,3,1, 20, 15), Duration.ofDays(3));
        reservationRep.save(r);


        vacationHouseTemp2.addReview(reviewTemp1);
        vacationHouseTemp2.addReview(reviewTemp2);
        vacationHouseTemp2.addReview(reviewTemp3);
        clientTemp1.addReview(reviewTemp1);
        clientTemp1.addReview(reviewTemp2);
        clientTemp1.addReview(reviewTemp3);
        vacationHouseRep.save(vacationHouseTemp2);


        Complaint complaintTemp1=new Complaint(clientTemp1,vacationHouseTemp,"UZASAN SMRAD UBIO ME");
        Complaint complaintTemp2=new Complaint(clientTemp1,vacHouseOwnerTemp1,"NULA OD COVJEKA UZAS");
        Complaint complaintTemp3=new Complaint(vacHouseOwnerTemp1,clientTemp1,"Nije ispostovan dogovor");
        clientTemp1.sendComplaint(complaintTemp1);
        vacationHouseTemp.addComplaint(complaintTemp1);
        clientTemp1.sendComplaint(complaintTemp2);
        vacHouseOwnerTemp1.recieveComplaint(complaintTemp2);
        vacHouseOwnerTemp1.sendComplaint(complaintTemp3);
        clientTemp1.recieveComplaint(complaintTemp3);

        complaintService.save(complaintTemp1);
        complaintService.save(complaintTemp2);
        complaintService.save(complaintTemp3);


        offerRep.save(offerTemp);

        Reservation reservationTemp = new Reservation(
                "Brcko",
                5,
                services,
                50.0,
                vacationHouseTemp,
                clientTemp1,
                LocalDateTime.of(2022,1,24,20,10),
                Duration.ofDays(3)
        );

        Reservation reservationTemp2 = new Reservation(
                "Brcko Fic",
                5,
                services,
                50.0,
                vacationHouseTemp,
                clientTemp1,
                LocalDateTime.of(2022,9,24,20,10),
                Duration.ofDays(3)
        );

        Reservation reservationTemp3 = new Reservation(
                "Brcko Fic basta",
                5,
                services,
                50.0,
                vacationHouseTemp,
                clientTemp1,
                LocalDateTime.of(2022,5,20,20,10),
                Duration.ofDays(3)
        );

        vacationHouseTemp.addReservation(reservationTemp3);
        clientTemp1.addReservation(reservationTemp3);
        reservationRep.save(reservationTemp3);

        vacationHouseTemp.addReservation(reservationTemp);
        clientTemp1.addReservation(reservationTemp);
        reservationRep.save(reservationTemp);

        vacationHouseTemp.addReservation(reservationTemp2);
        clientTemp1.addReservation(reservationTemp2);
        reservationRep.save(reservationTemp2);
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        List<String> pics4=new ArrayList<>();
        pics4.add("pictures/renting_entities/6/1.jpg");
        pics4.add("pictures/renting_entities/6/2.jpg");
        pics4.add("pictures/renting_entities/6/3.jpg");


        Ship shipTemp2 = new Ship(
                "Gondola Cruzing",
                "Venecija D Italija",
                "Lakani kruzing",
                pics4,
                "Zabranjen ulazak bez 5L ruma",
                300d,
                "Zabranjeno driftovanje",
                "3 dana pred kraj",
                "Gondola",
                5,
                "000000000",
                120,
                34,
                nav1,
                4,
                fisheq1
        );
        shipTemp2.setAvailableFrom(LocalDateTime.of(2021,8,1, 20, 15));
        shipTemp2.setAvailableTo(LocalDateTime.of(2021,11,1, 20, 30));
        shipOwnerTemp1.addShip(shipTemp2);
        shipRep.save(shipTemp2);


        List<String> pics5=new ArrayList<>();
        pics5.add("pictures/renting_entities/7/1.jpg");
        pics5.add("pictures/renting_entities/7/2.jpg");
        pics5.add("pictures/renting_entities/7/3.jpg");
        pics5.add("pictures/renting_entities/7/4.jpg");
        pics5.add("pictures/renting_entities/7/5.jpg");
        pics5.add("pictures/renting_entities/7/6.jpg");

        FishingClass fishingClassTemp2 = new FishingClass(
                "Fishing with the best",
                "Lednicko jezero",
                "Nocno pecanje po danu",
                pics5,
                "Zabranjene varalice",
                50d,
                "Pice obezbedjeno",
                "3 dana ranije",
                "Instruktor brz najbri",
                10,
                fisheq1
        );

        fishingClassTemp2.setAvailableFrom(LocalDateTime.of(2022,8,1, 20, 15));
        fishingClassTemp2.setAvailableTo(LocalDateTime.of(2022,9,1, 20, 30));
        fishingInstructorTemp.addFishingClass(fishingClassTemp2);
        fishingClassRep.save(fishingClassTemp2);


        Request requestTemp=new Request(clientTemp1,"Text",RequestType.DELETE_ACCOUNT);
        Request requestTemp2=new Request(vacHouseOwnerTemp1,"Test test",RequestType.BECOME_VH_OWNER);
        Request requestTemp3=new Request(shipOwnerTemp1,"",RequestType.DELETE_ACCOUNT);
        requestService.save(requestTemp);
        requestService.save(requestTemp2);
        requestService.save(requestTemp3);

        clientTemp1.addSubscription(vacationHouseTemp);
        clientTemp1.addSubscription(vacationHouseTemp2);
        clientRep.save(clientTemp1);



        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);




    }
}
