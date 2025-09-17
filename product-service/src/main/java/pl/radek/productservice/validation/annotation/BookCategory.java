package pl.radek.productservice.validation.annotation;

import pl.radek.productservice.validation.validator.BookCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {BookCategoryValidator.class})
public @interface BookCategory {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
