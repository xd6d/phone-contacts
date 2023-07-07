package com.example.phonecontacts.service.user;

import com.example.phonecontacts.dto.user.UserCreationDto;
import com.example.phonecontacts.model.User;

public interface UserService {
  User create(UserCreationDto user);

  User getByLogin(String login);
}
