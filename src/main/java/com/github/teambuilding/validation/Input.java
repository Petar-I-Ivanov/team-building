package com.github.teambuilding.validation;

import lombok.Data;

@Data
@ValidInput
public class Input {

	private String command;
	private String heroPick;
}
