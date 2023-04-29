package com.github.teambuilding.api;

import com.github.teambuilding.dto.GameDto;
import com.github.teambuilding.dto.MappingService;
import com.github.teambuilding.validation.Input;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/game")
public class GameApi {

  private MappingService mappingService;

  public GameApi(MappingService mappingService) {
    this.mappingService = mappingService;
  }

  @GET
  @Path("/{gameId}")
  public GameDto test(Long gameId) {
    return mappingService.getGame(gameId);
  }

  @POST
  public GameDto startNewGame() {
    return mappingService.startNewGame();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public GameDto makeAction(@Valid Input input) {
	  
    char command = input.getCommand().charAt(0);
    char heroPick = (input.getHeroPick() == null) ? '\0' : input.getHeroPick().charAt(0);
    Long gameId = input.getGameId();

    return mappingService.makeTry(gameId, command, heroPick);
  }
}
