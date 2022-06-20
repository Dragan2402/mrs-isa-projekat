package com.projekat.projekat_mrs_isa.service;
import com.projekat.projekat_mrs_isa.model.Fee;

public interface FeeService {

    Fee findFee(Long id);

    Fee save(Fee fee);
}
