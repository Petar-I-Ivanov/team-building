package com.github.teambuilding.utility;

import com.github.teambuilding.models.Guard;
import javax.validation.UnexpectedTypeException;

public class GuardUtility {

  private GuardUtility() {}

  public static void setAtRandomCorner(Guard guard) {

    switch (RandomGenerator.getRandomValue(4)) {

      case 0 -> guard.setLocation(new Position(0, 0));
      case 1 -> guard.setLocation(new Position(0, Constants.GAMEBOARD_MAX_COL - 1));
      case 2 -> guard.setLocation(new Position(Constants.GAMEBOARD_MAX_ROW - 1, 0));
      case 3 -> guard.setLocation(
          new Position(Constants.GAMEBOARD_MAX_ROW - 1, Constants.GAMEBOARD_MAX_COL - 1));
      default -> throw new UnexpectedTypeException(
          "Shouldn't be more than 4 options for random corner.");
    }

  }

  public static boolean isShootSuccessful() {
    return RandomGenerator.twentyFourSidedDice() % 11 == 0;
  }

  public static void setGuardToSleep(Guard guard, short turn) {

    guard.setSleep(true);
    guard.setLocation(new Position(-1, -1));
    guard.setTurnSetToSleep(turn);
  }

  public static boolean isTurnToShowGuard(Guard guard, short turn) {
    return turn - guard.getTurnSetToSleep() >= 4;
  }
}
