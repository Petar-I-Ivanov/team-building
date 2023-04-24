package com.github.teambuilding.utility;

import java.util.Random;

public class RandomGenerator {

	public static char getRandomMove() {
		
		int randomWay = new Random().nextInt(4);
		
		return switch (randomWay) {
			case 0 ->  Constants.FORWARD_MOVE;
			case 1 -> Constants.BACK_MOVE;
			case 2 -> Constants.LEFT_MOVE;
			case 3 -> Constants.RIGHT_MOVE;
			default -> Constants.NULL;
		};
	}
	
	public static int twentyFourSidedDice() {
		return new Random().nextInt(24) + 1;
	}
	
	public static int getRandomValue(int border) {
		return new Random().nextInt(border);
	}
}
