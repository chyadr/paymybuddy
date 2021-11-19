package com.paymybuddy.service;

import com.paymybuddy.Constants;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.paymybuddy.Constants.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {



        @InjectMocks
        private UserService userService;

        @Mock
        private UserRepository userRepository;
        @Mock
        private BCryptPasswordEncoder passwordEncoder;




    @Test
    public void givenUser_whenSaveUser_thenReturnNothing() {

        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(any())).thenReturn(user);
        userService.saveUser(user);
        verify(userRepository,times(1)).save(any());


    }
    @Test
    public void givenEmail_whenFindByEmail_thenReturnUser(){
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        User user= userService.findByEmail("email");
        assertNotNull(user);

    }
    @Test
    public void givenId_FindAllNonConnectedUsersByUserId_thenReturnUsers(){
        when(userRepository.findAllNonConnectedUsersByUserId(anyLong())).thenReturn(List.of(user));
        List<User> users=userService.findAllNonConnectedUsersByUserId(2L);
        assertNotNull(users);

    }

    }
