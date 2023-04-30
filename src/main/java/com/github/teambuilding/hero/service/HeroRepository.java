package com.github.teambuilding.hero.service;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeroRepository {

  private static Long counter = 1L;
  private List<Hero> heroes;

  public HeroRepository() {
    this.heroes = new ArrayList<>();
  }

  public List<Hero> findByGameId(Long gameId) {

    List<Hero> heroesToReturn = new ArrayList<>();

    for (Hero hero : heroes) {
      if (hero.getGameId().equals(gameId)) {
        heroesToReturn.add(hero);
      }
    }

    heroesToReturn.sort(Comparator.comparing(Hero::getOrderPosition));
    return heroesToReturn;
  }

  public Hero findByGameIdAndPosition(Long gameId, Position position) {

    for (Hero hero : heroes) {
      if (hero.getGameId().equals(gameId)
          && Position.arePositionsEqual(hero.getLocation(), position)) {
        return hero;
      }
    }

    return null;
  }
  
  public Hero findByGameIdAndSign(Long gameId, String sign) {
	  
	  for (Hero hero : heroes) {
		  if (hero.getGameId().equals(gameId) && hero.getSign().equals(sign)) {
			  return hero;
		  }
	  }
	  
	  return null;
  }

  public void save(Hero hero) {

    if (hero.getId() != null) {
      deleteById(hero.getId());
      heroes.add(hero);
      return;
    }

    hero.setId(counter++);
    heroes.add(hero);
  }

  public void save(List<Hero> heroesToAdd) {

    for (Hero hero : heroesToAdd) {
      save(hero);
    }
  }

  public void deleteById(Long heroId) {

    for (Hero hero : heroes) {
      if (hero.getId().equals(heroId)) {
        heroes.remove(hero);
        return;
      }
    }
  }
}
