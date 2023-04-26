package com.github.teambuilding.bomb;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.guard.GuardService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;

public class BombService {

  private List<Bomb> bombsPlaced;

  private BuildingService buildingService;
  private HeroService heroService;
  private GuardService guardService;

  public BombService(BuildingService buildingService, HeroService heroService,
      GuardService guardService) {

    this.bombsPlaced = new ArrayList<Bomb>();

    this.buildingService = buildingService;
    this.heroService = heroService;
    this.guardService = guardService;
  }

  public void setBomb(int turn, Position position) {
    bombsPlaced.add(new Bomb(turn, position));
  }

  public String getSign(Position position) {

    for (Bomb bomb : bombsPlaced) {
      if (bomb.isPositionBomb(position)) {
        return bomb.getSign();
      }
    }

    return null;
  }

  public void explode(int turn) {

    if (bombsPlaced.isEmpty()) {
      return;
    }

    Bomb bombToRemove = null;

    for (Bomb bomb : bombsPlaced) {
      if (bomb.isBombExploding(turn)) {

        buildingService.explodePosition(bomb.getLocation());
        bombToRemove = bomb;
        killAroundTheExplode(bomb, turn);
        break;
      }
    }

    bombsPlaced.remove(bombToRemove);
  }

  private void killAroundTheExplode(Bomb bomb, int turn) {

    int bombRow = bomb.getLocation().getRow();
    int bombCol = bomb.getLocation().getCol();

    for (int row = bombRow - 1; row <= bombRow + 1; row++) {
      for (int col = bombCol - 1; col <= bombCol + 1; col++) {

        Position position = new Position(row, col);

        if (Position.isPositionInBorders(position)) {

          if (heroService.isPositionHero(position)) {
            heroService.killAtPosition(position);
          }

          if (guardService.isGuardAtPosition(position)) {
            guardService.kill(turn);
          }
        }
      }
    }
  }
}
