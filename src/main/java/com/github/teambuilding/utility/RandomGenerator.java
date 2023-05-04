package com.github.teambuilding.utility;

import java.util.Random;

public class RandomGenerator {

  private RandomGenerator() {}

  public static char getRandomMoveDirection() {

    int randomWay = new Random().nextInt(4);

    return switch (randomWay) {
      case 0 -> Constants.FORWARD_MOVE;
      case 1 -> Constants.BACK_MOVE;
      case 2 -> Constants.LEFT_MOVE;
      case 3 -> Constants.RIGHT_MOVE;
      default -> Constants.NULL;
    };
  }

  public static Position getRandomCornerPosition() {

    return switch (getRandomValue(4)) {
      case 0 -> new Position(0, 0);
      case 1 -> new Position(0, Constants.GAMEBOARD_MAX_COL - 1);
      case 2 -> new Position(Constants.GAMEBOARD_MAX_ROW - 1, 0);
      case 3 -> new Position(Constants.GAMEBOARD_MAX_ROW - 1, Constants.GAMEBOARD_MAX_COL - 1);
      default -> throw new IllegalArgumentException("Invalid random value for random corner.");
    };
  }

  public static int twentyFourSidedDice() {
    return new Random().nextInt(24) + 1;
  }

  public static int getRandomValue(int border) {
    return new Random().nextInt(border);
  }
}
