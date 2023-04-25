package com.github.teambuilding.hero.service.helpers;

import javax.validation.UnexpectedTypeException;

import com.github.teambuilding.hero.model.Hero;
import com.github.teambuilding.utility.Position;

public class SwapHeroOrder {

	private SwapHeroOrder() {}
	
	public static void swapHero(char heroSign, Hero[] heroes) {
		
		Hero hero = getHeroBySign(heroSign, heroes);
		
		if (hero == null) {
			throw new IllegalArgumentException("Invalid hero sign at swap.");
		}
		
//		if (hero.getOrderPosition() == 1) {
//			throw new IllegalArgumentException("Hero is already first.");
//		}
		
		Hero firstHero = heroes[0];
		
		if (!(hero.isAlive() || firstHero.isAlive())) {
			throw new IllegalArgumentException("Used heroes are already dead.");
		}
		
		swap(hero, firstHero, heroes);
		
	}
	
	private static Hero getHeroBySign(char heroSign, Hero[] heroes) {
		
		String stringHeroSign = String.valueOf(heroSign);
		
		for (Hero hero : heroes) {
			if (stringHeroSign.equals(hero.getSign())) {
				return hero;
			}
		}
		
		return null;
	}
	
	private static void swap(Hero heroOne, Hero heroTwo, Hero[] heroes) {
		
		Position tempPosition = heroOne.getLocation();
		
		int heroOneIndex = getHeroIndex(heroOne, heroes);
		int heroTwoIndex = getHeroIndex(heroTwo, heroes);
		
		heroOne.setLocation(heroTwo.getLocation());
		heroTwo.setLocation(tempPosition);
		
		heroes[heroOneIndex] = heroTwo;
		heroes[heroTwoIndex] = heroTwo;
	}
	
	private static int getHeroIndex(Hero hero, Hero[] heroes) {
		
		for (int i = 0; i < heroes.length; i++) {
			if (heroes[i] == hero) {
				return i;
			}
		}
		
		throw new UnexpectedTypeException("Unexpected hero type at hero swap");
	}
}
