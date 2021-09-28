package com.ttcsolutions.kwapp.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ttcsolutions.kwapp.core.model.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, InsertUpdateRepository<Device>, JpaSpecificationExecutor<Device> {


  boolean existsById(Long id);
  Optional<Device> findByDeviceCode(String deviceCode);
}
