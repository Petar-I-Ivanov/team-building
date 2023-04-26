package com.github.teambuilding.dto;

import javax.inject.Singleton;

import com.github.teambuilding.game.GamePlay;
import com.github.teambuilding.game.GameService;

@Singleton
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
	
	private GameDto convertGamePlayIntoGameDto(GamePlay gamePlay) {
		
		GameDto dto = new GameDto();
		
		dto.setId(gamePlay.getGameId());
		dto.setStatus(gamePlay.getStatus());
		dto.setGameboard(gamePlay.getGameboard());
		
		return dto;
	}
}
