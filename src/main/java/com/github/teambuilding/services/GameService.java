package com.github.teambuilding.services;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.GameStatusEnum;
import com.github.teambuilding.models.GameboardObject;
import com.github.teambuilding.repositories.GameRepository;
import com.github.teambuilding.services.bomb.BombServiceImpl;
import com.github.teambuilding.services.building.BuildingServiceImpl;
import com.github.teambuilding.services.guard.GuardServiceImpl;
import com.github.teambuilding.services.hero.HeroServiceImpl;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameService {

  private GameRepository gameRepository;

  private BuildingServiceImpl buildingService;
  private HeroServiceImpl heroService;
  private GuardServiceImpl guardService;
  private BombServiceImpl bombService;

  public GameService(GameRepository gameRepository, BuildingServiceImpl buildingService,
      HeroServiceImpl heroService, GuardServiceImpl guardService, BombServiceImpl bombService) {

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

    buildingService.generateBuildings(game);
    heroService.generateHeroes(game);
    guardService.generateGuard(game);

    gameRepository.save(game);

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
      deleteGameboardObjects(gameId);
    }

    return gameRepository.save(game);
  }

  public String[][] getGameboard(Long gameId) {

    String[][] gameboard = new String[Constants.GAMEBOARD_MAX_ROW][Constants.GAMEBOARD_MAX_COL];
    setAllGameboardObjects(gameboard, gameId);

    for (int row = 0; row < gameboard.length; row++) {
      for (int col = 0; col < gameboard[0].length; col++) {

        if (gameboard[row][col] == null) {
          gameboard[row][col] = Constants.EMPTY_POSITION;
        }
      }
    }

    return gameboard;
  }

  public void oldGameboardObjectsClean(LocalDate date) {

    for (Game oldOngoingGame : gameRepository.findOngoingGamesBefore(date)) {
      deleteGameboardObjects(oldOngoingGame.getId());
    }
  }

  private boolean isGameWon(Long gameId) {
    return buildingService.areBuildingsDestroyed(gameId);
  }

  private boolean isGameLost(Long gameId) {
    return heroService.isSaboteurKilled(gameId);
  }

  private void setAllGameboardObjects(String[][] gameboard, Long gameId) {

    List<GameboardObject> objects = fetchAllGameBoardObjects(gameId);

    for (GameboardObject object : objects) {
      if (Position.isPositionInBorders(object.getLocation())) {
        gameboard[object.getRowLocation()][object.getColLocation()] = object.getSign();
      }
    }
  }

  private List<GameboardObject> fetchAllGameBoardObjects(Long gameId) {

    List<GameboardObject> gameboardObjects = new ArrayList<>();

    gameboardObjects.addAll(buildingService.getAllForGameId(gameId));
    gameboardObjects.addAll(heroService.getAllForGameId(gameId));
    gameboardObjects.add(guardService.getAllForGameId(gameId));
    gameboardObjects.addAll(bombService.getAllForGameId(gameId));

    return gameboardObjects;
  }

  private void deleteGameboardObjects(Long gameId) {

    buildingService.deleteAllWhereGameId(gameId);
    heroService.deleteAllWhereGameId(gameId);
    guardService.deleteAllWhereGameId(gameId);
    bombService.deleteAllWhereGameId(gameId);
  }
}
