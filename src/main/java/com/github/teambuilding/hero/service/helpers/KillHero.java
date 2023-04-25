package com.github.teambuilding.hero.service.helpers;

import javax.validation.UnexpectedTypeException;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.hero.model.SniperHero;
import com.github.teambuilding.hero.model.TankHero;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public class KillHero {

	private KillHero() {}

	public static void kill(Hero[] heroes) {

		TankHero tank = (TankHero) getHeroBySign(heroes, Constants.TANK_HERO);
		SniperHero sniper = (SniperHero) getHeroBySign(heroes, Constants.SNIPER_HERO);

		if (tank == null || sniper == null) {
			throw new UnexpectedTypeException("Unexpected error orrcured during hero kill.");
		}

		if (sniper.isAlive() && sniper.passiveAbility()) {
			return;
		}

		if (tank.isAlive()) {
			heroes = removeHeroFromOrder(heroes, tank);
		}

		heroes = removeHeroFromOrder(heroes, heroes[0]);
	}
	
	public static void killAtPosition(Hero[] heroes, Position position) {
		
		for (Hero hero : heroes) {
			if (Position.arePositionsEqual(hero.getLocation(), position)) {
				heroes = removeHeroFromOrder(heroes, hero);
				return;
			}
		}
	}

	private static Hero getHeroBySign(Hero[] heroes, String heroSign) {

		for (Hero hero : heroes) {
			if (heroSign.equals(hero.getSign())) {
				return hero;
			}
		}

		return null;
	}

	private static Hero[] removeHeroFromOrder(Hero[] heroes, Hero heroToRemove) {

		Hero[] newOrder = new Hero[heroes.length - 1];
		int counter = 0;

		for (Hero hero : heroes) {
			if (hero != heroToRemove) {
				newOrder[counter++] = hero;
			}
		}

		return newOrder;
	}
}
