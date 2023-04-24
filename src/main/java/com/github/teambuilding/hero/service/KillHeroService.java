package com.github.teambuilding.hero.service;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.utility.Position;

public class KillHeroService {

	private TankHero tank;
	private SniperHero sniper;

	private Hero[] order;

	public KillHeroService(TankHero tank, SniperHero sniper, Hero[] order) {
		this.tank = tank;
		this.sniper = sniper;
		this.order = order;
	}

	public boolean kill() {

		if (sniper.isAlive()) {
			
		// && sniper.passiveAbility()) {
			return false;
		}

		if (tank.isAlive()) {
			order = removeHeroFromOrder(tank);
			return true;
		}

		killFirstInOrder();
		return true;
	}

	public void killAtPosition(Position position) {

		Hero hero = getHeroByPosition(position);
		hero.setLocation(new Position(-1, -1));
		hero.setAlive(false);
		order = removeHeroFromOrder(hero);
	}

	private void killFirstInOrder() {

		order[0].setLocation(new Position(-1, -1));
		order[0].setAlive(false);
		order = removeHeroFromOrder(order[0]);
	}

	private Hero[] removeHeroFromOrder(Hero heroToRemove) {

		Hero[] newOrder = new Hero[order.length - 1];
		int counter = 0;

		for (Hero hero : order) {
			if (hero != heroToRemove) {
				newOrder[counter++] = hero;
			}
		}

		return newOrder;
	}

	private Hero getHeroByPosition(Position position) {

		for (Hero hero : order) {
			if (hero.getLocation().getRow() == position.getRow() && hero.getLocation().getCol() == position.getCol()) {
				return hero;
			}
		}

		return null;
	}
}
