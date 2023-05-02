package com.github.teambuilding.bomb;

import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.game.Game;
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

  public BombService(BombRepository bombRepository, BuildingService buildingService,
      HeroService heroService, GuardService guardService) {

    this.bombRepository = bombRepository;

    this.buildingService = buildingService;
    this.heroService = heroService;
    this.guardService = guardService;
  }

  public void addBomb(Game game, short turn, Position position) {

    Bomb bomb = new Bomb();

    bomb.setTurnPlaced(turn);
    bomb.setLocation(position);
    bomb.setGame(game);

    bombRepository.save(bomb);
  }

  public String getSign(Long gameId, Position position) {

    Bomb bomb = bombRepository.findByGameIdAndPosition(gameId, position);
    return (bomb != null) ? bomb.getSign() : null;
  }

  public void explodeCheck(Long gameId, short turn) {

    List<Bomb> gameBombs = bombRepository.findByGameId(gameId);

    for (Bomb bomb : gameBombs) {

      if (isBombExploding(bomb, turn)) {

        buildingService.explodePosition(bomb.getLocation(), gameId);
        killAroundTheExplode(bomb, turn);
        bombRepository.delete(bomb);
        break;
      }
    }
  }

  private void killAroundTheExplode(Bomb bomb, short turn) {

    int bombRow = bomb.getRowLocation();
    int bombCol = bomb.getColLocation();

    for (int row = bombRow - 1; row <= bombRow + 1; row++) {
      for (int col = bombCol - 1; col <= bombCol + 1; col++) {

        Position position = new Position(row, col);

        if (heroService.isPositionHero(bomb.getGame().getId(), position)) {
          heroService.killAtPosition(bomb.getGame().getId(), position);
        }

        if (guardService.isGuardAtPosition(bomb.getGame().getId(), position)) {
          guardService.kill(bomb.getGame().getId(), turn);
        }
      }
    }
  }

  private static boolean isBombExploding(Bomb bomb, short turn) {
    return turn - bomb.getTurnPlaced() >= 5;
  }
}
