package org.wex.infrastructure.web.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ViolationsHandler<T> {

    private final int status = Response.Status.BAD_REQUEST.getStatusCode();
    public List<ValidationError> errors = new ArrayList<>();

    public ViolationsHandler(Set<ConstraintViolation<T>> constraints) {
        this.errors = buildErrors(constraints);
    }

    private List<ValidationError> buildErrors(Set<ConstraintViolation<T>> constraints) {
        var errors = new ArrayList<ValidationError>();
        for (ConstraintViolation<?> constraint : constraints) {
            var fieldName = constraint.getPropertyPath().toString();
            var error = constraint.getMessage();
            errors.add(new ValidationError(fieldName, error));
        }
        return errors;
    }

    public static <T> void throwIfHasViolations(Set<ConstraintViolation<T>> violations) throws BadRequestException {
        if (!violations.isEmpty()) {
            var response = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ViolationsHandler<>(violations))
                    .build();
            throw new BadRequestException(response);
        }
    }

    public int getStatus() {
        return status;
    }

    public record ValidationError(String field, String error) {
    }

}
