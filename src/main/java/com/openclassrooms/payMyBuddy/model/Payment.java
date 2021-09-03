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
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "transaction_id")
    private long transactionId;

    @Column(name = "motive")
    private String motive;

    @Column(name = "amount")
    private double amount;

    @Column(name = "fee")
    private double fee;
}
