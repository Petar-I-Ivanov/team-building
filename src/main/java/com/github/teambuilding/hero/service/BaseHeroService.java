package com.github.teambuilding.hero.service;

import com.github.teambuilding.bomb.BombService;
import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SaboteurHero;
import com.github.teambuilding.utility.Position;

public class BaseHeroService {
	
	private SaboteurHero saboteur;
	private Hero[] order;
	
	public BaseHeroService(SaboteurHero saboteur, Hero[] order) {
		this.saboteur = saboteur;
		this.order = order;
	}

	public String getSign(Position position) {
		
		Hero hero = getHeroByPosition(position);
		return (hero == null) ? null : hero.getSign();
	}
	
	public boolean isSaboteuorKilled() {
		return !this.saboteur.isAlive();
	}
	
	public boolean isPositionHero(Position position) {
		return getHeroByPosition(position) != null;
	}
	
	public boolean isHeroAroundPosition(Position position) {
		
		for (int row = position.getRow() - 1; row <= position.getRow() + 1; row++) {
			for (int col = position.getCol() - 1; col <= position.getCol() + 1; col++) {
				if (getHeroByPosition(new Position(row, col)) != null) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	public boolean setExplode(BombService bombService) {

		if (saboteur.isAlive() && !isSaboteurFirstInOrder()) {
			System.out.println("Saboteur isn't first to place bombs!");
			return false;
		}

		bombService.setBomb(0, this.saboteur.getLocation());
//		saboteur.specialAbility();
		return true;
	}
	
	private Hero getHeroByPosition(Position position) {
		
		for (Hero hero : order) {
			if (Position.arePositionsEqual(hero.getLocation(), position)) {
				return hero;
			}
		}

		return null;
	}
	
	private boolean isSaboteurFirstInOrder() {
		return this.order[0] == this.saboteur;
	}
}
