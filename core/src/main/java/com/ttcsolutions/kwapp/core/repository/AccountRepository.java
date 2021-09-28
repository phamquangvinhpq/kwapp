package com.ttcsolutions.kwapp.core.repository;

import com.ttcsolutions.kwapp.core.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, InsertUpdateRepository<Account>, JpaSpecificationExecutor<Account> {


  boolean existsById(Long id);

  Optional<Account> findByEmail(String email);
}
