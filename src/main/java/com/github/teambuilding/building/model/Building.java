package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public abstract class Building {

  protected final int HEIGHT;
  protected final int WIDTH;

  protected Long id;
  protected String sign;
  protected Position[] locations;
  protected List<Position> explodedPositions;
  protected Long gameId;

  protected boolean isDestroyed;

  protected Building(String sign, int height, int width, Position startingPosition) {

    this.sign = sign;
    this.HEIGHT = height;
    this.WIDTH = width;
    this.explodedPositions = new ArrayList<>();

    if (canSetBuilding(startingPosition)) {
      set(startingPosition);
    }
  }

  public boolean isBuildingSet() {
    return this.locations != null;
  }

  public Position getStartingPosition() {
    return this.locations[0];
  }

  public void cleansePositions() {
    this.locations = new Position[0];
  }

  public boolean isPositionBuilding(Position position) {

    for (Position location : this.locations) {
      if (Position.arePositionsEqual(location, position) && !isPositionExploded(position)) {
        return true;
      }
    }

    return false;
  }

  public void addExplode(Position position) {

    this.explodedPositions.add(position);

    if (isExplodeFatal()) {
      this.isDestroyed = true;
    }
  }

  public abstract boolean isEntryPossible(Position position);

  protected abstract boolean isExplodeFatal();

  private void set(Position startingPosition) {

    this.locations = new Position[this.HEIGHT * this.WIDTH];
    int counter = 0;

    for (int row = 0; row < this.HEIGHT; row++) {
      for (int col = 0; col < this.WIDTH; col++) {
        this.locations[counter++] =
            new Position(startingPosition.getRow() + row, startingPosition.getCol() + col);
      }
    }

    this.isDestroyed = false;
  }

  private boolean canSetBuilding(Position position) {
    return willBuildingNotBeOnBorders(position) && willBuildingNotBeNearHearoes(position);
  }

  private boolean willBuildingNotBeOnBorders(Position position) {

    return (position.getRow() > 0
        && position.getRow() < Constants.GAMEBOARD_MAX_ROW - 1 - this.HEIGHT)
        && (position.getCol() > 0
            && position.getCol() < Constants.GAMEBOARD_MAX_COL - 1 - this.WIDTH);
  }

  private boolean willBuildingNotBeNearHearoes(Position position) {

    return !((position.getRow() + this.HEIGHT > 8) && (position.getCol() + this.WIDTH > 4));
  }

  private boolean isPositionExploded(Position position) {

    for (Position location : this.explodedPositions) {
      if (Position.arePositionsEqual(location, position)) {
        return true;
      }
    }

    return false;
  }
}
