package com.projekat.projekat_mrs_isa.repository;

import com.projekat.projekat_mrs_isa.dto.UserDTO;
import com.projekat.projekat_mrs_isa.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("select new com.projekat.projekat_mrs_isa.dto.UserDTO(c) from Client c")
    public List<UserDTO> findAllDTO();

    @Query("select new com.projekat.projekat_mrs_isa.dto.UserDTO(c) from Client c where c.id = ?1 ")
    public UserDTO findUserDTO(Long id);

    @Query("select c from Client c where c.email= ?1")
    public Client findByEmail(String email);
}
