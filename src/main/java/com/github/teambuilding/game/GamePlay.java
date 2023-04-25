package com.github.teambuilding.game;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.guard.GuardService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class GamePlay {
	
	private int turn;

	private BuildingService buildingService;
	private HeroService heroService;
	private GuardService guardService;
	private BombService bombService;

	public GamePlay() {
		
		this.turn = 0;

		this.buildingService = new BuildingService();
		this.heroService = new HeroService(buildingService);
		this.guardService = new GuardService(this.buildingService, this.heroService);
		this.bombService = new BombService(buildingService, heroService, guardService);
		
		this.heroService.setBombService(bombService);
	}

	public void makeAction(char command, char heroSign) {
		
		heroService.makeAction(command, heroSign);
		guardService.move(turn);
		bombService.explode(turn);
		turn++;
		
		if (isGameWon() || isGameLost()) {
			throw new IllegalArgumentException("You " + isGameWon() + "the game!");
		}
	}

	public String[][] getGameboard() {

		String[][] gameboard = new String[Constants.GAMEBOARD_MAX_ROW][Constants.GAMEBOARD_MAX_COL];

		for (int row = 0; row < Constants.GAMEBOARD_MAX_ROW; row++) {
			for (int col = 0; col < Constants.GAMEBOARD_MAX_COL; col++) {

				Position position = new Position(row, col);

				String sign = heroService.getSign(position);
				sign = (sign == null) ? buildingService.getSign(position) : sign;
				sign = (sign == null) ? guardService.getSign(position) : sign;
				sign = (sign == null) ? bombService.getSign(position) : sign;
				sign = (sign == null) ? "X" : sign;
				gameboard[row][col] = sign;
			}
		}

		return gameboard;
	}
	
	private boolean isGameWon() {
		return buildingService.areBuildingsDestroyed();
	}
	
	private boolean isGameLost() {
		return heroService.isSaboteurKilled();
	}
}
