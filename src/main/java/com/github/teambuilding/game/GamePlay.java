package com.github.teambuilding.game;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.guard.GuardService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class GamePlay {

	private Game game;

	private BuildingService buildingService;
	private HeroService heroService;
	private GuardService guardService;
	private BombService bombService;

	public GamePlay() {

		this.game = new Game();

		this.buildingService = new BuildingService();
		this.heroService = new HeroService(buildingService);
		this.guardService = new GuardService(this.buildingService, this.heroService);
		this.bombService = new BombService(buildingService, heroService, guardService);

		this.heroService.setBombService(bombService);
	}

	public long getGameId() {
		return this.game.getId();
	}

	public GameStatusEnum getStatus() {
		return this.game.getStatus();
	}

	public void makeAction(char command, char heroSign) {

		heroService.makeAction(command, heroSign, game.getTurn());
		guardService.move(game.getTurn());
		bombService.explode(game.getTurn());
		game.setTurn(game.getTurn() + 1);

		if (isGameWon() || isGameLost()) {

			GameStatusEnum newStatus = (isGameWon()) ? GameStatusEnum.WON : GameStatusEnum.LOST;
			game.setStatus(newStatus);
		}
	}

	public String[][] getGameboard() {

		String[][] gameboard = new String[Constants.GAMEBOARD_MAX_ROW][Constants.GAMEBOARD_MAX_COL];

		for (int row = 0; row < Constants.GAMEBOARD_MAX_ROW; row++) {
			for (int col = 0; col < Constants.GAMEBOARD_MAX_COL; col++) {

				Position position = new Position(row, col);

				String sign = bombService.getSign(position);
				sign = (sign == null) ? heroService.getSign(position) : sign;
				sign = (sign == null) ? buildingService.getSign(position) : sign;
				sign = (sign == null) ? guardService.getSign(position) : sign;
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
