package com.ttcsolutions.kwapp.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Phonebook extends BaseEntity<Phonebook> {

	@Id
	@SequenceGenerator(name = "Phonebook_id_seq", sequenceName = "Phonebook_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Phonebook_id_seq")
	private Long id;
	private String relationship;
	private String phone;

	@Override
	protected Phonebook self() {
		
		return this;
	}

}
