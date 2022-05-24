package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.model.Review;

public interface ReviewService {


    Review findById(Long id);

    Review save(Review review);


}
