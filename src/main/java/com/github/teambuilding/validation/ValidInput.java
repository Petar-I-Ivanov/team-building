package com.github.teambuilding.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InputValidator.class)
public @interface ValidInput {

	String message() default "Invalid input.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
