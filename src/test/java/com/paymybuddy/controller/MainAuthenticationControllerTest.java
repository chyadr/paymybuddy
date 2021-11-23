package com.paymybuddy.controller;

import com.paymybuddy.ConstantsTest;
import com.paymybuddy.service.impl.ConnectionService;
import com.paymybuddy.service.impl.TransactionService;
import com.paymybuddy.service.impl.UserService;
import com.paymybuddy.service.impl.UserServiceDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainAuthenticationController.class)
@WithMockUser(username = "user", authorities = {"USER"})
public class MainAuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ConnectionService connectionService;
    @MockBean
    private UserService userService;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private UserServiceDetails userServiceDetails;


    @Test
    public void givenUser_whenRegister_thenReturnRegister() throws Exception {

        mvc.perform(get("/register").param("User", "Constant.user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void givenNothing_whenLogin_thenReturnLogin() throws Exception {
        mvc.perform(get("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }


    @Test
    public void givenNothing_whenDefault_thenReturnHome()throws Exception{

        when(userService.findByEmail(anyString())).thenReturn(ConstantsTest.user);
        when(transactionService.findAllByUserId(anyLong(),any())).thenReturn(ConstantsTest.pageTransaction);
        when(connectionService.findAllConnectionsByUserId(anyLong())).thenReturn(ConstantsTest.connectionsWithoutConnection);
        when(userService.findAllNonConnectedUsersByUserId(anyLong())).thenReturn(ConstantsTest.users);

        mvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk()).andExpect(model()
                        .attributeExists("transactionPage","pageNumbers","connections","nonConnectedUsers"));
        mvc.perform(get("/home")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk()).andExpect(model()
                        .attributeExists("transactionPage","pageNumbers","connections","nonConnectedUsers"));

    }

}
