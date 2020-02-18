package ua.carsale.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.carsale.domain.Engine;

@Component
public class EngineValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Engine.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Engine engine = (Engine) target;
		
		if (engine.getPower() <= 0) {
			errors.rejectValue("power", "size.engine.power");
		}
		
		if (engine.getVolume() <= 0) {
			errors.rejectValue("volume", "size.engine.volume");
		}
	}

}
