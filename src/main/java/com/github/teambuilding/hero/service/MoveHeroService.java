package com.github.teambuilding.hero.service;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SpyHero;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class MoveHeroService {

	private SpyHero spy;
	private Hero[] order;

	public MoveHeroService(SpyHero spy, Hero[] order) {
		this.spy = spy;
		this.order = order;
	}

	public void move(char direction) {

		Position newPosition = getNewPositionFromChar(direction);

		if (isPositionNotPossibleToMove(newPosition)) {
			throw new IllegalArgumentException("Invalid move!");
		}

		for (Hero hero : order) {
			Position oldHeroLocation = hero.getLocation();
			hero.setLocation(newPosition);
			newPosition = oldHeroLocation;
		}
	}

	private boolean isPositionNotPossibleToMove(Position position) {

		return !((isPositionNextStep(position) ||
				(this.spy.isAlive() && this.spy.passiveAbility(this.order[0].getLocation(), position)))
				&& isPositionFreeOrEntryPossible(position));
	}

	private boolean isPositionNextStep(Position position) {

		Position firstHeroLocation = order[0].getLocation();
		
		int rowDifference = Math.abs(firstHeroLocation.getRow() - position.getRow());
		int colDifference = Math.abs(firstHeroLocation.getCol() - position.getCol());

		return (rowDifference == 1 && colDifference == 0) || (rowDifference == 0 && colDifference == 1);
	}

	private boolean isPositionFreeOrEntryPossible(Position position) {

		// TODO: check if position is free or possible to enter
		// return GameController.isPositionFree(position) || BaseBuildingsController.isEntryPossible(position);
		return true;
	}

	private Position getNewPositionFromChar(char way) {

		Position oldPosition = order[0].getLocation();

		Position newPosition = switch (way) {
			case Constants.FORWARD_MOVE -> new Position(oldPosition.getRow() - 1, oldPosition.getCol());
			case Constants.BACK_MOVE -> new Position(oldPosition.getRow() + 1, oldPosition.getCol());
			case Constants.LEFT_MOVE -> new Position(oldPosition.getRow(), oldPosition.getCol() - 1);
			case Constants.RIGHT_MOVE -> new Position(oldPosition.getRow(), oldPosition.getCol() + 1);
			default -> throw new IllegalArgumentException("Invalid move direction.");
		};

		return positionModifyIfAboveTheBorders(newPosition);
	}

	// modifies position if row or col passes gameboard's borders
	private static Position positionModifyIfAboveTheBorders(Position position) {

		if (position.getRow() == Constants.GAMEBOARD_MAX_ROW) {
			return new Position(0, position.getCol());
		}

		if (position.getCol() == Constants.GAMEBOARD_MAX_COL) {
			return new Position(position.getRow(), 0);
		}

		if (position.getRow() == -1) {
			return new Position(Constants.GAMEBOARD_MAX_ROW - 1, position.getCol());
		}

		if (position.getCol() == -1) {
			return new Position(position.getRow(), Constants.GAMEBOARD_MAX_COL - 1);
		}

		return position;
	}
}
