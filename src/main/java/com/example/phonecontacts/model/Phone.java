package com.example.phonecontacts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Phone {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false, unique = true)
  @Pattern(regexp = "\\+\\d{7,15}")
  @EqualsAndHashCode.Include
  private String number;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @ToString.Exclude
  private Contact contact;
}
