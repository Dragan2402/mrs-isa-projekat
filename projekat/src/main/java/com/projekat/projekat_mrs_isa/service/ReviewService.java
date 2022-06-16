package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.ReviewDTO;
import com.projekat.projekat_mrs_isa.model.Review;

import java.util.List;

public interface ReviewService {


    List<ReviewDTO> findAllDTO();

    Review findById(Long id);

    Review save(Review review);


}
