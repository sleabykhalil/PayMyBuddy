package com.openclassrooms.payMyBuddy.dto.mapper;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.FriendEmailDto;
import com.openclassrooms.payMyBuddy.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto clientToClientDto(Client client);

    Client clientDtoToClient(ClientDto client);

    List<FriendEmailDto> friendEmailList(List<Client> friendList);

}
