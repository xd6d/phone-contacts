package com.example.phonecontacts.service.contact;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.model.Contact;
import java.util.Optional;

public interface ContactService {
  Contact create(Contact contact);

  Optional<Contact> getById(Long id, String login);

  Optional<Contact> update(Long id, ContactDto contact, String login);

  void delete(Long id, String login);
}
