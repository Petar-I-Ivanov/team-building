package com.github.teambuilding.input;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.teambuilding.utility.Constants;

public class AvailableCommandValidator implements ConstraintValidator<AvailableCommand, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value.length() == 1) {

			char letter = value.charAt(0);

			if (isMovementCommand(letter) || isHeroSwapCommand(letter) || isSpecialAbilityCommand(letter)) {
				return true;
			}
		}

		return false;
	}

	private boolean isMovementCommand(char value) {

		return value == Constants.FORWARD_MOVE || value == Constants.RIGHT_MOVE || value == Constants.BACK_MOVE
				|| value == Constants.LEFT_MOVE;
	}

	private boolean isHeroSwapCommand(char value) {
		return value == Constants.HEROES_SWAP;
	}

	private boolean isSpecialAbilityCommand(char value) {
		return value == Constants.SPECIAL_ABILITY;
	}
}
