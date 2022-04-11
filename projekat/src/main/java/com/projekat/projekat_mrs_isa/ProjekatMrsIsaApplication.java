package com.projekat.projekat_mrs_isa;

import com.projekat.projekat_mrs_isa.model.*;
import com.projekat.projekat_mrs_isa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        List<String> pics1 = new ArrayList<String>();

        List<Offer> offer1 = new ArrayList<Offer>();
        List<String> nav1 = new ArrayList<String>();
        List<String> fisheq1 = new ArrayList<String>();
        Set<String> addService= new HashSet<>();
        //Integer id, String firstSlot, Long slotQuantity, String place, Integer clientLimit, Set<String> additionalServices, Double price)
        Offer offerTemp = new Offer("firstSlot",5L,"Brcko",5,addService, 500.0);


        Ship shipTemp = new Ship("Russki vojeni karabil", "Snake Island", "Ima svacega", pics1, "Nema jajarenja", "100 dinara", "Ponesite svoje pice", "Nema para nazad", "Raketni razarac", 90, "1234412541", 550, 115, nav1, 55, fisheq1);
        shipOwnerTemp1.addShip(shipTemp);
        shipRep.save(shipTemp);

        FishingClass fishingClassTemp = new FishingClass("Lovim i brojim", "Plaza u kurcu mome", "Bice interesantno", pics1, "Nema jajarenja", "200 dinara", "Ponesite svoje pice", "Nema para nazad", "Triput osudjivan za pokusaj silovanja na radnom mestu", 10, fisheq1);
        fishingInstructorTemp.addFishingClass(fishingClassTemp);
        fishingClassRep.save(fishingClassTemp);

        VacationHouse vacationHouseTemp = new VacationHouse("Golden Rose", "Rakovac", "Glavni kuvar Milos pravi gulas od cevapa", pics1, "Nema jajarenja", "500 dinara", "Ponesite svoje pice", "Nema para nazad", 10, 5);
        vacationHouseTemp.addOffer(offerTemp);
        vacHouseOwnerTemp1.addVacationHouse(vacationHouseTemp);
        vacationHouseRep.save(vacationHouseTemp);
        offerRep.save(offerTemp);
    }
}
