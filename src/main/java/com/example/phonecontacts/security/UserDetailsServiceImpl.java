package com.example.phonecontacts.security;

import com.example.phonecontacts.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) {
    return userRepository.findByLogin(login)
        .orElseThrow(() -> new UsernameNotFoundException("Incorrect login"));
  }
}
