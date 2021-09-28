package com.ttcsolutions.kwapp.core.mapper;

import com.ttcsolutions.kwapp.commons.mapper.BeanMapper;
import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.core.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface Entity2LxUser extends BeanMapper<Account, KwAccount> {
  Entity2LxUser INSTANCE = Mappers.getMapper(Entity2LxUser.class);
}
