package pl.maciejkizlich.interview.validators;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import pl.maciejkizlich.interview.persistence.model.User;

@Component
public class UserValidator implements Validator {


    public static final String PASSWORD_TO_SHORT_ERROR = "Password to short. Type min 3 chars";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required(error_code)", "Field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required(error_code)", "Field is required.");

        User user = (User) o;

        if (EmailValidator.getInstance().isValid(user.getUsername()) == false) {
            errors.reject("Invalid email format", "Invalid email format");
        }

        if(user.getPassword().length() < 3){
            errors.rejectValue("password", PASSWORD_TO_SHORT_ERROR, PASSWORD_TO_SHORT_ERROR);
        }

    }
}
