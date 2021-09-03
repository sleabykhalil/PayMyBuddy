package com.openclassrooms.payMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private long clientId;

    @Column(name = "email_account")
    private String emailAccount;

    @Column(name = "client_password")
    private String clientPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "client_type")
    private String clientType;

    @OneToOne(
            cascade = CascadeType.ALL
/*            ,orphanRemoval = true*/
          ,  fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Balance balance;

    @OneToMany(
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_client_id")
    private List<MoneyTransaction> moneyTransactions;

    @ManyToMany(
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "friend_client",
            joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Friend> friends;

}
