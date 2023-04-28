package com.github.teambuilding.game;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameService {

  private List<GamePlay> gamePlays;
  private GameRepository gameRepository;

  public GameService(GameRepository gameRepository) {
    this.gamePlays = new ArrayList<>();
    this.gameRepository = gameRepository;
  }

  public GamePlay getGame(Long id) {

    for (GamePlay game : gamePlays) {
      if (game.getGameId() == id) {
        return game;
      }
    }

    return null;
  }

  public GamePlay startNewGame() {

    GamePlay gamePlay = new GamePlay(gameRepository);
    this.gamePlays.add(gamePlay);
    return gamePlay;
  }

  public GamePlay makeAction(Long id, char command, char heroPick) {

    for (GamePlay gamePlay : gamePlays) {
      if (gamePlay.getGameId() == id) {

        if (GameStatusEnum.ONGOING != gamePlay.getStatus()) {
          throw new IllegalArgumentException("This game is compleated");
        }

        gamePlay.makeAction(command, heroPick);
        return gamePlay;
      }
    }

    return null;
  }
}
