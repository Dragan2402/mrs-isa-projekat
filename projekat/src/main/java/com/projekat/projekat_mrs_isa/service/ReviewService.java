package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Review;
import com.projekat.projekat_mrs_isa.model.User;
import com.projekat.projekat_mrs_isa.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




public interface ReviewService {


    Review findById(Long id);

    Review save(Review review);


}
