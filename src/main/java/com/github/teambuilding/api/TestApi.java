package com.github.teambuilding.api;

import com.github.teambuilding.game.GameService;
import com.github.teambuilding.validation.Input;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/game")
public class TestApi {

  private GameService gameService;

  public TestApi(GameService gameService) {
    this.gameService = gameService;
  }

  @GET
  public String[][] test() {
    return gameService.getGameboard(1L);
  }

  @POST
  public String[][] startNewGame() {
    return gameService.startNewGame();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public String[][] makeAction(@Valid Input input) {

    char command = input.getCommand().charAt(0);
    char heroPick = (input.getHeroPick() == null) ? '\0' : input.getHeroPick().charAt(0);

    return gameService.makeAction(1L, command, heroPick);
  }
}
