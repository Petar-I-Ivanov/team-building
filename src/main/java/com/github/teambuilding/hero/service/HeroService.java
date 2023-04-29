package com.github.teambuilding.hero.service;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SaboteurHero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.SpyHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.hero.service.helpers.KillHero;
import com.github.teambuilding.hero.service.helpers.MoveHeroes;
import com.github.teambuilding.hero.service.helpers.SwapHeroOrder;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeroService {

  private BuildingService buildingService;
  private HeroRepository heroRepository;
  private BombService bombService;

  public HeroService(BuildingService buildingService, HeroRepository heroRepository) {
    this.buildingService = buildingService;
    this.heroRepository = heroRepository;
  }

  public void setBombService(BombService bombService) {
    this.bombService = bombService;
  }

  public void generateHeroes(Long gameId) {

    List<Hero> heroes = new ArrayList<>();

    heroes.add(new TankHero());
    heroes.add(new SniperHero());
    heroes.add(new SpyHero());
    heroes.add(new SaboteurHero());

    for (Hero hero : heroes) {
      hero.setGameId(gameId);
    }

    heroRepository.save(heroes);
  }

  public String getSign(Position position, Long gameId) {

    Hero hero = heroRepository.findByGameIdAndPosition(gameId, position);
    return (hero != null) ? hero.getSign() : null;
  }

  public void makeAction(char command, char heroPick, int turn, Long gameId) {

    List<Hero> heroes = heroRepository.findByGameId(gameId);

    if (Constants.isCharMovementAction(command)) {
      MoveHeroes.moveHeroes(heroes, command, buildingService);
    }

    if (Constants.isCharSpecialAbilityAction(command)) {
      setExplode(turn, gameId);
    }

    if (Constants.isCharHeroesSwapAction(command)) {
      SwapHeroOrder.swapHero(heroPick, heroes);
    }

    heroRepository.save(heroes);
  }

  public boolean isHeroAroundPosition(Position position, Long gameId) {

    for (int row = position.getRow() - 1; row <= position.getRow() + 1; row++) {
      for (int col = position.getCol() - 1; col <= position.getCol() + 1; col++) {
        if (getHeroByPosition(new Position(row, col), gameId) != null) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean isPositionHero(Position position, Long gameId) {

    Hero hero = heroRepository.findByGameIdAndPosition(gameId, position);
    return hero != null;
  }

  public void kill(Long gameId) {
    List<Hero> heroes = KillHero.kill(heroRepository.findByGameId(gameId), heroRepository);
    heroRepository.save(heroes);
  }

  public void killAtPosition(Position position, Long gameId) {
    List<Hero> heroes =
        KillHero.killAtPosition(heroRepository.findByGameId(gameId), position, heroRepository);
    heroRepository.save(heroes);
  }

  public boolean isSaboteurKilled(Long gameId) {
    return getSaboteur(gameId) == null;
  }

  private Hero getHeroByPosition(Position position, Long gameId) {

    List<Hero> heroes = heroRepository.findByGameId(gameId);

    for (Hero hero : heroes) {
      if (Position.arePositionsEqual(hero.getLocation(), position)) {
        return hero;
      }
    }

    return null;
  }

  private void setExplode(int turn, Long gameId) {

    SaboteurHero saboteur = getSaboteur(gameId);

    if (saboteur != null && saboteur.isAlive() && saboteur.getOrderPosition() == 1) {
      throw new IllegalArgumentException("Saboteur isn't first to place bombs!");
    }

    bombService.setBomb(turn, saboteur.getLocation(), gameId);
  }

  private SaboteurHero getSaboteur(Long gameId) {

    List<Hero> heroes = heroRepository.findByGameId(gameId);

    for (Hero hero : heroes) {
      if (hero.getSign().equals(Constants.SABOTEUR_HERO)) {
        return (SaboteurHero) hero;
      }
    }

    return null;
  }
}
