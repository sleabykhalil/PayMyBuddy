package com.openclassrooms.payMyBuddy.dao;

import com.openclassrooms.payMyBuddy.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao extends JpaRepository<Friend, Long> {
}
