package com.github.teambuilding.bomb;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import lombok.Data;

@Data
public class Bomb {

  private Long id;

  private String sign;

  private short turnPlaced;

  private byte rowLocation;
  private byte colLocation;

  private Long gameId;

  public Bomb() {

    this.sign = Constants.BOMB;

    this.turnPlaced = 0;
    this.rowLocation = 0;
    this.colLocation = 0;
    this.gameId = 0L;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position position) {
    this.rowLocation = (byte) position.getRow();
    this.colLocation = (byte) position.getCol();
  }
}
