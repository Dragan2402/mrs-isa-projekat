package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Review;
import com.projekat.projekat_mrs_isa.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review findById(Long id){return reviewRepository.findById(id).orElse(null);}


}
