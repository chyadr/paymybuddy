package com.paymybuddy.service;

import com.paymybuddy.ConstantsTest;
import com.paymybuddy.exception.BusinessResourceException;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.impl.UserServiceDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceDetailsTest {



        @InjectMocks
        private UserServiceDetails userServiceDetails;

        @Mock
        private UserRepository userRepository;



    @Test
    public void givenEmail_whenLoadUserByUsername_thenReturnUserDetails(){
        when(userRepository.findByEmail(anyString())).thenReturn(ConstantsTest.user);
        UserDetails userDetails= userServiceDetails.loadUserByUsername("email");
        assertNotNull(userDetails);

    }

    @Test
    public void givenEmail_whenLoadUserByUsername_thenThrowException(){

        when(userRepository.findUserAndAccountByEmail(anyString())).thenReturn(null);

        assertThrows(
                BusinessResourceException.class,
                () -> userServiceDetails.loadUserByUsername("email"),
                "Bad credentials"
        );

    }


    }
