package com.paymybuddy.controller;

import com.paymybuddy.ConstantsTest;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
@WithMockUser(username = "user", authorities = { "USER" })
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserServiceDetails userServiceDetails;

    @Test
    public void givenConnectedUserIdAndAmountAndDescription_whenSaveTransaction_thenReturnTableTransaction() throws Exception {
        doNothing().when(transactionService).saveTransaction(any(),anyLong(),any(),anyString());
        when(userService.findByEmail(anyString())).thenReturn(ConstantsTest.user);
        when(transactionService.findAllByUserId(anyLong(),any())).thenReturn(ConstantsTest.pageTransaction);


            mvc.perform(post("/saveTransaction").param("connectedUserId","1").param("amount","3").param("description","description")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .andExpect(status().isOk()).andExpect(model()
        .attributeExists("transactionPage"))
                .andExpect(view().name("home :: #tableTransaction"));
    }

}
