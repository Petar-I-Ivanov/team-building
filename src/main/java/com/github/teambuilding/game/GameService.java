package com.github.teambuilding.game;

import javax.enterprise.context.ApplicationScoped;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.building.service.BuildingService;
import com.github.teambuilding.guard.GuardService;
import com.github.teambuilding.hero.service.HeroService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

@ApplicationScoped
public class GameService {

	private BuildingService buildingService;
	private HeroService heroService;
	private GuardService guardService;
	private BombService bombService;
	
	public GameService(BuildingService buildingService, HeroService heroService) {
		this.buildingService = buildingService;
		this.heroService = heroService;
		this.guardService = new GuardService(buildingService, heroService);
		this.bombService = new BombService(buildingService, heroService, guardService);
	}
	
	public void makeAction(char action) {
		
		heroService.makeAction(action, bombService);
		guardService.move(1);
		bombService.explode(5);
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
	
	public boolean isPositionInBorders(Position position) {
		return position.getRow() >= 0 && position.getRow() < Constants.GAMEBOARD_MAX_ROW
			&& position.getCol() >= 0 && position.getCol() < Constants.GAMEBOARD_MAX_COL;
	}
	
	public boolean isPositionNotFree(Position position) {
		
		return !getGameboard()[position.getRow()][position.getCol()].equals(Constants.EMPTY_POSITION);
	}
}
