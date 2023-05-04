package com.github.teambuilding.services.hero;

import com.github.teambuilding.models.hero.Hero;
import com.github.teambuilding.repositories.HeroRepository;
import com.github.teambuilding.utility.Position;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeroSwapOrder {

  private HeroRepository heroRepository;

  public HeroSwapOrder(HeroRepository heroRepository) {
    this.heroRepository = heroRepository;
  }

  public void swapHero(Long gameId, char heroSign) {

    Hero hero = heroRepository.findByGameIdAndSign(gameId, String.valueOf(heroSign));

    if (hero == null) {
      throw new IllegalArgumentException("Invalid hero sign at swap.");
    }

    Hero firstHero = heroRepository.findByGameIdAndOrderPositionOne(gameId);

    if (firstHero.equals(hero)) {
      throw new IllegalArgumentException("Hero is already first.");
    }

    swap(hero, firstHero);

    heroRepository.save(hero);
    heroRepository.save(firstHero);
  }

  private static void swap(Hero heroOne, Hero heroTwo) {

    Position tempPosition = heroOne.getLocation();
    byte tempOrderPosition = heroOne.getOrderPosition();

    heroOne.setLocation(heroTwo.getLocation());
    heroTwo.setLocation(tempPosition);

    heroOne.setOrderPosition(heroTwo.getOrderPosition());
    heroTwo.setOrderPosition(tempOrderPosition);
  }
}
