package com.github.teambuilding.services.guard;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.Guard;
import com.github.teambuilding.repositories.GuardRepository;
import com.github.teambuilding.services.hero.HeroService;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuardServiceImpl implements GuardService {

  private GuardRepository guardRepository;
  private GuardMovementService guardMovementService;

  private HeroService heroService;

  public GuardServiceImpl(GuardRepository guardRepository,
      GuardMovementService guardMovementService, HeroService heroService) {

    this.guardRepository = guardRepository;
    this.guardMovementService = guardMovementService;

    this.heroService = heroService;
  }

  @Override
  public void generateGuard(Game game) {

    Guard guard = new Guard();

    guard.setGame(game);
    guard.setLocation(guardMovementService.getRandomAvailableShownPosition(game.getId()));

    guardRepository.save(guard);

    game.setGuard(guard);
  }

  @Override
  public Guard getAllForGameId(Long gameId) {
    return guardRepository.findByGameId(gameId);
  }

  @Override
  public void move(Long gameId, short turn) {

    Guard guard = guardRepository.findByGameId(gameId);

    if (guard.isSleep()) {

      if (isTurnToAwakeGuard(guard, turn)) {
        guardMovementService.awakeGuard(gameId);
      }

      return;
    }

    guardMovementService.moveGuard(gameId);

    if (isGuardSeeingHeroes(gameId)) {
      guardSeeingHeroesAction(guard, gameId, turn);
    }
  }

  @Override
  public void kill(Long gameId, short turn) {
    guardMovementService.sleepGuard(gameId, turn);
  }

  @Override
  public boolean isGuardAtPosition(Long gameId, Position position) {
    return guardRepository.findByGameIdAndPosition(gameId, position) != null;
  }

  @Override
  public void deleteAllWhereGameId(Long gameId) {
    guardRepository.deleteWhereGameId(gameId);
  }

  private boolean isGuardSeeingHeroes(Long gameId) {

    Guard guard = guardRepository.findByGameId(gameId);

    int guardRow = guard.getRowLocation();
    int guardCol = guard.getColLocation();

    for (int row = guardRow - 1; row <= guardRow + 1; row++) {
      for (int col = guardCol - 1; col <= guardCol + 1; col++) {

        if (heroService.isHeroAtPosition(gameId, new Position(row, col))) {
          return true;
        }
      }
    }

    return false;
  }

  private void guardSeeingHeroesAction(Guard guard, Long gameId, short turn) {

    if (isShootSuccessful()) {

      heroService.kill(gameId);
      guardMovementService.sleepGuard(gameId, turn);
      return;
    }

    guardMovementService.setGuardAtRandomCorner(gameId);
  }

  private static boolean isTurnToAwakeGuard(Guard guard, short turn) {
    return turn - guard.getTurnSetToSleep() >= 4;
  }

  private static boolean isShootSuccessful() {
    return RandomGenerator.twentyFourSidedDice() % 11 == 0;
  }
}
