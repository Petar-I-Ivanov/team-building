package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.BigBuilding;
import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.building.model.MediumBuilding;
import com.github.teambuilding.building.model.SmallBuilding;
import com.github.teambuilding.utility.Position;

public class BaseBuildingService {

	private SmallBuilding smallB;
	private MediumBuilding mediumB;
	private BigBuilding bigB;
	
	public BaseBuildingService(SmallBuilding small, MediumBuilding medium, BigBuilding big) {
		this.smallB = small;
		this.mediumB = medium;
		this.bigB = big;
	}
	
	public String getSign(Position position) {

		return (smallB.isPositionBuilding(position))  ? smallB.getSign()
			 : (mediumB.isPositionBuilding(position)) ? mediumB.getSign()
			 : (bigB.isPositionBuilding(position)) 	 ? bigB.getSign()
			 : null;
	}

	public boolean isPositionBuilding(Position position) {
		
		return smallB.isPositionBuilding(position) ||
			   mediumB.isPositionBuilding(position) ||
			   bigB.isPositionBuilding(position);
	}

	public boolean isEntryPossible(Position position) {
		
		return smallB.isEntryPossible(position) ||
			   mediumB.isEntryPossible(position) ||
			   bigB.isEntryPossible(position);
	}

	public void explodePosition(Position position) {

		Building building = getBuildingByPosition(position);

		if (building == null) {
			return;
		}

		if (!building.isDestroyed()) {
			building.addExplode(position);
		}

		if (building.isDestroyed()) {
			building.cleansePositions();
		}
	}

	public boolean areBuildingsDestroyed() {
		
		return smallB.isDestroyed() &&
			   mediumB.isDestroyed() &&
			   bigB.isDestroyed();
	}

	private Building getBuildingByPosition(Position position) {

		return (smallB.isPositionBuilding(position)) ? smallB
			  : mediumB.isPositionBuilding(position) ? mediumB
			  : bigB.isPositionBuilding(position) ? bigB
			  : null;
	}
}
