package com.github.teambuilding.dto;

import com.github.teambuilding.game.GameStatusEnum;

import lombok.Data;

@Data
public class GameDto {

	private long id;
	private GameStatusEnum status;
	private String[][] gameboard;
}
