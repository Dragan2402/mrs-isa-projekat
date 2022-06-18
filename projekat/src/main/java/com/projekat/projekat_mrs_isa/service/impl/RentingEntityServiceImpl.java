package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.OfferDTO;
import com.projekat.projekat_mrs_isa.model.Offer;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.VacationHouse;
import com.projekat.projekat_mrs_isa.repository.OfferRepository;
import com.projekat.projekat_mrs_isa.repository.RentingEntityRepository;
import com.projekat.projekat_mrs_isa.service.RentingEntityService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentingEntityServiceImpl implements RentingEntityService {
    @Autowired
    private RentingEntityRepository rentingEntityRepository;

    @Autowired
    private OfferRepository offerRepositroy;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public RentingEntity findById(Long id) {
        return rentingEntityRepository.findById(id).orElse(null);
    }

    @Override
    public void save(RentingEntity rentingEntity) {
        rentingEntityRepository.save(rentingEntity);
    }

    @Override
    public void flush() {
        rentingEntityRepository.flush();
    }

    @Override
    public List<OfferDTO> getOffersByREId(RentingEntity rentingEntity) {
        List<OfferDTO> offers=new ArrayList<>();
        for(Offer offer : offerRepositroy.getOffersByRentingEntity(rentingEntity)) {
            if (offer.getStart().compareTo(LocalDateTime.now()) > 0) {
                OfferDTO temp = new OfferDTO(offer);
                offers.add(temp);
            }else{
                offer.setDeleted(true);
            }
        }
        return offers;
    }

    @Override
    public List<String> getPicturesByRentingEntity(RentingEntity rentingEntity) {
        RentingEntity reTemp= rentingEntityRepository.findById(rentingEntity.getId()).orElse(null);
        if(reTemp==null)
            return null;

        List<String> picturePaths=reTemp.getPictures();
        List<String> encodedPictures = new ArrayList<>();

        for (String picturePath: picturePaths) {
            Resource r = resourceLoader
                    .getResource("classpath:" + picturePath);
            try {
                File file = r.getFile();
                byte[] picture = FileUtils.readFileToByteArray(file);
                String encodedPicture = Base64.encodeBase64String(picture);
                encodedPictures.add(encodedPicture);
            } catch (IOException e) {
                return null;
            }
        }
        return encodedPictures;
    }
}
