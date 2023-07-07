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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements Mapper {
  ModelMapper modelMapper = new ModelMapper();

  @Override
  public ContactDto toContactDto(Contact contact) {
    return modelMapper.map(contact, ContactDto.class);
  }

  @Override
  public UserDto toUserDto(User user) {
    return modelMapper.map(user, UserDto.class);
  }

  @Override
  public UserCreatedDto toUserCreatedDto(User user) {
    return modelMapper.map(user, UserCreatedDto.class);
  }

  @Override
  public Email toEmail(EmailDto emailDto) {
    return modelMapper.map(emailDto, Email.class);
  }

  @Override
  public Phone toPhone(PhoneDto phoneDto) {
    return modelMapper.map(phoneDto, Phone.class);
  }

  @Override
  public Contact toContact(ContactDto contactDto) {
    return modelMapper.map(contactDto, Contact.class);
  }

  @Override
  public User toUser(UserCreationDto userCreationDto) {
    return modelMapper.map(userCreationDto, User.class);
  }
}
