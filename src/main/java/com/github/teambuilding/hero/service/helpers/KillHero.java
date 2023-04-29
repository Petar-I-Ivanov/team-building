package com.github.teambuilding.hero.service.helpers;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.hero.service.HeroRepository;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import javax.validation.UnexpectedTypeException;

public class KillHero {

  private KillHero() {}

  public static List<Hero> kill(List<Hero> heroes, HeroRepository heroRepository) {

    TankHero tank = (TankHero) getHeroBySign(heroes, Constants.TANK_HERO);
    SniperHero sniper = (SniperHero) getHeroBySign(heroes, Constants.SNIPER_HERO);

    if (sniper != null && sniper.passiveAbility()) {
      return heroes;
    }

    if (tank != null) {
      heroRepository.deleteById(tank.getId());
      return removeHeroFromOrder(heroes, tank);
    }

    heroRepository.deleteById(heroes.get(0).getId());
    return removeHeroFromOrder(heroes, heroes.get(0));
  }

  public static List<Hero> killAtPosition(List<Hero> heroes, Position position,
      HeroRepository heroRepository) {

    for (Hero hero : heroes) {
      if (Position.arePositionsEqual(hero.getLocation(), position)) {
        return removeHeroFromOrder(heroes, hero);
      }
    }

    throw new UnexpectedTypeException("Shouldn't go so far.");
  }

  private static Hero getHeroBySign(List<Hero> heroes, String heroSign) {

    for (Hero hero : heroes) {
      if (heroSign.equals(hero.getSign())) {
        return hero;
      }
    }

    return null;
  }

  private static List<Hero> removeHeroFromOrder(List<Hero> heroes, Hero heroToRemove) {

    List<Hero> newOrder = new ArrayList<>();

    for (Hero hero : heroes) {
      if (hero != heroToRemove) {
        newOrder.add(hero);
      }
    }

    return newOrder;
  }
}
