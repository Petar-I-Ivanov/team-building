package com.github.teambuilding.hero.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class SpyHero extends Hero {

	public SpyHero() {
		super("3", new Position(14, 13), 3);
	}
	
	public boolean passiveAbility(Position fromPosition, Position toPosition) {
		
		return ((fromPosition.getRow() == 0 && toPosition.getRow() == Constants.GAMEBOARD_MAX_ROW - 1)
			|| (fromPosition.getCol() == 0 && toPosition.getCol() == Constants.GAMEBOARD_MAX_COL - 1)
			|| (fromPosition.getRow() == Constants.GAMEBOARD_MAX_ROW - 1 && toPosition.getRow() == 0)
			|| (fromPosition.getCol() == Constants.GAMEBOARD_MAX_COL - 1 && toPosition.getCol() == 0));
	}
}
