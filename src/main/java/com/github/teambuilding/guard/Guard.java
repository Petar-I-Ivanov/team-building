package com.github.teambuilding.guard;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import lombok.Data;

@Data
public class Guard {

  private Long id;
  private String sign;
  private byte rowLocation;
  private byte colLocation;
  private boolean isSleep;
  private short turnToBeAsleep;
  private Long gameId;

  public Guard() {
    this.sign = Constants.GUARD;
    this.rowLocation = 0;
    this.colLocation = 0;
    this.isSleep = false;
    this.turnToBeAsleep = 0;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position position) {
    this.rowLocation = (byte) position.getRow();
    this.colLocation = (byte) position.getCol();
  }
}
