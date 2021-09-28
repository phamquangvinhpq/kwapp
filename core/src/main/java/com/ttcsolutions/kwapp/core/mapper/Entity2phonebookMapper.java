package com.ttcsolutions.kwapp.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ttcsolutions.kwapp.commons.mapper.BeanMapper;
import com.ttcsolutions.kwapp.core.model.entity.Phonebook;
import com.ttcsolutions.kwapp.core.model.response.PhonebookResponse;

@Mapper
public interface Entity2phonebookMapper extends BeanMapper<Phonebook, PhonebookResponse> {
  Entity2phonebookMapper INSTANCE = Mappers.getMapper(Entity2phonebookMapper.class);
}
