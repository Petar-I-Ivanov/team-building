package com.github.teambuilding.hero.service.helpers;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.utility.Position;
import java.util.List;

public class SwapHeroOrder {

  private SwapHeroOrder() {}

  public static void swapHero(char heroSign, List<Hero> heroes) {

    Hero hero = getHeroBySign(heroSign, heroes);

    if (hero == null) {
      throw new IllegalArgumentException("Invalid hero sign at swap.");
    }

    Hero firstHero = heroes.get(0);

    if (firstHero == hero) {
      throw new IllegalArgumentException("Hero is already first.");
    }

    swap(hero, firstHero, heroes);

  }

  private static Hero getHeroBySign(char heroSign, List<Hero> heroes) {

    String stringHeroSign = String.valueOf(heroSign);

    for (Hero hero : heroes) {
      if (stringHeroSign.equals(hero.getSign())) {
        return hero;
      }
    }

    return null;
  }

  private static void swap(Hero heroOne, Hero heroTwo, List<Hero> heroes) {

    Position tempPosition = heroOne.getLocation();
    byte tempOrderPosition = heroOne.getOrderPosition();

    heroOne.setLocation(heroTwo.getLocation());
    heroTwo.setLocation(tempPosition);

    heroOne.setOrderPosition(heroTwo.getOrderPosition());
    heroTwo.setOrderPosition(tempOrderPosition);
  }
}
