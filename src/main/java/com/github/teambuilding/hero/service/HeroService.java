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

public class HeroService {

  private Hero[] order;

  private BuildingService buildingService;
  private BombService bombService;

  public HeroService(BuildingService buildingService) {

    this.order = new Hero[4];

    this.order[0] = new TankHero();
    this.order[1] = new SniperHero();
    this.order[2] = new SpyHero();
    this.order[3] = new SaboteurHero();

    this.buildingService = buildingService;
  }

  public void setBombService(BombService bombService) {
    this.bombService = bombService;
  }

  public String getSign(Position position) {

    Hero hero = getHeroByPosition(position);
    return (hero != null) ? hero.getSign() : null;
  }

  public void makeAction(char command, char heroPick, int turn) {

    if (Constants.isCharMovementAction(command)) {
      MoveHeroes.moveHeroes(order, command, buildingService);
    }

    if (Constants.isCharSpecialAbilityAction(command)) {
      setExplode(turn);
    }

    if (Constants.isCharHeroesSwapAction(command)) {
      SwapHeroOrder.swapHero(heroPick, order);
    }
  }

  public boolean isHeroAroundPosition(Position position) {

    for (int row = position.getRow() - 1; row <= position.getRow() + 1; row++) {
      for (int col = position.getCol() - 1; col <= position.getCol() + 1; col++) {
        if (getHeroByPosition(new Position(row, col)) != null) {
          return true;
        }
      }
    }

    return false;
  }

  public boolean isPositionHero(Position position) {

    Hero hero = getHeroByPosition(position);
    return hero != null;
  }

  public void kill() {
    this.order = KillHero.kill(order);
  }

  public void killAtPosition(Position position) {
    this.order = KillHero.killAtPosition(order, position);
  }

  public boolean isSaboteurKilled() {
    return getSaboteur() == null;
  }

  private Hero getHeroByPosition(Position position) {

    for (Hero hero : order) {
      if (Position.arePositionsEqual(hero.getLocation(), position)) {
        return hero;
      }
    }

    return null;
  }

  private void setExplode(int turn) {

    SaboteurHero saboteur = getSaboteur();

    if (saboteur != null && saboteur.isAlive() && !isSaboteurFirstInOrder()) {
      throw new IllegalArgumentException("Saboteur isn't first to place bombs!");
    }

    bombService.setBomb(turn, saboteur.getLocation());
  }

  private boolean isSaboteurFirstInOrder() {
    return this.order[0].getSign() == Constants.SABOTEUR_HERO;
  }

  private SaboteurHero getSaboteur() {

    for (Hero hero : order) {
      if (hero.getSign().equals(Constants.SABOTEUR_HERO)) {
        return (SaboteurHero) hero;
      }
    }

    return null;
  }
}
