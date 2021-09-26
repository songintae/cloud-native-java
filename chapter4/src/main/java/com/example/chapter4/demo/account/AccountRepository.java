package com.example.chapter4.demo.account;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends Repository<Account, String> {
    List<Account> findAllByUsername(String username);

    Optional<Account> findByAccountNumber(String accountNumber);
}
