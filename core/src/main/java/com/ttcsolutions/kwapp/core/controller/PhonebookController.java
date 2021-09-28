package com.ttcsolutions.kwapp.core.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.commons.util.Pageables;
import com.ttcsolutions.kwapp.core.model.request.PhonebookRequest;
import com.ttcsolutions.kwapp.core.model.response.PhonebookResponse;
import com.ttcsolutions.kwapp.core.service.PhonebookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/phonebook")
@Validated
@RequiredArgsConstructor
public class PhonebookController {
	private final PhonebookService PhonebookService;
	
	/**
     
     * API add phone number contacts
     * @param PhonebookRequest
     * @return
     */
	
	@PostMapping
	public Response<PhonebookResponse> create(@Valid @RequestBody PhonebookRequest PhonebookRequest) {
		return Response.ofSucceeded(PhonebookService.create(PhonebookRequest));
	}
	
	
	@GetMapping
	public Response<List<PhonebookResponse>> getphonebook(@RequestParam int page, @RequestParam int size,
			@RequestParam(required = false) String sort) {
		var res = PhonebookService.getPhoneBook(Pageables.of(page, size, sort));
		return Response.ofSucceeded(res);
	}
	

	@DeleteMapping("{id}")
	public void deletephonebook(@PathVariable Long id) {
	PhonebookService.delete(id);
		
	}

}
