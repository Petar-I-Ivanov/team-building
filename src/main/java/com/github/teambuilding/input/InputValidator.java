package com.github.teambuilding.input;

import lombok.Data;

@Data
public class InputValidator {

	@AvailableCommand
	private String action;
}
