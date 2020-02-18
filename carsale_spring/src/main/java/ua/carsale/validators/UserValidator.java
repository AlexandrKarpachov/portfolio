package ua.carsale.validators;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.carsale.domain.User;
import ua.carsale.persistance.UserRepository;

/**
 * Validator for {@link ua.carsale.domain.User} class,
 * implements {@link Validator} interface.
 *
 * @author Aleksandr Karpachov
 * @version 1.0
 */

@Component
public class UserValidator implements Validator {

    private final UserRepository userService;

    @Autowired
    public UserValidator(UserRepository userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Required");
        if (user.getLogin().length() < 4 || user.getLogin().length() > 32) {
            errors.rejectValue("login", "Size.userForm.username");
        }

        if (userService.findByLogin(user.getLogin()) != null) {
            errors.rejectValue("login", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
        
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if (user.getName().length() < 3) {
        	errors.rejectValue("name", "MinSize.userForm.name");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Required");
        
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Required");
        if (!user.getPhone().matches("[0-9]+")) {
        	errors.rejectValue("phone", "Num.userForm.phone");
        } else if (user.getPhone().length() < 9) {
        	errors.rejectValue("phone", "MinSize.userForm.phone");
        }
       
    }
}