package com.github.teambuilding.hero.service;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.game.Game;
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

  private HeroRepository heroRepository;

  private BuildingService buildingService;
  private BombService bombService;

  public HeroService(HeroRepository heroRepository, BuildingService buildingService) {
    this.heroRepository = heroRepository;
    this.buildingService = buildingService;
  }

  public void setBombService(BombService bombService) {
    this.bombService = bombService;
  }

  public void generateHeroes(Game game) {

    List<Hero> heroes = new ArrayList<>();

    heroes.add(new TankHero());
    heroes.add(new SniperHero());
    heroes.add(new SpyHero());
    heroes.add(new SaboteurHero());

    for (Hero hero : heroes) {
      hero.setGame(game);
    }

    heroRepository.save(heroes);

    game.setHeroes(heroes);
  }

  public String getSign(Long gameId, Position position) {

    Hero hero = heroRepository.findByGameIdAndPosition(gameId, position);
    return (hero != null) ? hero.getSign() : null;
  }

  public void makeAction(Long gameId, char command, char heroPick, short turn) {

    List<Hero> heroes = heroRepository.findByGameId(gameId);

    if (Constants.isCharMovementAction(command)) {
      MoveHeroes.moveHeroes(heroes, command, buildingService);
    }

    if (Constants.isCharSpecialAbilityAction(command)) {
      setExplode(gameId, turn);
    }

    if (Constants.isCharHeroesSwapAction(command)) {
      SwapHeroOrder.swapHero(heroPick, heroes);
    }

    heroRepository.save(heroes);
  }

  public void kill(Long gameId) {
    KillHero.kill(gameId, heroRepository);
  }

  public void killAtPosition(Long gameId, Position position) {
    KillHero.killAtPosition(gameId, position, heroRepository);
  }

  public boolean isSaboteurKilled(Long gameId) {
    return heroRepository.findByGameIdAndSign(gameId, Constants.SABOTEUR_HERO) == null;
  }

  public boolean isHeroAroundPosition(Long gameId, Position position) {

    for (int row = position.getRow() - 1; row <= position.getRow() + 1; row++) {
      for (int col = position.getCol() - 1; col <= position.getCol() + 1; col++) {
        if (isPositionHero(gameId, new Position(row, col))) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean isPositionHero(Long gameId, Position position) {
    return heroRepository.findByGameIdAndPosition(gameId, position) != null;
  }

  private void setExplode(Long gameId, short turn) {

    SaboteurHero saboteur =
        (SaboteurHero) heroRepository.findByGameIdAndSign(gameId, Constants.SABOTEUR_HERO);

    if (saboteur != null && saboteur.getOrderPosition() == 1) {
      bombService.addBomb(saboteur.getGame(), turn, saboteur.getLocation());
      return;
    }

    throw new IllegalArgumentException("Saboteur isn't first to place bomb!");
  }
}
