package com.openclassrooms.payMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

/*    @OneToOne
    @MapsId
    @JoinColumn(name = "transaction_id")
    private MoneyTransaction moneyTransaction;*/
}
