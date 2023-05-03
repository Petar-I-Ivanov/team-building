package com.github.teambuilding.utility;

public class GameObject {

  protected byte rowLocation;
  protected byte colLocation;

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position location) {
    this.rowLocation = (byte) location.getRow();
    this.colLocation = (byte) location.getCol();
  }
}
