package com.example.phonecontacts.controller;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.dto.user.UserCreatedDto;
import com.example.phonecontacts.dto.user.UserCreationDto;
import com.example.phonecontacts.dto.user.UserDto;
import com.example.phonecontacts.mapper.Mapper;
import com.example.phonecontacts.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Controller")
@RestController
public class UserController {
  private UserService userService;
  private Mapper mapper;

  @GetMapping
  @SecurityRequirement(name = "Auth")
  @Operation(summary = "Get current user with all his contacts")
  public ResponseEntity<UserDto> getMe(Principal principal) {
    return ResponseEntity.ok(mapper.toUserDto(userService.getByLogin(principal.getName())));
  }

  @PostMapping("/sign-up")
  @Operation(summary = "Create new user", responses = @ApiResponse(responseCode = "201"))
  public ResponseEntity<UserCreatedDto> create(@RequestBody UserCreationDto user) {
    UserCreatedDto userCreatedDto = mapper.toUserCreatedDto(userService.create(user));
    return ResponseEntity.created(URI.create("/")).body(userCreatedDto);
  }

  @GetMapping("/contacts")
  @SecurityRequirement(name = "Auth")
  @Operation(summary = "Get all user`s contacts")
  public ResponseEntity<List<ContactDto>> contacts(Principal principal) {
    return ResponseEntity.ok(
        userService.getByLogin(principal.getName())
            .getContacts()
            .stream()
            .map(mapper::toContactDto)
            .collect(Collectors.toList()));
  }
}
