package com.openclassrooms.payMyBuddy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//@DynamicUpdate
@Table(name = "friend")
public class Friend {
    @Id
    @Column(name = "friend_id")
    private long friendId;

    @ManyToMany(
            mappedBy = "friends"
    )
    @JsonBackReference
    private List<Client> Clients;
}
