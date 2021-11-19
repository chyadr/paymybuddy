package com.paymybuddy.service;

import com.paymybuddy.Constants;
import com.paymybuddy.model.Connection;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.impl.ConnectionService;
import com.paymybuddy.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.paymybuddy.Constants.connectedUser;
import static com.paymybuddy.Constants.user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ConnectionServiceTest {


        @InjectMocks
        private ConnectionService connectionService;

        @Mock
        private ConnectionRepository connectionRepository;
        @Mock
        private UserRepository userRepository;
        @Mock
        private UserService userService;


        @Test
        public void givenUserId_whenFindAllConnectionsByUserId_thenReturnConnections() {

            when(connectionRepository.findAllConnectionsByUserId(anyLong())).thenReturn(Constants.connectionsWithoutConnection);
            List<Connection> connections = connectionService.findAllConnectionsByUserId(1L);
            assertNotNull(connections);
            assertEquals(connections.size(),1);
        }
        @Test
        public void givenConnectedUserId_whenSaveConnection_thenReturnNothing(){

            when(userRepository.getById(anyLong())).thenReturn(Constants.connectedUser);
            when(userRepository.findByEmail(anyString())).thenReturn(Constants.user);
            when(connectionRepository.save(any())).thenReturn(Constants.connection);

            connectionService.saveConnection(Constants.principal,2L);
            verify(connectionRepository,times(1)).save(any());

        }


}

/*
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
*/
