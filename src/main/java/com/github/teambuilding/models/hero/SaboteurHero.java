package com.github.teambuilding.models.hero;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class SaboteurHero extends Hero {

  private static final byte STARTING_ROW = 14;
  private static final byte STARTING_COL = 14;
  private static final byte STARTING_ORDER_POSITION = 4;

  public SaboteurHero() {
    super(Constants.SABOTEUR_HERO, new Position(STARTING_ROW, STARTING_COL),
        STARTING_ORDER_POSITION);
  }
}
