package com.github.teambuilding.game;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.guard.GuardService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameService {

  private GameRepository gameRepository;

  private BuildingService buildingService;
  private HeroService heroService;
  private GuardService guardService;
  private BombService bombService;

  public GameService(GameRepository gameRepository, BuildingService buildingService,
      HeroService heroService, GuardService guardService, BombService bombService) {

    this.gameRepository = gameRepository;

    this.buildingService = buildingService;
    this.heroService = heroService;
    this.guardService = guardService;
    this.bombService = bombService;

    this.heroService.setBombService(bombService);
  }

  public Game getGame(Long gameId) {
    return gameRepository.findById(gameId);
  }

  public Game startNewGame() {

    Game game = gameRepository.save(new Game());

    buildingService.generateBuildings(game.getId());
    heroService.generateHeroes(game.getId());
    guardService.generateGuard(game.getId());

    return game;
  }

  public Game makeAction(Long gameId, char command, char heroPick) {

    Game game = gameRepository.findById(gameId);

    if (GameStatusEnum.ONGOING != game.getStatus()) {
      throw new IllegalArgumentException("This game is compleated");
    }

    heroService.makeAction(game.getId(), command, heroPick, game.getTurn());
    bombService.explodeCheck(game.getId(), game.getTurn());
    guardService.move(game.getId(), game.getTurn());
    game.setTurn((short) (game.getTurn() + 1));

    if (isGameWon(gameId) || isGameLost(gameId)) {

      GameStatusEnum newStatus = (isGameWon(gameId)) ? GameStatusEnum.WON : GameStatusEnum.LOST;
      game.setStatus(newStatus);
    }

    return gameRepository.save(game);
  }

  public String[][] getGameboard(Long gameId) {

    String[][] gameboard = new String[Constants.GAMEBOARD_MAX_ROW][Constants.GAMEBOARD_MAX_COL];

    for (int row = 0; row < Constants.GAMEBOARD_MAX_ROW; row++) {
      for (int col = 0; col < Constants.GAMEBOARD_MAX_COL; col++) {

        Position position = new Position(row, col);

        String sign = bombService.getSign(gameId, position);
        sign = (sign == null) ? heroService.getSign(gameId, position) : sign;
        sign = (sign == null) ? buildingService.getSign(position, gameId) : sign;
        sign = (sign == null) ? guardService.getSign(gameId, position) : sign;
        sign = (sign == null) ? "X" : sign;
        gameboard[row][col] = sign;
      }
    }

    return gameboard;
  }

  private boolean isGameWon(Long gameId) {
    return buildingService.areBuildingsDestroyed(gameId);
  }

  private boolean isGameLost(Long gameId) {
    return heroService.isSaboteurKilled(gameId);
  }
}
