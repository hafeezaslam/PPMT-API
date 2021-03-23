package com.hafeez.ppmtool.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConfirmPasswordConstraintValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {

    String message() default "passwords must match";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};



}
