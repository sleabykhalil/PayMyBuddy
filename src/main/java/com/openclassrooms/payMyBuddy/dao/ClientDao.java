package com.openclassrooms.payMyBuddy.dao;

import com.openclassrooms.payMyBuddy.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {

    Optional<Client> findClientByEmailAccount(String emailAccount);
}
