package com.github.teambuilding.models.hero.abilities;

import com.github.teambuilding.utility.RandomGenerator;

public interface SniperPassiveAbility {

  default boolean isShootChanceSuccessful() {
    return RandomGenerator.getRandomValue(2) == 1;
  }
}
