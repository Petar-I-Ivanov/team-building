package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class SmallBuilding extends Building {

	public SmallBuilding() {
		super(Constants.SMALL_BUILDING,
			  Constants.SMALL_B_MAX_ROW,
			  Constants.SMALL_B_MAX_COL);
	}

	@Override
	public boolean isEntryPossible(Position position) {
		
		for (Position location : this.locations) {
			if (arePositionsEqual(location, position)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected boolean isExplodeFatal() {
		return this.explodedPositions.size() >= 1;
	}
}
