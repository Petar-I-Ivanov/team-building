package com.github.teambuilding.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.github.teambuilding.game.GameService;
import com.github.teambuilding.validation.Input;

@Path("/game")
public class TestApi {

	private GameService gameService;
	
	public TestApi(GameService gameService) {
		this.gameService = gameService;
	}
	
	@GET
	public String[][] test() {
		return gameService.getGameboard();
	}
	
	@POST
	public String[][] startNewGame() {
		return gameService.getGameboard();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String[][] makeAction(@Valid Input input) {
		gameService.makeAction(input.getCommand().charAt(0));
		return gameService.getGameboard();
	}
}
