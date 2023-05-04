package com.github.teambuilding.dto;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.services.GameService;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MappingService {

  private GameService gameService;

  public MappingService(GameService gameService) {
    this.gameService = gameService;
  }

  public GameDto startNewGame() {
    return convertGamePlayIntoGameDto(gameService.startNewGame());
  }

  public GameDto getGame(Long id) {
    return convertGamePlayIntoGameDto(gameService.getGame(id));
  }

  public GameDto makeTry(Long id, char command, char heroPick) {
    return convertGamePlayIntoGameDto(gameService.makeAction(id, command, heroPick));
  }

  private GameDto convertGamePlayIntoGameDto(Game game) {

    GameDto dto = new GameDto();

    dto.setId(game.getId());
    dto.setTurn(game.getTurn());
    dto.setStatus(game.getStatus());
    dto.setGameboard(gameService.getGameboard(game.getId()));

    return dto;
  }
}
