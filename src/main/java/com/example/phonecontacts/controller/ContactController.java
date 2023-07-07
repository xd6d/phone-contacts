package com.example.phonecontacts.controller;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.mapper.Mapper;
import com.example.phonecontacts.model.Contact;
import com.example.phonecontacts.model.User;
import com.example.phonecontacts.service.contact.ContactService;
import com.example.phonecontacts.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Contact Controller")
@SecurityRequirement(name = "Auth")
@RestController
public class ContactController {
  private UserService userService;
  private ContactService contactService;
  private Mapper mapper;

  @PostMapping
  @Operation(summary = "Create new contact", responses = @ApiResponse(responseCode = "201"),
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
          (content = @Content(schema = @Schema(implementation = ContactDto.class))))
  public ResponseEntity<ContactDto> createContact(Principal principal,
                                                  @RequestBody Contact contact) {
    User user = userService.getByLogin(principal.getName());
    contact.setUser(user);
    ContactDto created = mapper.toContactDto(contactService.create(contact));
    return ResponseEntity.created(URI.create("/contacts")).body(created);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get contact by id", responses = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "404", content = @Content())
  })
  public ResponseEntity<ContactDto> getContact(@PathVariable Long id, Principal principal) {
    return ResponseEntity.of(
        contactService.getById(id, principal.getName()).map(mapper::toContactDto));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Update contact by id", responses = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "404", content = @Content())
  })
  public ResponseEntity<ContactDto> updateContact(@PathVariable Long id,
                                                  @RequestBody ContactDto contactDto,
                                                  Principal principal) {
    return ResponseEntity.of(
        contactService.update(id, contactDto, principal.getName()).map(mapper::toContactDto));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete contact by id", responses = @ApiResponse(responseCode = "204"))
  public ResponseEntity<Void> deleteContact(@PathVariable Long id, Principal principal) {
    contactService.delete(id, principal.getName());
    return ResponseEntity.noContent().build();
  }
}
