package com.github.teambuilding.services.hero;

import com.github.teambuilding.models.hero.Hero;
import com.github.teambuilding.models.hero.SniperHero;
import com.github.teambuilding.models.hero.TankHero;
import com.github.teambuilding.repositories.HeroRepository;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeroKillService {

  private HeroRepository heroRepository;

  public HeroKillService(HeroRepository heroRepository) {
    this.heroRepository = heroRepository;
  }

  public void kill(Long gameId) {

    SniperHero sniper =
        (SniperHero) heroRepository.findByGameIdAndSign(gameId, Constants.SNIPER_HERO);

    if (sniper != null && sniper.isShootChanceSuccessful()) {
      return;
    }

    List<Hero> heroes = heroRepository.findByGameId(gameId);
    TankHero tank = (TankHero) heroRepository.findByGameIdAndSign(gameId, Constants.TANK_HERO);

    Hero heroToRemove = (tank != null) ? tank : heroes.get(0);

    removeHeroFromOrder(heroes, heroToRemove);
    heroRepository.delete(heroToRemove);
    heroRepository.save(heroes);
  }

  public void killAtPosition(Long gameId, Position position) {

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
