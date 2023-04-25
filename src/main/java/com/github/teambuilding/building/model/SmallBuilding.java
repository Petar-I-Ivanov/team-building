package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class SmallBuilding extends Building {

  public SmallBuilding(Position startingPosition) {
    super(Constants.SMALL_BUILDING, Constants.SMALL_B_MAX_ROW, Constants.SMALL_B_MAX_COL,
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
    return this.explodedPositions.size() >= 1;
  }
}
