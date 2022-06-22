package com.projekat.projekat_mrs_isa.service.impl;
import com.projekat.projekat_mrs_isa.model.Fee;
import com.projekat.projekat_mrs_isa.repository.FeeRepository;
import com.projekat.projekat_mrs_isa.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Override
    public Fee findFee(Long id) { return  feeRepository.findFee(id) ; }

    @Override
    public Fee save(Fee fee) {
        return feeRepository.save(fee);
    }
}
