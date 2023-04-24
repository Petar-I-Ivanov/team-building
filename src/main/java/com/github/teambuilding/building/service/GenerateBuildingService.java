package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.BigBuilding;
import com.github.teambuilding.building.model.MediumBuilding;
import com.github.teambuilding.building.model.SmallBuilding;
import com.github.teambuilding.utility.Position;

public class GenerateBuildingService {

	private SmallBuilding smallB;
	private MediumBuilding mediumB;
	private BigBuilding bigB;
	
	public GenerateBuildingService(SmallBuilding small, MediumBuilding medium, BigBuilding big) {
		this.smallB = small;
		this.mediumB = medium;
		this.bigB = big;
	}
	
	public void generateBuildings() {

		Position position = new Position();

		while (!smallB.canSetBuilding(position)) {
			position = new Position();
		}

		smallB.set(position);
		position = new Position();

		while (!mediumB.canSetBuilding(position) || willMediumBuildingBeOverSmall(position)) {
			position = new Position();
		}

		mediumB.set(position);
		position = new Position();

		while (!bigB.canSetBuilding(position) || willBigBuildingBeOverTheRest(position)) {
			position = new Position();
		}

		bigB.set(position);

		if (!doBuildingsHaveThreeRowOrColSpace()) {
			generateBuildings();
		}
	}

	private boolean willMediumBuildingBeOverSmall(Position position) {

		for (int row = position.getRow(); row <= position.getRow() + 2; row++) {
			for (int col = position.getCol(); col < position.getCol() + 3; col++) {
				if (smallB.isPositionBuilding(new Position(row, col))) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean willBigBuildingBeOverTheRest(Position position) {

		for (int row = position.getRow(); row <= position.getRow() + 3; row++) {
			for (int col = position.getCol(); col < position.getCol() + 3; col++) {
				if (smallB.isPositionBuilding(new Position(row, col))
						|| mediumB.isPositionBuilding(new Position(row, col))) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean doBuildingsHaveThreeRowOrColSpace() {

		return doSmallAndMediumHaveThreeSpace() &&
			   doSmallAndBigHaveThreeSpace() &&
			   doMediumAndBigHaveThreeSpace();
	}

	private boolean doSmallAndMediumHaveThreeSpace() {

		Position smallPosition = smallB.getStartingPosition();
		Position mediumPosition = mediumB.getStartingPosition();

		return Math.abs(smallPosition.getRow() - mediumPosition.getRow()) == 5
			|| Math.abs(smallPosition.getCol() - mediumPosition.getCol()) == 6;
	}

	private boolean doSmallAndBigHaveThreeSpace() {

		Position smallPosition = smallB.getStartingPosition();
		Position bigPosition = bigB.getStartingPosition();

		return Math.abs(smallPosition.getRow() - bigPosition.getRow()) == 6
			|| Math.abs(smallPosition.getCol() - bigPosition.getCol()) == 6;
	}

	private boolean doMediumAndBigHaveThreeSpace() {

		Position mediumPosition = mediumB.getStartingPosition();
		Position bigPosition = bigB.getStartingPosition();

		return Math.abs(mediumPosition.getRow() - bigPosition.getRow()) == 6
			|| Math.abs(mediumPosition.getCol() - bigPosition.getCol()) == 7;
	}
}
