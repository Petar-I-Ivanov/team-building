package com.github.teambuilding.models.hero;

import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.RandomGenerator;
import javax.persistence.Entity;

@Entity
public class SniperHero extends Hero {

  public SniperHero() {
    super("2", new Position(14, 12), 2);
  }

  public boolean passiveAbility() {
    return RandomGenerator.getRandomValue(2) == 1;
  }
}
