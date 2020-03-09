package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Publication;

@Component
public class AddPublicationFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Publication.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.publication.title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "Error.publication.text");
	}

}
