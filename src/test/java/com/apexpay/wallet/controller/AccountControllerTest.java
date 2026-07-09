package com.apexpay.wallet.controller;

import com.apexpay.wallet.service.AccountService;
import com.apexpay.wallet.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransferService transferService;

    @MockitoBean
    private AccountService accountService;

    @Test
    void shouldReturn400WhenAccountIsNegative() throws Exception {
        String invalidPayload = """
                {
                    "sourceAccountId": 1,
                    "destinationAccountId": 2,
                    "amount": -50.00
                }
                """;

        mockMvc.perform(
                        post("/api/v1/accounts/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidPayload)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.amount").value("Transfer amount must be greater than zero"));
    }
}
