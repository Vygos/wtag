package org.wex.infrastructure.web.validator.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DateISOValidator implements ConstraintValidator<DateISO, String> {


    public void initialize(DateISOValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
