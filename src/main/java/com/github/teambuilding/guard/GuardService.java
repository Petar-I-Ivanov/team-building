package com.github.teambuilding.guard;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;

public class GuardService {

	private Guard guard;
	
	private BuildingService buildingService;
	private HeroService heroService;
	
	public GuardService(BuildingService buildingService, HeroService heroService) {
		
		this.buildingService = buildingService;
		this.heroService = heroService;
		
		this.guard = new Guard(randomShownLocation());
	}
	
	public String getSign(Position position) {
		return (isGuardAtPosition(position)) ? guard.getSign() : null;
	}
	
	public void move(int turn) {
		
		if (guard.isSleep()) {
			
			if (turn - guard.getTurnToBeAsleep() == 4) {
				guard = new Guard(randomShownLocation());
			}
			
			return;
		}
		
		Position newPosition = Position.getPositionBasedOnDirection(this.guard.getLocation(), RandomGenerator.getRandomMove());
		
		while (isNextPositionNotInBordersOrNotFree(newPosition)) {
			newPosition = Position.getPositionBasedOnDirection(this.guard.getLocation(), RandomGenerator.getRandomMove());
		}
		
		guard.setLocation(newPosition);
		
		if (heroService.isHeroAroundPosition(guard.getLocation())) {
			
			if (isShootSuccessful()) {
				setGuardToSleep(turn);
				heroService.kill();
				return;
			}
			
			setAtCorner();
		}
	}
	
	public boolean isGuardAtPosition(Position position) {
	  return Position.arePositionsEqual(this.guard.getLocation(), position);
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
		guard.setTurnToBeAsleep(turn);
	}
	
	private boolean isNextPositionNotInBordersOrNotFree(Position position) {
		return !Position.isPositionInBorders(position) || isPositionNotFree(position);
	}
	
	private boolean isPositionNotFree(Position position) {
		return buildingService.isPositionBuilding(position) || heroService.isPositionHero(position);
	}
}
