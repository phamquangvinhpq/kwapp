package com.ttcsolutions.kwapp.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ttcsolutions.kwapp.commons.mapper.BeanMapper;
import com.ttcsolutions.kwapp.core.model.entity.AccountDevice;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountDeviceRequest;

@Mapper
public interface CreateAccountDevice2EntityMapper extends BeanMapper<CreateAccountDeviceRequest, AccountDevice> {
  CreateAccountDevice2EntityMapper INSTANCE = Mappers.getMapper(CreateAccountDevice2EntityMapper.class);
}
