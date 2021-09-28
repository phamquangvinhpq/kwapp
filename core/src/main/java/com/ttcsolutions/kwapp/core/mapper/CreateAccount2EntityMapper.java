package com.ttcsolutions.kwapp.core.mapper;

import com.ttcsolutions.kwapp.commons.mapper.BeanMapper;
import com.ttcsolutions.kwapp.core.model.entity.Account;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateAccount2EntityMapper extends BeanMapper<CreateAccountRequest, Account> {
  CreateAccount2EntityMapper INSTANCE = Mappers.getMapper(CreateAccount2EntityMapper.class);
}
