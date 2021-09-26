package com.example.chapter4.demo.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void getUserAccountsShouldReturnAccounts() throws Exception {
        String content = "[\n" +
                "   {\n" +
                "      \"username\":\"user\",\n" +
                "      \"accountNumber\":\"123456789\"\n" +
                "   }\n" +
                "]";
        given(accountService.getUserAccounts()).willReturn(List.of(new Account("user", "123456789")));

        this.mvc.perform(get("/v1/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

}