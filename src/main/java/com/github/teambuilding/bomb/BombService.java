package com.github.teambuilding.bomb;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.guard.GuardService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BombService {

  private BombRepository bombRepository;

  private BuildingService buildingService;
  private HeroService heroService;
  private GuardService guardService;

  public BombService(BuildingService buildingService, HeroService heroService,
      GuardService guardService, BombRepository bombRepository) {

    this.bombRepository = bombRepository;

    this.buildingService = buildingService;
    this.heroService = heroService;
    this.guardService = guardService;
  }

  public void setBomb(int turn, Position position, Long gameId) {

    Bomb bomb = new Bomb();
    bomb.setTurnToPlace((byte) turn);
    bomb.setLocation(position);
    bomb.setGameId(gameId);

    bombRepository.save(bomb);
  }

  public String getSign(Position position, Long gameId) {

    Bomb bomb = bombRepository.findByGameIdAndPosition(gameId, position);
    return (bomb != null) ? bomb.getSign() : null;
  }

  public void explode(int turn, Long gameId) {

    List<Bomb> gameBombs = bombRepository.findByGameId(gameId);

    for (Bomb bomb : gameBombs) {
      if (bomb.isBombExploding(turn)) {

        buildingService.explodePosition(bomb.getLocation(), gameId);
        killAroundTheExplode(bomb, turn);
        bombRepository.deleteById(bomb.getId());
        break;
      }
    }
  }

  private void killAroundTheExplode(Bomb bomb, int turn) {

    int bombRow = bomb.getLocation().getRow();
    int bombCol = bomb.getLocation().getCol();

    for (int row = bombRow - 1; row <= bombRow + 1; row++) {
      for (int col = bombCol - 1; col <= bombCol + 1; col++) {

        Position position = new Position(row, col);

        if (Position.isPositionInBorders(position)) {

          if (heroService.isPositionHero(position, bomb.getGameId())) {
            heroService.killAtPosition(position, bomb.getGameId());
          }

          if (guardService.isGuardAtPosition(position, bomb.getGameId())) {
            guardService.kill(turn, bomb.getGameId());
          }
        }
      }
    }
  }
}
