package com.projekat.projekat_mrs_isa.service;

import com.projekat.projekat_mrs_isa.dto.TransactionDTO;
import com.projekat.projekat_mrs_isa.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> findAllDTO();

    Transaction save(Transaction transaction);
}
