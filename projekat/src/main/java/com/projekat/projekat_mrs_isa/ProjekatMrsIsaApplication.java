package com.projekat.projekat_mrs_isa;

import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ProjekatMrsIsaApplication implements CommandLineRunner {

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

//    @Autowired
//    private SlotRepository slotRep;



    public static void main(String[] args) {
        SpringApplication.run(ProjekatMrsIsaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Admin adminTemp= new Admin("email@gmail.com","gage","Dragan"
        ,"Mirkovic","Hopovska 4","Novi Sad","Serbia","+381692402000");
        adminRep.save(adminTemp);


        Client clientTemp1= new Client("pero@gmail.com","pero","Pero"
                ,"Peric","Jovanova 14","Novi Sad","Serbia","+38165656565");
        clientRep.save(clientTemp1);

        Client clientTemp2= new Client("jovo@gmail.com","jovo","Jovo"
                ,"Jovic","Jovanovska 19","Novi Sad","Serbia","+381656565222");
        clientRep.save(clientTemp2);

        VacationHouseOwner vacHouseOwnerTemp1= new VacationHouseOwner("dimpet96@gmail.com","dimpet","Dimitrije"
                ,"Petrov","Telep Gang","Novi Sad","Serbia","+381650000000");
        vacHouseRep.save(vacHouseOwnerTemp1);

        ShipOwner shipOwnerTemp1=new ShipOwner("krtolinat@gmail.com","tomo","Tomo"
                ,"Krtolina","Balkan","Subotica","Serbia","+381653232322");

        shipOwnerRep.save(shipOwnerTemp1);

        FishingInstructor fishingInstructorTemp=new FishingInstructor("aki96@gmail.com","asi","Andrej","Culjak","Futoska 70","Novi Sad","Serbia","+381611155777");

        fishingInstructorRep.save(fishingInstructorTemp);


        List<String> pics1 = new ArrayList<>();
        pics1.add("pictures/renting_entities/3/1.jpg");
        pics1.add("pictures/renting_entities/3/2.jpg");
        pics1.add("pictures/renting_entities/3/3.jpg");
        pics1.add("pictures/renting_entities/3/4.jpg");
        Set<Offer> offer1 = new HashSet<>();
        Set<String> nav1 = new HashSet<>();
        Set<String> fisheq1 = new HashSet<>();
        Set<String> addService = new HashSet<>();

        Ship shipTemp = new Ship(
                "Takanik",
                "Porto Montenegro",
                "Skupo je",
                pics1,
                "Zabranjeno skakanje bombe sa broda",
                "100 evara",
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
        shipOwnerTemp1.addShip(shipTemp);
        shipRep.save(shipTemp);

        FishingClass fishingClassTemp = new FishingClass(
                "Pecajmo s Milosem",
                "Palicko jezero",
                "Bice interesantno",
                pics1,
                "Zabranjeno loviti dinamitom",
                "150 evara",
                "Pice obezbedjeno",
                "Nema para nazad",
                "Instruktor upitne biografije",
                10,
                fisheq1
        );

        fishingClassTemp.setAvailableFrom(LocalDateTime.of(2021,1,1, 20, 15));
        fishingClassTemp.setAvailableTo(LocalDateTime.of(2021,2,1, 20, 30));

        fishingInstructorTemp.addFishingClass(fishingClassTemp);
        fishingClassRep.save(fishingClassTemp);

        VacationHouse vacationHouseTemp = new VacationHouse(
                "Golden Rose",
                "Rakovac",
                "Glavni kuvar Milos pravi gulas od cevapa",
                pics1,
                "Sve je dozvoljeno",
                "5000 dinara",
                "Ponesite svoje pice",
                "Nema para nazad",
                10,
                5
        );

        vacationHouseTemp.setAvailableFrom(LocalDateTime.of(2021,1,1, 20, 15));
        vacationHouseTemp.setAvailableTo(LocalDateTime.of(2021,2,1, 20, 30));

        Offer offerTemp = new Offer(
                "Brcko",
                5,
                new ArrayList<>(),
                500.0,
                vacationHouseTemp,
                LocalDateTime.now(),
                Duration.ofHours(4)
        );

        vacationHouseTemp.addOffer(offerTemp);
        vacHouseOwnerTemp1.addVacationHouse(vacationHouseTemp);
        vacationHouseRep.save(vacationHouseTemp);
        offerRep.save(offerTemp);

        //public Reservation(String firstSlot, Long slotQuantity, String place, Integer clientNum, Set<String> additionalServices, Double price) {

        Reservation reservationTemp = new Reservation(
                "Brcko",
                5,
                new HashSet<>(),
                50.0,
                vacationHouseTemp,
                clientTemp1,
                LocalDateTime.now(),
                Duration.ofDays(3)
        );

        vacationHouseTemp.addReservation(reservationTemp);
        clientTemp1.addReservation(reservationTemp);
        reservationRep.save(reservationTemp);

//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
    }
}
