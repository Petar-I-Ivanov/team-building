package com.github.teambuilding.bomb;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import lombok.Data;

@Data
public class Bomb {

  private String sign;

  private int turnToPlace;
  private Position location;

  public Bomb(int turnToPlace, Position location) {

    this.sign = Constants.BOMB;
    this.turnToPlace = turnToPlace;
    this.location = location;
  }

  public boolean isBombExploding(int turn) {
    return turn - turnToPlace == 5;
  }

  public boolean isPositionBomb(Position position) {
    return Position.arePositionsEqual(this.location, position);
  }
}
