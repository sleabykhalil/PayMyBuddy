package com.openclassrooms.payMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        Payment payment;
/*    @OneToOne(mappedBy = "moneyTransaction", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment payment;*/

}
