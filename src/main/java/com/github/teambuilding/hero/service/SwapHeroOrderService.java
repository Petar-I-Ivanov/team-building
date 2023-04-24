package com.github.teambuilding.hero.service;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.utility.Position;

public class SwapHeroOrderService {

	private Hero[] order;

	public SwapHeroOrderService(Hero[] order) {
		this.order = order;
	}

	public boolean switchOrder(char heroSignPick) {

		if (isHeroNowsLeader(heroSignPick) || isPickedHeroDead(heroSignPick)) {
			System.out.println("Picked hero is unavailable to switch.");
			return false;
		}

		switchHeroesOrder(heroSignPick);
		return true;
	}

	private boolean isHeroNowsLeader(char heroSignPick) {
		return getHeroBySign(heroSignPick) == order[0];
	}

	private boolean isPickedHeroDead(char heroSignPick) {
		return !getHeroBySign(heroSignPick).isAlive();
	}

	private void switchHeroesOrder(char heroSignPick) {

		Hero firstHero = order[0];
		Hero secondHero = getHeroBySign(heroSignPick);

		Position firstLocation = firstHero.getLocation();
		Position secondLocation = secondHero.getLocation();

		firstHero.setLocation(secondLocation);
		secondHero.setLocation(firstLocation);

		int firstHeroIndex = getHeroOrderNumber(firstHero);
		int secondHeroIndex = getHeroOrderNumber(secondHero);

		order[firstHeroIndex] = secondHero;
		order[secondHeroIndex] = firstHero;
	}

	private Hero getHeroBySign(char sign) {

		for (Hero hero : order) {
			if (hero.getSign().equals(String.valueOf(sign))) {
				return hero;
			}
		}

		return null;
	}

	private int getHeroOrderNumber(Hero hero) {

		for (int i = 0; i < order.length; i++) {
			if (order[i].equals(hero)) {
				return i;
			}
		}

		return -1;
	}
}
