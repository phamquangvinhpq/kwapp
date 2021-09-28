package com.ttcsolutions.kwapp.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Account extends BaseEntity<Account> {
  @Id
  @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
  private Long id;
  private String email;
  private String password;
  private String avatar;
  @Override
  protected Account self() {
    return this;
  }
}
