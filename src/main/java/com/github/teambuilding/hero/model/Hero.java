package com.github.teambuilding.hero.model;

import com.github.teambuilding.utility.Position;
import lombok.Data;

@Data
public abstract class Hero {

  protected Long id;
  protected String sign;
  protected byte rowLocation;
  protected byte colLocation;
  protected boolean isAlive;
  protected byte orderPosition;
  protected Long gameId;

  protected Hero(String sign, Position location, int orderPosition) {

    this.sign = sign;
    this.rowLocation = (byte) location.getRow();
    this.colLocation = (byte) location.getCol();
    this.isAlive = true;
    this.orderPosition = (byte) orderPosition;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position position) {
    this.rowLocation = (byte) position.getRow();
    this.colLocation = (byte) position.getCol();
  }
}
