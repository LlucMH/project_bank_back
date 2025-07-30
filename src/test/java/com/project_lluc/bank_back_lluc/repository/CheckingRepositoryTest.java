package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never"
})
class CheckingRepositoryTest {

    @Autowired
    private CheckingRepository checkingRepository;

    @Test
    @DisplayName("Save and retrieve a Checking account")
    void testSaveAndFindChecking() {
        Checking chk = new Checking();
        chk.setSecretKey("secret123");
        chk.setBalance(new Money(new BigDecimal("500.00")));

        Checking saved = checkingRepository.save(chk);
        assertThat(saved.getId()).isNotNull();

        Checking fetched = checkingRepository.findById(saved.getId())
                .orElseThrow();
        assertThat(fetched.getBalance().getAmount())
                .isEqualByComparingTo("500.00");
    }
}
