package com.example.chapter4.demo.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAccountsShouldReturnAccounts() {
        this.entityManager.persist(new Account("jack", "098765432"));
        List<Account> accounts = accountRepository.findAllByUsername("jack");
        assertThat(accounts.size()).isEqualTo(1);
        Account actual = accounts.get(0);
        assertThat(actual.getAccountNumber()).isEqualTo("098765432");
        assertThat(actual.getUsername()).isEqualTo("jack");
    }

    @Test
    void findAccountShouldReturnAccount() {
        this.entityManager.persist(new Account("jack", "098765432"));
        Optional<Account> actual = accountRepository.findByAccountNumber("098765432");
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getAccountNumber()).isEqualTo("098765432");
    }
}