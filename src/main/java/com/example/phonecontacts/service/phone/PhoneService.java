package com.example.phonecontacts.service.phone;

import com.example.phonecontacts.model.Phone;

public interface PhoneService {
  Phone create(Phone phone);

  void delete(Phone phone);

  boolean exists(Phone phone);
}
