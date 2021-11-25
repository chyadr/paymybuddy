package com.paymybuddy.service.impl;

import com.paymybuddy.model.Connection;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.IConnectionService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ConnectionService implements IConnectionService {
    private final ConnectionRepository connectionRepository;
    private final UserService userService;

    public ConnectionService(ConnectionRepository connectionRepository, UserService userService) {
        this.connectionRepository = connectionRepository;
        this.userService = userService;
    }

    @Override
    public List<Connection> findAllConnectionsByUserId(Long userId) {
        return connectionRepository.findAllConnectionsByUserId(userId) ;
    }

    @Override
    public void saveConnection(Principal principal, Long connectedUserId) {

        Connection connection= new Connection();
        //Find and set connectedUser by id
        User connectedUser=userService.getById(connectedUserId);
        connection.setConnectedUser(connectedUser);
        //Find and set user by principal
        User persistedUser = userService.findByEmail(principal.getName());
        connection.setUser(persistedUser);

        connectionRepository.save(connection);
    }

    @Override
    public Connection findConnectionByUserIdAndConnectedUserId(Long userId, Long connectedUserId) {
        return connectionRepository.findConnectionByUserIdAndConnectedUserId(userId,connectedUserId);
    }

}
