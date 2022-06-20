package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.TransactionDTO;
import com.projekat.projekat_mrs_isa.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.TransactionDTO(t) from Transaction t")
    public List<TransactionDTO> findAllDTO();
}
