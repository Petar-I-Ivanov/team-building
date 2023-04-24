package com.github.teambuilding.input;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@Documented
@Constraint(validatedBy = AvailableCommandValidator.class)
@Target( { ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
@ReportAsSingleViolation
public @interface AvailableCommand {

	String message() default "You are using unsupported command.";

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};}
