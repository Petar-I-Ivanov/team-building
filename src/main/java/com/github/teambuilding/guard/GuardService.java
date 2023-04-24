package com.github.teambuilding.guard;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;

public class GuardService {

	private int turnToBeAsleep;
	private Guard guard;
	
	private BuildingService buildingService;
	private HeroService heroService;
	
	public GuardService(BuildingService buildingService, HeroService heroService) {
		
		this.heroService = heroService;
		this.buildingService = buildingService;
		
		generateGuard();
	}
	
	public void generateGuard() {
		this.guard = new Guard(randomShownLocation());
		turnToBeAsleep = 0;
	}
	
	public String getSign(Position position) {
		return (isGuardAtPosition(position)) ? guard.getSign() : null;
	}
	
	public boolean move(int turn) {
		
		if (guard.isSleep()) {
			
			if (turn - turnToBeAsleep == 4) {
				generateGuard();
			}
			
			return false;
		}
		
		Position newPosition = getNewPositionFromChar(RandomGenerator.getRandomMove());
		
		while (!isPositionInBorders(newPosition) || isPositionNotFree(newPosition)) {
			newPosition = getNewPositionFromChar(RandomGenerator.getRandomMove());
		}
		
		guard.setLocation(newPosition);
		
		if (heroService.isHeroAroundPosition(guard.getLocation())) {
			
			if (isShootSuccessful()) {
				setGuardToSleep(turn);
				return true;
			}
			
			setAtCorner();
			return false;
		}
		
		return false;
	}
	
	public boolean isGuardAtPosition(Position position) {
		
	  return guard.getLocation().getRow() == position.getRow() &&
	         guard.getLocation().getCol() == position.getCol();
	}
	
	public void kill(int turn) {
	  setGuardToSleep(turn);
	}
	
	private Position randomShownLocation() {
		
		Position location = new Position();
		
		while (isPositionNotFree(location)) {
			location = randomShownLocation();
		}
		
		return location;
	}
	
	private Position getNewPositionFromChar(char direction) {
		
		Position location = guard.getLocation();
		
		return switch (direction) {
			case Constants.FORWARD_MOVE -> new Position(location.getRow() - 1, location.getCol());
			case Constants.BACK_MOVE -> new Position(location.getRow() + 1, location.getCol());
			case Constants.LEFT_MOVE -> new Position(location.getRow(), location.getCol() - 1);
			case Constants.RIGHT_MOVE -> new Position(location.getRow(), location.getCol() + 1);
			default -> null;
		};
	}
	
	private static boolean isShootSuccessful() {
		return RandomGenerator.twentyFourSidedDice() % 11 == 0;
	}
	
	private void setAtCorner() {
		
		switch (RandomGenerator.getRandomValue(4)) {
			case 0 -> guard.setLocation(new Position(0, 0));
			case 1 -> guard.setLocation(new Position(0, Constants.GAMEBOARD_MAX_COL - 1));
			case 2 -> guard.setLocation(new Position(Constants.GAMEBOARD_MAX_ROW - 1, 0));
			case 3 -> guard.setLocation(new Position(Constants.GAMEBOARD_MAX_ROW - 1, Constants.GAMEBOARD_MAX_COL - 1));
		}
	}
	
	private void setGuardToSleep(int turn) {
		guard.setSleep(true);
		guard.setLocation(new Position(-1, -1));
		turnToBeAsleep = turn;
	}
	
	private boolean isPositionInBorders(Position position) {
		return position.getRow() >= 0 && position.getRow() < Constants.GAMEBOARD_MAX_ROW
			&& position.getCol() >= 0 && position.getCol() < Constants.GAMEBOARD_MAX_COL;
	}
	
	private boolean isPositionNotFree(Position position) {
		
		return buildingService.isPositionBuilding(position) || heroService.isPositionHero(position);
	}
}
