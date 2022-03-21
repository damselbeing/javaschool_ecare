package javaschool.ecare.services.impl;

import javaschool.ecare.entities.Client;
import javaschool.ecare.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientUserDetailsService implements UserDetailsService {


    @Autowired
    ClientRepository clientRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client clientFromDB= clientRepository.getClientByEmail(email);
        if (clientFromDB == null) {
            throw new UsernameNotFoundException("Unknown user with email: "+email);
        }
        UserDetails user = User.builder()
                .username(clientFromDB.getEmail())
                .password(clientFromDB.getPassword())
                .roles(clientFromDB.getRole())
                .build();
        return user;
    }
}
