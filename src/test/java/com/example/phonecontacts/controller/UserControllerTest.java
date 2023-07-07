package com.example.phonecontacts.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.phonecontacts.Utils;
import com.example.phonecontacts.data.ContactRepository;
import com.example.phonecontacts.data.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  private final String login = Utils.LOGIN;
  private final String password = Utils.PASSWORD;

  @AfterAll
  static void clear(@Autowired UserRepository userRepository,
                    @Autowired ContactRepository contactRepository) {
    userRepository.deleteAll();
    contactRepository.deleteAll();
  }

  @Test
  @Order(1)
  void createAndLogin() throws Exception {
    mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON).content(
            String.format("""
                {
                  "login": "%s",
                  "password": "%s"
                }""", login, password)
        ))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.login").value(login));

    assertTrue(userRepository.findByLogin(login).isPresent());

    mockMvc.perform(formLogin("/login").user(login).password(password))
        .andExpect(redirectedUrl("/"));
  }

  @Test
  void getMe() throws Exception {
    mockMvc.perform(get("/").with(user(login).password(password)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(String.format("""
            {
              "login": "%s",
              "contacts": []
            }""", login)
        ));
  }

  @Test
  void contacts() throws Exception {
    mockMvc.perform(get("/contacts").with(user(login).password(password)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json("[]"));
  }
}
