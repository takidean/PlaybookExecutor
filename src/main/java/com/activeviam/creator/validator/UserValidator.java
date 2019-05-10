package com.activeviam.creator.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.activeviam.creator.model.Developper;
import com.activeviam.creator.service.UserService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Developper.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Developper developper = (Developper) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (developper.getUsername().length() < 6 || developper.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(developper.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (developper.getPassword().length() < 8 || developper.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!developper.getPasswordConfirm().equals(developper.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
