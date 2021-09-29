package com.openclassrooms.payMyBuddy.dto.mapper;

import com.openclassrooms.payMyBuddy.dto.FriendDto;
import com.openclassrooms.payMyBuddy.model.Friend;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    Friend friendDtoToFriend(FriendDto friendDto);

    FriendDto friendToFriendDto(Friend friend);
}
