package com.example.phonecontacts.service.phone;

import com.example.phonecontacts.data.PhoneRepository;
import com.example.phonecontacts.model.Phone;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PhoneServiceImpl implements PhoneService {
  private PhoneRepository phoneRepository;

  @Override
  public Phone create(Phone phone) {
    return phoneRepository.save(phone);
  }

  @Override
  public void delete(Phone phone) {
    phoneRepository.delete(phone);
  }

  @Override
  public boolean exists(Phone phone) {
    return phoneRepository.exists(Example.of(phone));
  }
}
