package com.openclassrooms.payMyBuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "money_transaction")
public class MoneyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "money_transaction_timestamp")
    private LocalDateTime moneyTransactionTimestamp;

    @Column(name = "sender_client_id")
    private long senderClientId;

    @Column(name = "receiver_client_id")
    private long receiverClientId;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "transaction_id")
    @JsonIgnore
    @ToString.Exclude
    Payment payment;
}
