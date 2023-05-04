package com.github.teambuilding.models.hero;

import com.github.teambuilding.models.hero.abilities.SniperPassiveAbility;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class SniperHero extends Hero implements SniperPassiveAbility {

  private static final byte STARTING_ROW = 14;
  private static final byte STARTING_COL = 12;
  private static final byte STARTING_ORDER_POSITION = 2;

  public SniperHero() {
    super(Constants.SNIPER_HERO, new Position(STARTING_ROW, STARTING_COL), STARTING_ORDER_POSITION);
  }
}
