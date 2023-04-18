package com.productservice.validation.validator;

import com.productservice.validation.annotation.LocalDatePattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDatePatternValidator implements ConstraintValidator<LocalDatePattern, String> {
    @Override
    @SuppressWarnings(value = "unused")
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            LocalDate parse = LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

}
