package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class BigBuilding extends Building {

  public BigBuilding(Position startingPosition) {
    super(Constants.BIG_BUILDING, Constants.BIG_B_MAX_ROW, Constants.BIG_B_MAX_COL,
        startingPosition);
  }

  @Override
  public boolean isEntryPossible(Position position) {

    for (Position location : this.locations) {
      if (Position.arePositionsEqual(location, position)) {
        return true;
      }
    }

    return false;
  }

  @Override
  protected boolean isExplodeFatal() {

    int cornersCount = 0;

    Position[] positionsToCheck = new Position[] {

        this.locations[0], this.locations[2],

        this.locations[4],

        this.locations[6], this.locations[8]};

    for (Position position : this.explodedPositions) {
      for (Position positionToCheck : positionsToCheck) {
        if (Position.arePositionsEqual(position, positionToCheck)) {
          cornersCount++;
        }
      }
    }

    return cornersCount == 5;
  }
}
