package com.example.phonecontacts.service.email;

import com.example.phonecontacts.data.EmailRepository;
import com.example.phonecontacts.model.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
  private EmailRepository emailRepository;

  @Override
  public Email create(Email email) {
    return emailRepository.save(email);
  }

  @Override
  public void delete(Email email) {
    emailRepository.delete(email);
  }

  @Override
  public boolean exists(Email email) {
    return emailRepository.exists(Example.of(email));
  }
}
