package com.example.phonecontacts.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.phonecontacts.Utils;
import com.example.phonecontacts.data.ContactRepository;
import com.example.phonecontacts.data.UserRepository;
import com.example.phonecontacts.model.Contact;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ContactRepository contactRepository;

  private final String url = "contact";
  private final String login = Utils.LOGIN;
  private final String password = Utils.PASSWORD;
  private final String testName = "John";

  @AfterAll
  static void clear(@Autowired UserRepository userRepository,
                    @Autowired ContactRepository contactRepository) {
    userRepository.deleteAll();
    contactRepository.deleteAll();
  }

  @Test
  @Sql("/test-user.sql")
  @Order(1)
  void createContact() throws Exception {
    mockMvc.perform(post("/{url}", url).with(user(login).password(password))
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {
                  "name": "%s",
                  "emails": [{
                    "address": "john@email.com"
                  },
                  {
                    "address": "john2@email.com"
                  }],
                  "phones": [{
                    "number": "+380991234567"
                  }]
                }
                """, testName)))
        .andExpect(status().isCreated());

    assertEquals(1, contactRepository.findAll().size());
    var expectedContact = new Contact();
    expectedContact.setName(testName);
    Contact createdContact = contactRepository.findOne(Example.of(expectedContact)).orElseThrow();
    assertEquals(2, createdContact.getEmails().size());
    assertEquals(1, createdContact.getPhones().size());
  }

  @Test
  @Order(2)
  void getContact() throws Exception {
    var expectedContact = new Contact();
    expectedContact.setName(testName);
    Contact createdContact = contactRepository.findOne(Example.of(expectedContact)).orElseThrow();

    mockMvc.perform(get("/{url}/{id}", url, createdContact.getId())
            .with(user(login).password(password)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(String.format("{name:'%s'}", testName)))
        .andExpect(jsonPath("$.emails", hasSize(2)))
        .andExpect(jsonPath("$.emails[0].address", is("john@email.com")))
        .andExpect(jsonPath("$.emails[1].address", is("john2@email.com")))
        .andExpect(jsonPath("$.phones", hasSize(1)))
        .andExpect(jsonPath("$.phones[0].number", is("+380991234567")));
  }

  @Test
  @Order(3)
  void updateContact() throws Exception {
    var expectedContact = new Contact();
    expectedContact.setName(testName);
    Contact createdContact = contactRepository.findOne(Example.of(expectedContact)).orElseThrow();

    mockMvc.perform(patch("/{url}/{id}", url, createdContact.getId())
            .with(user(login).password(password)).contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "name": "Johnathan",
                  "phones": [
                  {
                    "number": "+380661234567"
                  }]
                }"""))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json("{name:'Johnathan'}"))
        .andExpect(jsonPath("$.emails", hasSize(2)))
        .andExpect(jsonPath("$.emails[0].address", is("john@email.com")))
        .andExpect(jsonPath("$.emails[1].address", is("john2@email.com")))
        .andExpect(jsonPath("$.phones", hasSize(1)))
        .andExpect(jsonPath("$.phones[0].number", is("+380661234567")));
  }

  @Test
  @Order(4)
  void deleteContact() throws Exception {
    var expectedContact = new Contact();
    expectedContact.setName("Johnathan");
    Contact createdContact = contactRepository.findOne(Example.of(expectedContact)).orElseThrow();

    mockMvc.perform(delete("/{url}/{id}", url, createdContact.getId())
            .with(user(login).password(password)))
        .andExpect(status().isNoContent());

    assertFalse(contactRepository.existsById(createdContact.getId()));
  }
}
