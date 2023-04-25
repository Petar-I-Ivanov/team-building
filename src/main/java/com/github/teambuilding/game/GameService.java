package com.github.teambuilding.game;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameService {

	private GamePlay gamePlay;

	public GameService() {
		this.gamePlay = new GamePlay();
	}

	public String[][] getGameboard() {
		return this.gamePlay.getGameboard();
	}
	
	public String[][] makeAction(char command, char heroPick) {
		
		this.gamePlay.makeAction(command, heroPick);
		return this.gamePlay.getGameboard();
	}
}
