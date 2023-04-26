package com.github.teambuilding.game;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameService {

  private List<GamePlay> gamePlays;

  public GameService() {
    this.gamePlays = new ArrayList<>();
  }

  public String[][] startNewGame() {

    GamePlay gamePlay = new GamePlay();
    this.gamePlays.add(gamePlay);
    return gamePlay.getGameboard();
  }

  public String[][] getGameboard(Long id) {

    for (GamePlay gamePlay : gamePlays) {
      if (gamePlay.getGameId() == id) {
        return gamePlay.getGameboard();
      }
    }

    return null;
  }

  public String[][] makeAction(Long id, char command, char heroPick) {

    for (GamePlay gamePlay : gamePlays) {
      if (gamePlay.getGameId() == id) {

        gamePlay.makeAction(command, heroPick);
        return gamePlay.getGameboard();
      }
    }

    return null;
  }
}
