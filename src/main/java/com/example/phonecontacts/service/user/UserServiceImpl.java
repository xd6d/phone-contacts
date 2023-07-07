package com.example.phonecontacts.service.user;

import com.example.phonecontacts.data.UserRepository;
import com.example.phonecontacts.dto.user.UserCreationDto;
import com.example.phonecontacts.mapper.Mapper;
import com.example.phonecontacts.model.User;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private Mapper mapper;
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public User create(UserCreationDto userCreationDto) {
    userCreationDto.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
    User user = mapper.toUser(userCreationDto);
    return userRepository.save(user);
  }

  @Override
  public User getByLogin(String login) {
    Optional<User> user = userRepository.findByLogin(login);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Incorrect login");
    }
    return user.get();
  }
}
