package com.github.teambuilding.models.hero;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class TankHero extends Hero {

  private static final byte STARTING_ROW = 14;
  private static final byte STARTING_COL = 11;
  private static final byte STARTING_ORDER_POSITION = 1;

  public TankHero() {
    super(Constants.TANK_HERO, new Position(STARTING_ROW, STARTING_COL), STARTING_ORDER_POSITION);
  }
}
