package com.github.teambuilding.hero.service.helpers;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.validation.UnexpectedTypeException;

public class KillHero {

  private KillHero() {}

  public static Hero[] kill(Hero[] heroes) {

    TankHero tank = (TankHero) getHeroBySign(heroes, Constants.TANK_HERO);
    SniperHero sniper = (SniperHero) getHeroBySign(heroes, Constants.SNIPER_HERO);

    if (sniper != null && sniper.passiveAbility()) {
      return heroes;
    }

    if (tank != null) {
      return removeHeroFromOrder(heroes, tank);
    }

    return removeHeroFromOrder(heroes, heroes[0]);
  }

  public static Hero[] killAtPosition(Hero[] heroes, Position position) {

    for (Hero hero : heroes) {
      if (Position.arePositionsEqual(hero.getLocation(), position)) {
        return removeHeroFromOrder(heroes, hero);
      }
    }

    throw new UnexpectedTypeException("Shouldn't go so far.");
  }

  private static Hero getHeroBySign(Hero[] heroes, String heroSign) {

    for (Hero hero : heroes) {
      if (heroSign.equals(hero.getSign())) {
        return hero;
      }
    }

    return null;
  }

  private static Hero[] removeHeroFromOrder(Hero[] heroes, Hero heroToRemove) {

    Hero[] newOrder = new Hero[heroes.length - 1];
    int counter = 0;

    for (Hero hero : heroes) {
      if (hero != heroToRemove) {
        newOrder[counter++] = hero;
      }
    }

    return newOrder;
  }
}
