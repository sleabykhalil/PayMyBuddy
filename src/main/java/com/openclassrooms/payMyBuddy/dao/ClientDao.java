package com.openclassrooms.payMyBuddy.dao;

import com.openclassrooms.payMyBuddy.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    Client findClientByEmailAccount(String emailAccount);
}
