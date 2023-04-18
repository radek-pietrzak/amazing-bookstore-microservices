package com.productservice.validation.validator;

import com.productservice.validation.annotation.LanguageCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class LanguageCodeValidator implements ConstraintValidator<LanguageCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNotBlank(value) && LocaleUtils.isAvailableLocale(new Locale(value));
    }

}
