package com.ttcsolutions.kwapp.core.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PhonebookResponse {

	private String relationship;
	private String phone;

}
