package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientDetailsService implements UserDetailsService {

    //Todo add Roles To DATABASE in next Version
    Set<String> roleSet = Set.of("ROLE_CLIENT");

    @Autowired
    private ClientService clientService;
    @Autowired
    ClientMapper clientMapper;

    /**
     * Lode user from DB
     *
     * @param clientEmail
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String clientEmail) throws UsernameNotFoundException {
        Client client = clientService.findClientByEmail(clientEmail);
        ClientDto clientDto = clientMapper.clientToClientDto(client);
        List<GrantedAuthority> authorities = getClientAuthority(roleSet);
        UserDetails userDetails = buildClientForAuthentication(clientDto, authorities);
        return userDetails;
    }

    private UserDetails buildClientForAuthentication(ClientDto clientDto, List<GrantedAuthority> authorities) {
        User user = new User(clientDto.getEmailAccount(), clientDto.getClientPassword(), authorities);
        return user;
    }

    private List<GrantedAuthority> getClientAuthority(Set<String> clientRoleSet) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : clientRoleSet) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
}
