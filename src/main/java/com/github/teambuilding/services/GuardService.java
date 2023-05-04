package com.github.teambuilding.services;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.Guard;
import com.github.teambuilding.repositories.GuardRepository;
import com.github.teambuilding.services.hero.HeroService;
import com.github.teambuilding.utility.GuardUtility;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuardService {

  private GuardRepository guardRepository;

  private BuildingService buildingService;
  private HeroService heroService;

  public GuardService(GuardRepository guardRepository, BuildingService buildingService,
      HeroService heroService) {

    this.guardRepository = guardRepository;

    this.buildingService = buildingService;
    this.heroService = heroService;
  }

  public void generateGuard(Game game) {

    Guard guard = new Guard();

    guard.setGame(game);
    guard.setLocation(getRandomShownLocation(game.getId()));

    guardRepository.save(guard);

    game.setGuard(guard);
  }

  public Guard getAllForGameId(Long gameId) {
    return guardRepository.findByGameId(gameId);
  }

  public void move(Long gameId, short turn) {

    Guard guard = guardRepository.findByGameId(gameId);

    if (guard.isSleep()) {

      if (GuardUtility.isTurnToShowGuard(guard, turn)) {

        guard.setLocation(getRandomShownLocation(gameId));
        guard.setSleep(false);
        guard.setTurnSetToSleep((short) 0);
        guardRepository.save(guard);
      }

      return;
    }

    guard.setLocation(getNewAvailablePositionToMove(gameId, guard));

    if (isGuardSeeingHeroes(guard, gameId)) {
      guardSeeingHeroesAction(guard, gameId, turn);
    }

    guardRepository.save(guard);
  }

  public void kill(Long gameId, short turn) {

    Guard guard = guardRepository.findByGameId(gameId);
    GuardUtility.setGuardToSleep(guard, turn);
    guardRepository.save(guard);
  }

  public boolean isGuardAtPosition(Long gameId, Position position) {
    return guardRepository.findByGameIdAndPosition(gameId, position) != null;
  }

  private Position getRandomShownLocation(Long gameId) {

    Position location = new Position();

    while (isNextPositionNotInBordersOrNotFree(gameId, location)) {
      location = new Position();
    }

    return location;
  }

  private Position getNewAvailablePositionToMove(Long gameId, Guard guard) {

    Position newLocation = Position.getPositionBasedOnDirection(guard.getLocation(),
        RandomGenerator.getRandomMoveDirection());

    while (isNextPositionNotInBordersOrNotFree(gameId, newLocation)) {

      newLocation = Position.getPositionBasedOnDirection(guard.getLocation(),
          RandomGenerator.getRandomMoveDirection());
    }

    return newLocation;
  }

  private boolean isNextPositionNotInBordersOrNotFree(Long gameId, Position position) {
    return !Position.isPositionInBorders(position) || isPositionNotFree(gameId, position);
  }

  private boolean isPositionNotFree(Long gameId, Position position) {
    return buildingService.isPositionBuilding(position, gameId)
        || heroService.isPositionHero(gameId, position);
  }

  private boolean isGuardSeeingHeroes(Guard guard, Long gameId) {
    return heroService.isHeroAroundPosition(gameId, guard.getLocation());
  }

  private void guardSeeingHeroesAction(Guard guard, Long gameId, short turn) {

    if (GuardUtility.isShootSuccessful()) {

      heroService.kill(gameId);
      GuardUtility.setGuardToSleep(guard, turn);
      return;
    }

    GuardUtility.setAtRandomCorner(guard);
  }
}
