package com.example.phonecontacts.dto;

import java.util.Set;
import lombok.Data;

@Data
public class ContactDto {
  private String name;

  private Set<EmailDto> emails;

  private Set<PhoneDto> phones;
}
