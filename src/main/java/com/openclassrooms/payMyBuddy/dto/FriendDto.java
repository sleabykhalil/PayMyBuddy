package com.openclassrooms.payMyBuddy.dto;

import com.openclassrooms.payMyBuddy.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    private long friendId;
    private List<Client> Clients;
}
