package pl.radek.productservice.validation.validator;

import pl.radek.productservice.entity.Category;
import pl.radek.productservice.validation.annotation.BookCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class BookCategoryValidator implements ConstraintValidator<BookCategory, List<String>> {
    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }

        try {
            values.forEach(Category::valueOf);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
