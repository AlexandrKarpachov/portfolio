package ua.carsale.validators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import ua.carsale.domain.Car;

@Component
public class CarValidator implements Validator {

	private static final String EMPTY_FIELD = "none";
	@Autowired
	EngineValidator engineValidator;
	
	@Autowired
	BodyValidator bodyValidator;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Car.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Car car = (Car) target;
		if (car.getBrand().equals(EMPTY_FIELD)) {
			 errors.rejectValue("brand", "Required");
		}
		
		if (car.getModel().equals(EMPTY_FIELD)) {
			 errors.rejectValue("model", "Required");
		}
		
		if (car.getPrice() == null) {
			errors.rejectValue("price", "Required");
		} else if (car.getPrice() <= 0) {
			errors.rejectValue("price", "size.car.price");
		}
		
		List<MultipartFile> images = car.getImgFiles(); 
		for (MultipartFile img : images) {
			if (!img.getContentType().startsWith("image/") && !img.isEmpty()) {
				errors.rejectValue("imgFiles", "fileType.car.imgFiles");
			}
		}
		
		errors.pushNestedPath("engine");
		ValidationUtils.invokeValidator(engineValidator, car.getEngine(), errors);
		errors.popNestedPath();
		
		errors.pushNestedPath("body");
		bodyValidator.validate(car.getBody(), errors);
		errors.popNestedPath();
		
		
	}

}
