package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
}
