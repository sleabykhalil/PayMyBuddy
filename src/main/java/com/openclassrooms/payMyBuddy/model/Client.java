package com.openclassrooms.payMyBuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicUpdate
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            , fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    @ToString.Exclude
    private Balance balance;

    @OneToMany(
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_client_id")
    @JsonIgnore
    private List<MoneyTransaction> moneyTransactions;

    @ManyToMany(
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "friend_client",
            joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonIgnore
    @ToString.Exclude
    private List<Friend> friends;

}
