package com.openclassrooms.payMyBuddy.dao;

import com.openclassrooms.payMyBuddy.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceDao extends JpaRepository<Balance,Long> {
}
