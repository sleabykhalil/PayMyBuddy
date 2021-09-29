package com.openclassrooms.payMyBuddy.dto;

import com.openclassrooms.payMyBuddy.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewFriendDto {
    long clientId;
    long newFriendId;
    String emailAccount;
}
