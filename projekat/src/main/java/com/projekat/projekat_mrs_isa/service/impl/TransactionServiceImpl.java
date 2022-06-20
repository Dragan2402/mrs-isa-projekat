package com.projekat.projekat_mrs_isa.service.impl;

import com.projekat.projekat_mrs_isa.dto.TransactionDTO;
import com.projekat.projekat_mrs_isa.model.Fee;
import com.projekat.projekat_mrs_isa.model.Transaction;
import com.projekat.projekat_mrs_isa.repository.TransactionRepository;
import com.projekat.projekat_mrs_isa.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionDTO> findAllDTO() { return transactionRepository.findAllDTO(); }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
