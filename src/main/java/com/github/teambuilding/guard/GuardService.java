package com.github.teambuilding.guard;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuardService {

  private GuardRepository guardRepository;

  private BuildingService buildingService;
  private HeroService heroService;

  public GuardService(BuildingService buildingService, HeroService heroService,
      GuardRepository guardRepository) {

    this.buildingService = buildingService;
    this.heroService = heroService;
    this.guardRepository = guardRepository;
  }

  public void generateGuard(Long gameId) {

    Guard guard = new Guard();

    guard.setGameId(gameId);
    guard.setLocation(randomShownLocation(gameId));

    guardRepository.save(guard);
  }

  public String getSign(Position position, Long gameId) {

    Guard guard = guardRepository.findByGameIdAndPosition(gameId, position);
    return (guard != null) ? guard.getSign() : null;
  }

  public void move(int turn, Long gameId) {

    Guard guard = guardRepository.findByGameId(gameId);

    if (guard.isSleep()) {

      if (turn - guard.getTurnToBeAsleep() == 4) {
        guard.setLocation(randomShownLocation(gameId));
      }

      guardRepository.save(guard);
      return;
    }

    Position newPosition =
        Position.getPositionBasedOnDirection(guard.getLocation(), RandomGenerator.getRandomMove());

    while (isNextPositionNotInBordersOrNotFree(newPosition, gameId)) {
      newPosition = Position.getPositionBasedOnDirection(guard.getLocation(),
          RandomGenerator.getRandomMove());
    }

    guard.setLocation(newPosition);

    if (heroService.isHeroAroundPosition(guard.getLocation(), gameId)) {

      if (isShootSuccessful()) {
        setGuardToSleep(turn, guard);
        heroService.kill(gameId);
        guardRepository.save(guard);
        return;
      }

      setAtCorner(guard);
    }

    guardRepository.save(guard);
  }

  public void kill(int turn, Long gameId) {

    Guard guard = guardRepository.findByGameId(gameId);
    setGuardToSleep(turn, guard);
  }

  public boolean isGuardAtPosition(Position position, Long gameId) {
    return guardRepository.findByGameIdAndPosition(gameId, position) != null;
  }

  private Position randomShownLocation(Long gameId) {

    Position location = new Position();

    while (isPositionNotFree(location, gameId)) {
      location = randomShownLocation(gameId);
    }

    return location;
  }

  private static boolean isShootSuccessful() {
    return RandomGenerator.twentyFourSidedDice() % 11 == 0;
  }

  private void setAtCorner(Guard guard) {

    switch (RandomGenerator.getRandomValue(4)) {
      case 0 -> guard.setLocation(new Position(0, 0));
      case 1 -> guard.setLocation(new Position(0, Constants.GAMEBOARD_MAX_COL - 1));
      case 2 -> guard.setLocation(new Position(Constants.GAMEBOARD_MAX_ROW - 1, 0));
      case 3 -> guard.setLocation(
          new Position(Constants.GAMEBOARD_MAX_ROW - 1, Constants.GAMEBOARD_MAX_COL - 1));
    }
  }

  private void setGuardToSleep(int turn, Guard guard) {

    guard.setSleep(true);
    guard.setLocation(new Position(-1, -1));
    guard.setTurnToBeAsleep((short) turn);
  }

  private boolean isNextPositionNotInBordersOrNotFree(Position position, Long gameId) {
    return !Position.isPositionInBorders(position) || isPositionNotFree(position, gameId);
  }

  private boolean isPositionNotFree(Position position, Long gameId) {
    return buildingService.isPositionBuilding(position, gameId)
        || heroService.isPositionHero(position, gameId);
  }
}
