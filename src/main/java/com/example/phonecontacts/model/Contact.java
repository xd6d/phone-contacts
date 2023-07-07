package com.example.phonecontacts.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "contacts")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contact {
  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  @Column(unique = true)
  @EqualsAndHashCode.Include
  private String name;
  @ManyToOne
  private User user;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Set<Email> emails;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Set<Phone> phones;
}
