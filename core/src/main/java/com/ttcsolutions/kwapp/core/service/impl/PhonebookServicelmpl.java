package com.ttcsolutions.kwapp.core.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ttcsolutions.kwapp.core.mapper.Entity2phonebookMapper;
import com.ttcsolutions.kwapp.core.mapper.PhoneBook2EntityMapper;
import com.ttcsolutions.kwapp.core.model.request.PhonebookRequest;
import com.ttcsolutions.kwapp.core.model.response.PhonebookResponse;
import com.ttcsolutions.kwapp.core.repository.PhonebookRepository;
import com.ttcsolutions.kwapp.core.service.PhonebookService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PhonebookServicelmpl implements PhonebookService{
	
	private final PhonebookRepository PhonebookRepository;

	@Override
	public PhonebookResponse create(PhonebookRequest phonebookRequest) {
		var phone = PhoneBook2EntityMapper.INSTANCE.map(phonebookRequest);
		var insertedRec = PhonebookRepository.insert(phone);
		return Entity2phonebookMapper.INSTANCE.map(insertedRec);
	}

	@Override
	public Page<PhonebookResponse> getPhoneBook(Pageable pageable) {
		return PhonebookRepository.getPhoneBook(pageable);
	}

	@Override
	public void delete(long id) {
		PhonebookRepository.deleteById(id);
	}



	

	
	
	
	

	
	



}
