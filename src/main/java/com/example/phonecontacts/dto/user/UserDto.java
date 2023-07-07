package com.example.phonecontacts.dto.user;

import com.example.phonecontacts.dto.ContactDto;
import java.util.List;
import lombok.Data;

@Data
public class UserDto {
  private String login;
  private List<ContactDto> contacts;
}
