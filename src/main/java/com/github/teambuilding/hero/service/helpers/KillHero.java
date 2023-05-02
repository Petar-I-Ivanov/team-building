package com.github.teambuilding.hero.service.helpers;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.hero.service.HeroRepository;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.List;

public class KillHero {

  private KillHero() {}

  public static void kill(Long gameId, HeroRepository heroRepository) {

    SniperHero sniper =
        (SniperHero) heroRepository.findByGameIdAndSign(gameId, Constants.SNIPER_HERO);

    if (sniper != null && sniper.passiveAbility()) {
      return;
    }

    List<Hero> heroes = heroRepository.findByGameId(gameId);
    TankHero tank = (TankHero) heroRepository.findByGameIdAndSign(gameId, Constants.TANK_HERO);

    Hero heroToRemove = (tank != null) ? tank : heroes.get(0);
    removeHeroFromOrder(heroes, heroToRemove);
    heroRepository.delete(heroToRemove);
    heroRepository.save(heroes);
  }

  public static void killAtPosition(Long gameId, Position position, HeroRepository heroRepository) {

    List<Hero> heroes = heroRepository.findByGameId(gameId);

    for (Hero hero : heroes) {
      if (Position.arePositionsEqual(hero.getLocation(), position)) {
        removeHeroFromOrder(heroes, hero);
        heroRepository.delete(hero);
        heroRepository.save(heroes);
        break;
      }
    }
  }

  private static void removeHeroFromOrder(List<Hero> heroes, Hero heroToRemove) {

    byte counter = 1;

    for (Hero hero : heroes) {
      if (hero != heroToRemove) {
        hero.setOrderPosition(counter++);
      }
    }

    heroes.remove(heroToRemove);
  }
}
