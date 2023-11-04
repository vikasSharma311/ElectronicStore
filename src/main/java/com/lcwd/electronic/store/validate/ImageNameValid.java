package com.lcwd.electronic.store.validate;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageNameValidator.class)
public @interface  ImageNameValid {
    //error message
    java.lang.String message() default "Invalid Image name ";

    //reperesent grop of contraints
    java.lang.Class<?>[] groups() default {};

    //additional information about annotations
    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};

}
