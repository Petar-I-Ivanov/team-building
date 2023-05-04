package com.github.teambuilding.services.guard;

import com.github.teambuilding.models.Guard;
import com.github.teambuilding.repositories.GuardRepository;
import com.github.teambuilding.services.building.BuildingServiceImpl;
import com.github.teambuilding.services.hero.HeroServiceImpl;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuardMovementService {

  private GuardRepository guardRepository;

  private HeroServiceImpl heroService;
  private BuildingServiceImpl buildingService;

  public GuardMovementService(GuardRepository guardRepository, HeroServiceImpl heroService,
      BuildingServiceImpl buildingService) {

    this.guardRepository = guardRepository;
    this.heroService = heroService;
    this.buildingService = buildingService;
  }

  public void moveGuard(Long gameId) {

    Guard guard = guardRepository.findByGameId(gameId);
    guard.setLocation(getRandomAvailableMovePosition(gameId, guard.getLocation()));
    guardRepository.save(guard);
  }

  public void awakeGuard(Long gameId) {

    Guard guard = guardRepository.findByGameId(gameId);

    guard.setLocation(getRandomAvailableShownPosition(gameId));
    guard.setSleep(false);
    guard.setTurnSetToSleep((short) 0);

    guardRepository.save(guard);
  }

  public void sleepGuard(Long gameId, short turn) {

    Guard guard = guardRepository.findByGameId(gameId);

    guard.setLocation(new Position(-1, -1));
    guard.setSleep(true);
    guard.setTurnSetToSleep(turn);

    guardRepository.save(guard);
  }

  public Position getRandomAvailableShownPosition(Long gameId) {

    Position randomPosition = new Position();

    while (isPositionOccupied(gameId, randomPosition)) {
      randomPosition = new Position();
    }

    return randomPosition;
  }

  public void setGuardAtRandomCorner(Long gameId) {

    Guard guard = guardRepository.findByGameId(gameId);
    guard.setLocation(RandomGenerator.getRandomCornerPosition());
    guardRepository.save(guard);
  }

  private Position getRandomAvailableMovePosition(Long gameId, Position guardPositon) {

    Position nextPosition = Position.getPositionBasedOnDirection(guardPositon,
        RandomGenerator.getRandomMoveDirection());

    while (isPositionUnavailableToMove(gameId, nextPosition)) {

      nextPosition = Position.getPositionBasedOnDirection(guardPositon,
          RandomGenerator.getRandomMoveDirection());
    }

    return nextPosition;
  }

  private boolean isPositionUnavailableToMove(Long gameId, Position position) {
    return !Position.isPositionInBorders(position) || isPositionOccupied(gameId, position);
  }

  private boolean isPositionOccupied(Long gameId, Position position) {

    return heroService.isHeroAtPosition(gameId, position)
        || buildingService.isPositionBuilding(position, gameId);
  }
}
