package com.openclassrooms.payMyBuddy.dao;

import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyTransactionDao extends JpaRepository<MoneyTransaction,Long> {
}
