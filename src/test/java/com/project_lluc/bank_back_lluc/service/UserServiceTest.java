package com.project_lluc.bank_back_lluc.service;

import com.project_lluc.bank_back_lluc.model.users.User;
import com.project_lluc.bank_back_lluc.model.enums.Role;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.model.users.Admin;
import com.project_lluc.bank_back_lluc.model.users.ThirdParty;
import com.project_lluc.bank_back_lluc.repository.UserRepository;
import com.project_lluc.bank_back_lluc.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateAccountHolder() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName("Lluc");
        accountHolder.setUsername("lluc123");
        accountHolder.setPassword("secret");
        accountHolder.setDateOfBirth(LocalDate.of(2000, 1, 1));

        userService.createUser(accountHolder);

        ArgumentCaptor<AccountHolder> captor = ArgumentCaptor.forClass(AccountHolder.class);
        verify(userRepository, times(1)).save(captor.capture());

        assertEquals("lluc123", captor.getValue().getUsername());
    }

    @Test
    void testCreateAdmin() {
        Admin admin = new Admin();
        admin.setName("Admin User");
        admin.setUsername("admin");
        admin.setPassword("pass");

        userService.createUser(admin);

        verify(userRepository).save(admin);
    }

    @Test
    void testCreateThirdParty() {
        ThirdParty thirdParty = new ThirdParty();
        thirdParty.setName("Stripe");
        thirdParty.setHashedKey("abc123hashed");

        userService.createUser(thirdParty);

        verify(userRepository).save(thirdParty);
    }

    @Test
    void testFindUserByUsernameIfExists() {
        AccountHolder user = new AccountHolder();
        user.setUsername("lluc123");

        when(userRepository.findByUsername("lluc123")).thenReturn(Optional.of(user));

        var found = userService.findByUsername("lluc123");

        assertTrue(found.isPresent());
        assertEquals("lluc123", found.get().getUsername());
    }

    @Test
    void testFindUserByUsernameIfNotExists() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        var result = userService.findByUsername("notfound");

        assertFalse(result.isPresent());
    }
}
