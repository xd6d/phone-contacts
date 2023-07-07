package com.example.phonecontacts.service.email;

import com.example.phonecontacts.model.Email;

public interface EmailService {
  Email create(Email email);

  void delete(Email email);

  boolean exists(Email email);
}
