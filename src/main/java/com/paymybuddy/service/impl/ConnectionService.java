package com.paymybuddy.service.impl;

import com.paymybuddy.model.Connection;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ConnectionService implements IConnectionService{
    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    public ConnectionService(ConnectionRepository connectionRepository, UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Connection> findAllConnectionsByUserId(Long userId) {
        return connectionRepository.findAllConnectionsByUserId(userId) ;
    }

    @Override
    public void saveConnection(Principal principal, Long connectedUserId) {

        Connection connection= new Connection();
        //Find and set connectedUser by id
        User connectedUser=userRepository.getById(connectedUserId);
        connection.setConnectedUser(connectedUser);
        //Find and set user by principal
        User persistedUser = userRepository.findByEmail(principal.getName());
        connection.setUser(persistedUser);

        connectionRepository.save(connection);
    }

}
