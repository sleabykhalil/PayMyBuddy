package com.openclassrooms.payMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @Column(name = "client_id")
    private long clientId;

    @Column(name = "amount")
    private double amount;

}
