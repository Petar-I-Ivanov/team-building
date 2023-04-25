package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class MediumBuilding extends Building {

  public MediumBuilding(Position startingPosition) {
    super(Constants.MEDIUM_BUILDING, Constants.MEDIUM_B_MAX_ROW, Constants.MEDIUM_B_MAX_COL,
        startingPosition);
  }

  @Override
  public boolean isEntryPossible(Position position) {

    for (int i = 0; i < this.locations.length; i++) {

      if (i == 1 || i == 4) {
        continue;
      }

      if (Position.arePositionsEqual(locations[i], position)) {
        return true;
      }
    }

    return false;
  }

  @Override
  protected boolean isExplodeFatal() {
    return isFirstDiagonalDestroyed() || isSecondDiagonalDestroyed();
  }

  private boolean isFirstDiagonalDestroyed() {

    int corrnersCount = 0;

    Position firstCorner = this.locations[0];
    Position secondCorner = this.locations[locations.length - 1];

    for (Position position : this.explodedPositions) {

      if (Position.arePositionsEqual(firstCorner, position)
          || Position.arePositionsEqual(secondCorner, position)) {
        corrnersCount++;
      }
    }

    return corrnersCount == 2;
  }

  private boolean isSecondDiagonalDestroyed() {

    int corrnersCount = 0;

    Position firstCorner = this.locations[2];
    Position secondCorner = this.locations[3];

    for (Position position : this.explodedPositions) {

      if (Position.arePositionsEqual(firstCorner, position)
          || Position.arePositionsEqual(secondCorner, position)) {
        corrnersCount++;
      }
    }

    return corrnersCount == 2;
  }
}
