package com.paymybuddy.service;

import com.paymybuddy.ConstantsTest;
import com.paymybuddy.model.Connection;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.impl.ConnectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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



        @Test
        public void givenUserId_whenFindAllConnectionsByUserId_thenReturnConnections() {

            when(connectionRepository.findAllConnectionsByUserId(anyLong())).thenReturn(ConstantsTest.connectionsWithoutConnection);
            List<Connection> connections = connectionService.findAllConnectionsByUserId(1L);
            assertNotNull(connections);
            assertEquals(connections.size(),1);
        }
        @Test
        public void givenConnectedUserId_whenSaveConnection_thenReturnNothing(){

            when(userRepository.getById(anyLong())).thenReturn(ConstantsTest.connectedUser);
            when(userRepository.findByEmail(anyString())).thenReturn(ConstantsTest.user);
            when(connectionRepository.save(any())).thenReturn(ConstantsTest.connection);

            connectionService.saveConnection(ConstantsTest.principal,2L);
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
