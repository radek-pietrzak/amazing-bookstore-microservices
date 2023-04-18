package com.productservice.validation.annotation;

import com.productservice.validation.validator.LanguageCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {LanguageCodeValidator.class})
public @interface LanguageCode {

    String message() default "Invalid language code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
