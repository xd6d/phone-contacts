package com.example.phonecontacts.mapper;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.dto.EmailDto;
import com.example.phonecontacts.dto.PhoneDto;
import com.example.phonecontacts.dto.user.UserCreatedDto;
import com.example.phonecontacts.dto.user.UserCreationDto;
import com.example.phonecontacts.dto.user.UserDto;
import com.example.phonecontacts.model.Contact;
import com.example.phonecontacts.model.Email;
import com.example.phonecontacts.model.Phone;
import com.example.phonecontacts.model.User;

public interface Mapper {
  ContactDto toContactDto(Contact contact);

  UserDto toUserDto(User user);

  UserCreatedDto toUserCreatedDto(User user);

  Email toEmail(EmailDto emailDto);

  Phone toPhone(PhoneDto phoneDto);

  Contact toContact(ContactDto contactDto);

  User toUser(UserCreationDto userCreationDto);
}
