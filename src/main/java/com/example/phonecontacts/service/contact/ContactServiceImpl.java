package com.example.phonecontacts.service.contact;

import com.example.phonecontacts.data.ContactRepository;
import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.mapper.Mapper;
import com.example.phonecontacts.model.Contact;
import com.example.phonecontacts.service.email.EmailService;
import com.example.phonecontacts.service.phone.PhoneService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
  private ContactRepository contactRepository;
  private EmailService emailService;
  private PhoneService phoneService;
  private Mapper mapper;

  @Override
  @Transactional
  public Contact create(Contact contact) {
    return contactRepository.save(contact);
  }

  @Override
  public Optional<Contact> getById(Long id, String login) {
    Optional<Contact> contactOptional = contactRepository.findById(id);
    if (contactOptional.isPresent() && !contactOptional.get().getUser().getLogin().equals(login)) {
      throw new IllegalArgumentException("Not your contact");
    }
    return contactOptional;
  }

  @Override
  @Transactional
  public Optional<Contact> update(Long id, ContactDto contact, String login) {
    if (contactRepository.existsById(id)) {
      Optional<Contact> currentOptional = contactRepository.findById(id);
      if (currentOptional.isEmpty()) {
        return currentOptional;
      }
      Contact current = currentOptional.get();
      if (!current.getUser().getLogin().equals(login)) {
        throw new IllegalArgumentException("Not your contact");
      }
      if (contact.getName() != null) {
        current.setName(contact.getName());
      }
      if (contact.getEmails() != null) {
        current.getEmails().forEach(emailService::delete);
        current.getEmails().clear();
        current.getEmails().addAll(
            (contact.getEmails().stream().map(mapper::toEmail).collect(Collectors.toSet())));
      }
      if (contact.getPhones() != null) {
        current.getPhones().forEach(phoneService::delete);
        current.getPhones().clear();
        current.getPhones()
            .addAll(contact.getPhones().stream().map(mapper::toPhone).collect(Collectors.toSet()));
      }
      return Optional.of(contactRepository.save(current));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void delete(Long id, String login) {
    if (contactRepository.existsById(id) &&
        contactRepository.getReferenceById(id).getUser().getLogin().equals(login)) {
      contactRepository.deleteById(id);
    }
  }
}
