package com.github.teambuilding.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import com.github.teambuilding.dto.GameDto;
import com.github.teambuilding.dto.MappingService;
import com.github.teambuilding.validation.Input;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/game")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class GameApi {

  private MappingService mappingService;

  public GameApi(MappingService mappingService) {
    this.mappingService = mappingService;
  }

  @GET
  public GameDto testt() {
    return mappingService.getGame(1L);
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
  public GameDto makeAction(@Valid Input input) {

    Long gameId = input.getGameId();
    char command = input.getCommand().charAt(0);
    char heroPick = (input.getHeroPick() == null) ? '\0' : input.getHeroPick().charAt(0);

    return mappingService.makeTry(gameId, command, heroPick);
  }
}
