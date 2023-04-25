package com.github.teambuilding.hero.service.helpers;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SpyHero;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class MoveHeroes {

	private MoveHeroes() {}
	
	public static void moveHeroes(Hero[] heroes, char direction, BuildingService buildingService) {
		
		Position newPosition = Position.getPositionBasedOnDirection(heroes[0].getLocation(), direction);
		SpyHero spy = getSpyHero(heroes);
		
		if (isPositionNotInBordersAndUnavailable(newPosition, spy)) {
			throw new IllegalArgumentException("Moving through walls is not available.");
		}
		
		newPosition = Position.modifyPositionIfOutOfBorders(newPosition);
		
		if (isMovementImpossible(newPosition, buildingService, heroes)) {
			throw new IllegalArgumentException("This movement is not available");
		}
		
		move(heroes, newPosition);
	}
	
	private static SpyHero getSpyHero(Hero[] heroes) {
		
		for (Hero hero : heroes) {
			if (Constants.SPY_HERO.equals(hero.getSign())) {
				return (SpyHero) hero;
			}
		}
		
		return null;
	}
	
	private static boolean isPositionNotInBordersAndUnavailable(Position position, SpyHero spy) {
		return !Position.isPositionInBorders(position) && !spy.isAlive();
	}
	
	private static boolean isMovementImpossible(Position position, BuildingService buildingService, Hero[] heroes) {
		return isPositionBuildingAndNotEntryPossible(position, buildingService) || isPositionHero(position, heroes);
	}
	
	private static boolean isPositionBuildingAndNotEntryPossible(Position position, BuildingService buildingService) {
		return  buildingService.isPositionBuilding(position) && !buildingService.isEntryPossible(position);
	}
	
	private static boolean isPositionHero(Position position, Hero[] heroes) {
		
		for (Hero hero : heroes) {
			if (Position.arePositionsEqual(hero.getLocation(), position)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static void move(Hero[] heroes, Position newPosition) {
		
		for(Hero hero : heroes) {
			
			Position tempPosition = hero.getLocation();
			hero.setLocation(newPosition);
			newPosition = tempPosition;
		}
	}
}
