package com.hafeez.ppmtool.validation;

import com.hafeez.ppmtool.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class  ConfirmPasswordConstraintValidator implements ConstraintValidator<ConfirmPassword, User> {

    @Override
    public void initialize(ConfirmPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword() !=null && user.getPassword().equals(user.getConfirmPassword());
    }
}
