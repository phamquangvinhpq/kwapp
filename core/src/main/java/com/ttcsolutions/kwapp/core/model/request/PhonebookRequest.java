package com.ttcsolutions.kwapp.core.model.request;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)

public class PhonebookRequest {

	@NotBlank
	private String relationship;
	@NotBlank
	private String phone;

	

}
