package com.productservice.validation.validator;

import com.productservice.entity.Category;
import com.productservice.validation.annotation.BookCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class BookCategoryValidator implements ConstraintValidator<BookCategory, List<String>> {
    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        try {
            values.forEach(Category::valueOf);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
