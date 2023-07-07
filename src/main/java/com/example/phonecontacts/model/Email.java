package com.example.phonecontacts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Email {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false, unique = true)
  @EqualsAndHashCode.Include
  @jakarta.validation.constraints.Email
  private String address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @ToString.Exclude
  private Contact contact;
}
