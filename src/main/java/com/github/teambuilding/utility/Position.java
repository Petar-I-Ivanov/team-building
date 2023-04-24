package com.github.teambuilding.utility;

public class Position {

	private byte row;
	private byte col;
	
	public Position(int row, int col) {
		this.row = (byte) row;
		this.col = (byte) col;
	}
	
	public Position() {
		this.row = (byte) RandomGenerator.getRandomValue(Constants.GAMEBOARD_MAX_ROW);
		this.col = (byte) RandomGenerator.getRandomValue(Constants.GAMEBOARD_MAX_COL);
	}
 	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public static boolean arePositionsEqual(Position positionOne, Position positionTwo) {
		return positionOne.getRow() == positionTwo.getRow() && positionOne.getCol() == positionTwo.getCol();
	}
	
	public static boolean isPositionInBorders(Position position) {
		
		return position.getRow() >= 0 && position.getRow() < Constants.GAMEBOARD_MAX_ROW
				&& position.getCol() >= 0 && position.getCol() < Constants.GAMEBOARD_MAX_COL;
	}
}
