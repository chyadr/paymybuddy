package com.paymybuddy.controller;

import com.paymybuddy.Constants;
import com.paymybuddy.service.impl.ConnectionService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@WithMockUser(username = "user", authorities = { "USER" })
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserServiceDetails userServiceDetails;

    @Test
    public void givenUser_whenSaveUser_thenReturnLogin() throws Exception {

        doNothing().when(userService).saveUser(any());

        mvc.perform(post("/saveUser").flashAttr("user",Constants.user)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .andExpect(status().isOk()).andExpect(model()
        .attributeExists("message"))
                .andExpect(view().name("login"));
    }

}
