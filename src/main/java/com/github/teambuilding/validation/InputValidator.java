package com.github.teambuilding.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.teambuilding.utility.Constants;

public class InputValidator implements ConstraintValidator<ValidInput, Input> {

	@Override
	public boolean isValid(Input input, ConstraintValidatorContext context) {
	
		return isCommandLengthOneAndHeroPickLengthOneOrNull(input)
			&& (isCommandMoveOrSpecialAbilityAndHeroPickNull(input)
			|| isCommandHeroSwapAndHeroPickAvailable(input));
		
	}
	
	private boolean isCommandLengthOneAndHeroPickLengthOneOrNull(Input input) {
		
		return input.getCommand().length() == 1
			&& (input.getHeroPick() == null || input.getHeroPick().length() == 1);
	}

	private boolean isCommandMoveOrSpecialAbilityAndHeroPickNull(Input input) {
		
		char command = input.getCommand().charAt(0);
		
		return (Constants.isCharMovementAction(command) || Constants.isCharSpecialAbilityAction(command))
			&& input.getHeroPick() == null;
	}
	
	private boolean isCommandHeroSwapAndHeroPickAvailable(Input input) {
		
		return Constants.isCharHeroesSwapAction(input.getCommand().charAt(0))
			&& Constants.isCharHeroSign(input.getHeroPick().charAt(0));
	}
}
