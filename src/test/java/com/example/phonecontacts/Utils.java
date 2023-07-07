package com.example.phonecontacts;

import com.example.phonecontacts.model.Contact;
import com.example.phonecontacts.model.Email;
import com.example.phonecontacts.model.Phone;
import com.example.phonecontacts.model.User;
import java.util.Set;

public class Utils {

  public static final String LOGIN = "test-login";
  public static final String PASSWORD = "qwerty";

  public User getTestUser() {
    User testUser = new User();
    testUser.setLogin("login");
    testUser.setPassword("password");
    return testUser;
  }

  public Contact getTestContact(){
    Contact contact = new Contact();
    contact.setName("Name");
//    contact.setUser(getTestUser());
    contact.setEmails(Set.of(getTestEmail()));
    contact.setPhones(Set.of(getTestPhone()));
    return contact;
  }

  public Email getTestEmail(){
    Email email = new Email();
    email.setAddress("test@mail.com");
    return email;
  }

  public Phone getTestPhone(){
    Phone phone = new Phone();
    phone.setNumber("+380991234567");
    return phone;
  }
}
