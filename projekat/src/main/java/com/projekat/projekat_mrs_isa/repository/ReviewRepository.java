package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.ReviewDTO;
import com.projekat.projekat_mrs_isa.model.RentingEntity;
import com.projekat.projekat_mrs_isa.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.ReviewDTO(r) from Review r where r.approved=false")
    List<ReviewDTO> findAllDTO();

    @Query("select r from Review r where r.rentingEntity.id=?1 or r.rentingOwner.id=?2 ")
    List<Review> getByREIdOrOwnerId(Long reID, Long owId);

    @Modifying
    @Query("update Review r set r.deleted=true where r.rentingEntity=?1")
    void deleteReviewByRentingEntity(RentingEntity rentingEntity);
}
