package ua.carsale.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.carsale.domain.Body;

@Component
public class BodyValidator  implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Body.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "Required");
	}

}
