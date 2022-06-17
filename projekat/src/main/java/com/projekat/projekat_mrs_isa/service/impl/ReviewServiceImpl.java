package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.ComplaintDTO;
import com.projekat.projekat_mrs_isa.dto.ReviewDTO;
import com.projekat.projekat_mrs_isa.model.Review;
import com.projekat.projekat_mrs_isa.repository.ReviewRepository;
import com.projekat.projekat_mrs_isa.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> findAllDTO() { return reviewRepository.findAllDTO(); }

    @Override
    public Review findById(Long id){return reviewRepository.findById(id).orElse(null);}

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }
}
