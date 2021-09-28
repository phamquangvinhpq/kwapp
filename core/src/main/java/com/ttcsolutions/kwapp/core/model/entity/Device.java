package com.ttcsolutions.kwapp.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.ttcsolutions.kwapp.core.model.DeviceStatus;

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
public class Device extends BaseEntity<Device> {
	
  @Id
  @SequenceGenerator(name = "device_id_seq", sequenceName = "device_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_id_seq")
  private Long id;
  private String deviceCode;
  
  @Enumerated(EnumType.ORDINAL)
  private DeviceStatus status;
  
  @Override
  protected Device self() {
    return this;
  }
}
