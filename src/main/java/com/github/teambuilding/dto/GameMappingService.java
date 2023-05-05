package com.github.teambuilding.dto;

import com.github.teambuilding.encoder.Base64IdEncoder;
import com.github.teambuilding.encoder.IdEncoder;
import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.GameStatusEnum;
import com.github.teambuilding.services.GameService;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameMappingService {

  private IdEncoder encoder;
  private GameService gameService;

  public GameMappingService(Base64IdEncoder encoder, GameService gameService) {
    this.encoder = encoder;
    this.gameService = gameService;
  }

  public GameDto startNewGame() {
    return convertGameIntoGameDto(gameService.startNewGame());
  }

  public GameDto getGame(String encodedId) {

    Long decodedId = encoder.decode(encodedId);
    return convertGameIntoGameDto(gameService.getGame(decodedId));
  }

  public GameDto makeTry(String encodedId, char command, char heroPick) {

    Long decodedId = encoder.decode(encodedId);
    return convertGameIntoGameDto(gameService.makeAction(decodedId, command, heroPick));
  }

  private GameDto convertGameIntoGameDto(Game game) {

    GameDto dto = new GameDto();
    String encodedId = encoder.encode(game.getId());

    dto.setId(encodedId);
    dto.setTurn(game.getTurn());
    dto.setStatus(game.getStatus());

    if (game.getStatus() == GameStatusEnum.ONGOING) {
      dto.setGameboard(gameService.getGameboard(game.getId()));
    }

    return dto;
  }
}
