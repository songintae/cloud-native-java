package com.example.chapter4.demo.account;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/v1/accounts")
    public List<Account> getAccounts() {
        return this.accountService.getUserAccounts();
    }
}
