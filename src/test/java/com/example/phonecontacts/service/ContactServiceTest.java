package com.example.phonecontacts.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.phonecontacts.Utils;
import com.example.phonecontacts.data.ContactRepository;
import com.example.phonecontacts.data.UserRepository;
import com.example.phonecontacts.mapper.Mapper;
import com.example.phonecontacts.service.contact.ContactService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/users.sql")
class ContactServiceTest {

  @Autowired
  private ContactService contactService;

  @Autowired
  private ContactRepository contactRepository;

  @Autowired
  private Mapper mapper;

  private final Utils utils = new Utils();

  @AfterEach
  void clear() {
    contactRepository.deleteAll();
  }

  @AfterAll
  static void clear(@Autowired UserRepository userRepository) {
    userRepository.deleteAll();
  }

  @Test
  void create() {
    var expectedContact = utils.getTestContact();
    contactService.create(expectedContact);
    var createdContact = contactRepository.findOne(Example.of(expectedContact)).orElseThrow();

    assertTrue(contactRepository.exists(Example.of(expectedContact)));
    assertEquals(expectedContact, createdContact);
  }

  @Test
  @Sql("/contacts.sql")
  void getById() {
    var expected = contactRepository.findById(1L).orElseThrow();
    var gotById = contactService.getById(1L, "login").orElseThrow();
    var gotByMissingId = contactService.getById(100L, "");

    assertTrue(gotByMissingId.isEmpty());
    assertEquals(expected, gotById);
  }

  @Test
  @Sql("/contacts.sql")
  void update() {
    var toUpdate = contactService.getById(1L, "login").orElseThrow();
    toUpdate.setName("Val");
    var dtoToUpdate = mapper.toContactDto(toUpdate);
    contactService.update(1L, dtoToUpdate, "login");
    var updated = contactService.getById(1L, "login").orElseThrow();

    assertEquals(updated.getName(), "Val");
  }

  @Test
  @Sql("/contacts.sql")
  void delete() {
    assertTrue(contactRepository.existsById(1L));

    contactService.delete(1L, "login");

    assertFalse(contactRepository.existsById(1L));
  }
}