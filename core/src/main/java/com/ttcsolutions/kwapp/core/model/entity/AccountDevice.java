package com.ttcsolutions.kwapp.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.ttcsolutions.kwapp.core.model.AccountDeviceStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * The entity, which is communicate with data base
 * @author mahai
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AccountDevice extends BaseEntity<AccountDevice> {
  @Id
  @SequenceGenerator(name = "user_device_id_seq", sequenceName = "user_device_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_device_id_seq")
  private Long id;
  private Long accountId;
  private Long deviceId;
  private String deviceCode;
  
  @Enumerated(EnumType.ORDINAL)
  private AccountDeviceStatus status;
  private boolean admin;
  private String icon;
  private String relationship;
  private String deviceName;
  @Override
  protected AccountDevice self() {
    return this;
  }
}
