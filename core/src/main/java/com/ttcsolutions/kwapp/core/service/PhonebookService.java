package com.ttcsolutions.kwapp.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ttcsolutions.kwapp.core.model.request.PhonebookRequest;
import com.ttcsolutions.kwapp.core.model.response.PhonebookResponse;


public interface PhonebookService {
	
	Page<PhonebookResponse> getPhoneBook(Pageable pageable);

	PhonebookResponse create(PhonebookRequest phonebookRequest);

	void delete(long id);


}
