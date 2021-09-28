package com.ttcsolutions.kwapp.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ttcsolutions.kwapp.core.model.entity.Phonebook;
import com.ttcsolutions.kwapp.core.model.response.PhonebookResponse;

@Repository
public interface PhonebookRepository
		extends JpaRepository<Phonebook, Long>, InsertUpdateRepository<Phonebook>, JpaSpecificationExecutor<Phonebook> {

	boolean existsById(Long id);

	@Query("SELECT p FROM Phonebook p")
	Page<PhonebookResponse> getPhoneBook(Pageable pageable);

}
