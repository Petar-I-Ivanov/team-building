package com.github.teambuilding.models.hero;

import com.github.teambuilding.models.hero.abilities.SpyPassiveAbility;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class SpyHero extends Hero implements SpyPassiveAbility {

  private static final byte STARTING_ROW = 14;
  private static final byte STARTING_COL = 13;
  private static final byte STARTING_ORDER_POSITION = 3;

  public SpyHero() {
    super(Constants.SPY_HERO, new Position(STARTING_ROW, STARTING_COL), STARTING_ORDER_POSITION);
  }
}
