package com.github.teambuilding.services.hero;

import com.github.teambuilding.models.hero.Hero;
import com.github.teambuilding.models.hero.SpyHero;
import com.github.teambuilding.repositories.HeroRepository;
import com.github.teambuilding.services.building.BuildingService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeroMoveService {

  private HeroRepository heroRepository;
  private BuildingService buildingService;

  public HeroMoveService(HeroRepository heroRepository, BuildingService buildingService) {

    this.heroRepository = heroRepository;
    this.buildingService = buildingService;
  }

  public void move(Long gameId, char direction) {

    List<Hero> heroes = heroRepository.findByGameId(gameId);
    Position firstInOrderPosition = heroes.get(0).getLocation();

    Position newPosition = Position.getPositionBasedOnDirection(firstInOrderPosition, direction);

    if (isPositionOverTheWallAndUnavailable(gameId, firstInOrderPosition, newPosition)) {
      throw new IllegalArgumentException("Moving through walls is not available.");
    }

    newPosition = Position.modifyPositionIfOutOfBorders(newPosition);

    if (isMovementImpossible(gameId, newPosition)) {
      throw new IllegalArgumentException("Next position is occupied.");
    }

    moveHeroes(heroes, newPosition);
    heroRepository.save(heroes);
  }

  private boolean isPositionOverTheWallAndUnavailable(Long gameId, Position fromPosition,
      Position toPosition) {

    SpyHero spy = (SpyHero) heroRepository.findByGameIdAndSign(gameId, Constants.SPY_HERO);
    return !Position.isPositionInBorders(toPosition)
        && !(spy != null && spy.isMovementOverTheWall(fromPosition, toPosition));
  }

  private boolean isMovementImpossible(Long gameId, Position position) {

    return isPositionBuildingAndNotEntryPossible(gameId, position)
        || isPositionHero(gameId, position);
  }

  private boolean isPositionBuildingAndNotEntryPossible(Long gameId, Position position) {

    return buildingService.isPositionBuilding(position, gameId)
        && !buildingService.isEntryPossible(position, gameId);
  }

  private boolean isPositionHero(Long gameId, Position position) {
    return heroRepository.findByGameIdAndPosition(gameId, position) != null;
  }

  private static void moveHeroes(List<Hero> heroes, Position nextPosition) {

    for (Hero hero : heroes) {

      Position tempPosition = hero.getLocation();
      hero.setLocation(nextPosition);
      nextPosition = tempPosition;
    }
  }
}
