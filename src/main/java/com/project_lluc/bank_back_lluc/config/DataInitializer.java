package com.project_lluc.bank_back_lluc.config;

import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.accounts.Savings;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.model.users.Admin;
import com.project_lluc.bank_back_lluc.model.users.ThirdParty;
import com.project_lluc.bank_back_lluc.repository.AccountHolderRepository;
import com.project_lluc.bank_back_lluc.repository.CheckingRepository;
import com.project_lluc.bank_back_lluc.repository.SavingsRepository;
import com.project_lluc.bank_back_lluc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final AccountHolderRepository holderRepository;
    private final CheckingRepository checkingRepository;
    private final SavingsRepository savingsRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            // 1) Usuarios
            if (userRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setName("System Administrator");
                userRepository.save(admin);

                ThirdParty tp = new ThirdParty();
                tp.setUsername("thirdp");
                tp.setPassword(passwordEncoder.encode("tp123"));
                tp.setHashedKey("a1b2c3d4-e5f6-4712-89ab-cdef01234567");
                tp.setName("Payroll Service");
                userRepository.save(tp);

                AccountHolder aliceUser = new AccountHolder();
                aliceUser.setUsername("alice");
                aliceUser.setPassword(passwordEncoder.encode("alice123"));
                aliceUser.setName("Alice Smith");
                aliceUser.setDateOfBirth(LocalDate.of(1985, 6, 15));
                userRepository.save(aliceUser);
            }

            // 2) AccountHolder (tabla específica)
            if (holderRepository.count() == 0) {
                holderRepository.findByUsername("alice").orElseGet(() -> {
                    AccountHolder ah = new AccountHolder();
                    ah.setUsername("alice");
                    ah.setName("Alice Smith");
                    ah.setDateOfBirth(LocalDate.of(1985, 6, 15));
                    return holderRepository.save(ah);
                });
            }

            // 3) Checking
            if (checkingRepository.count() == 0) {
                AccountHolder alice = holderRepository.findByUsername("alice")
                        .orElseThrow();
                Checking chk = new Checking();
                chk.setSecretKey("sekret1");
                chk.setBalance(new Money(new BigDecimal("500.00")));
                chk.setPrimaryOwner(alice);
                checkingRepository.save(chk);
            }

            // 4) Savings
            if (savingsRepository.count() == 0) {
                AccountHolder alice = holderRepository.findByUsername("alice")
                        .orElseThrow();
                Savings sav = new Savings();
                sav.setSecretKey("savsek");
                sav.setBalance(new Money(new BigDecimal("1500.00")));
                sav.setPrimaryOwner(alice);
                savingsRepository.save(sav);
            }
        };
    }
}
