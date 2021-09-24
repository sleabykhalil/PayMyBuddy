package com.openclassrooms.payMyBuddy.dao;

import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyTransactionDao extends JpaRepository<MoneyTransaction, Long> {
    List<MoneyTransaction> findBySenderClientId(long senderId);

    List<MoneyTransaction> findBySenderClientIdOrderByMoneyTransactionTimestampAsc(long senderId);

    Page<MoneyTransaction> findBySenderClientIdOrderByMoneyTransactionTimestampDesc(long senderId, Pageable pageable);

}
