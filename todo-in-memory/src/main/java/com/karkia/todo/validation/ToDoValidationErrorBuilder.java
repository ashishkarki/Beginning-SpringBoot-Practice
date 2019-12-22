package com.karkia.todo.validation;

import lombok.val;
import org.springframework.validation.Errors;

/**
 * factory that helps build the ToDoValidationError instance
 */
public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingErrors(Errors errors) {
        val error = new ToDoValidationError("Validation  failed."
                + errors.getErrorCount() + " error(s)");

        for (val objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }

        return error;
    }
}
