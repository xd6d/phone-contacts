package com.example.phonecontacts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.phonecontacts.Utils;
import com.example.phonecontacts.data.ContactRepository;
import com.example.phonecontacts.data.UserRepository;
import com.example.phonecontacts.dto.user.UserCreationDto;
import com.example.phonecontacts.service.user.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  private final Utils utils = new Utils();

  @AfterEach
  void clear() {
    userRepository.deleteAll();
  }

  @AfterAll
  static void clear(@Autowired ContactRepository contactRepository) {
    contactRepository.deleteAll();
  }

  @Test
  void create() {
    var expectedUser = utils.getTestUser();
    var newUser = new UserCreationDto();
    newUser.setLogin(expectedUser.getLogin());
    newUser.setPassword(expectedUser.getPassword());
    userService.create(newUser);
    var createdUser = userRepository.findByLogin(expectedUser.getLogin()).orElseThrow();

    assertTrue(userRepository.existsByLogin(expectedUser.getLogin()));
    assertEquals(expectedUser, createdUser);
  }

  @Test
  @Sql("/users.sql")
  void getByLogin() {
    var expectedUser = userRepository.findById(1L).orElseThrow();
    var gotByLogin = userService.getByLogin("login");

    assertEquals(expectedUser, gotByLogin);
  }
}