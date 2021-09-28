package com.ttcsolutions.kwapp.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ttcsolutions.kwapp.commons.mapper.BeanMapper;
import com.ttcsolutions.kwapp.core.model.entity.Phonebook;
import com.ttcsolutions.kwapp.core.model.request.PhonebookRequest;

@Mapper
public interface PhoneBook2EntityMapper extends BeanMapper<PhonebookRequest, Phonebook> {
  PhoneBook2EntityMapper INSTANCE = Mappers.getMapper(PhoneBook2EntityMapper.class);
}
