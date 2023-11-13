package org.wex.infrastructure.web.validator.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateISOValidator.class)
public @interface DateISO {
    String message() default "The date should be in a valid yyyy-MM-dd format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
