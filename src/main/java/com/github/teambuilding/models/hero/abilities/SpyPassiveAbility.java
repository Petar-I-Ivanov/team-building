package com.github.teambuilding.models.hero.abilities;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public interface SpyPassiveAbility {

  default boolean isMovementOverTheWall(Position fromPosition, Position toPosition) {

    return ((fromPosition.getRow() == 0 && toPosition.getRow() == -1)
        || (fromPosition.getCol() == 0 && toPosition.getCol() == -1)
        || (fromPosition.getRow() == Constants.GAMEBOARD_MAX_ROW - 1
            && toPosition.getRow() == Constants.GAMEBOARD_MAX_ROW)
        || (fromPosition.getCol() == Constants.GAMEBOARD_MAX_COL - 1
            && toPosition.getCol() == Constants.GAMEBOARD_MAX_COL));
  }
}
