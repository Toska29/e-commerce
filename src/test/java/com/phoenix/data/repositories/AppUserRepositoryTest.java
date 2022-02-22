package com.phoenix.data.repositories;

import com.phoenix.data.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql("/db/insert.sql")
class AppUserRepositoryTest {

    @Autowired
    AppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Create a new user with cart test")
    void whenUserIsCreated_thenCreateCartTest(){
        //creating a user object
        AppUser appUser = new AppUser();
        appUser.setEmail("toska@gmail.com");
        appUser.setAddress("Sabo");
        appUser.setFirstName("Toska");
        appUser.setLastName("Emma");

        //save user in db
        appUserRepository.save(appUser);
        assertThat(appUser.getId()).isNotNull();
        assertThat(appUser.getMyCart()).isNotNull();

        log.info("App User created :: {}", appUser);
    }
}