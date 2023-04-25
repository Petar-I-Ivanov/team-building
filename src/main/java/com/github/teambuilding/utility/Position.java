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

    return positionOne.getRow() == positionTwo.getRow()
        && positionOne.getCol() == positionTwo.getCol();
  }

  public static boolean isPositionInBorders(Position position) {

    return position.getRow() >= 0 && position.getRow() < Constants.GAMEBOARD_MAX_ROW
        && position.getCol() >= 0 && position.getCol() < Constants.GAMEBOARD_MAX_COL;
  }
  
  public static Position getPositionBasedOnDirection(Position oldPosition, char direction) {
	  
	  return switch(direction) {
	  
	  case Constants.FORWARD_MOVE -> new Position(oldPosition.getRow() - 1, oldPosition.getCol());
	  case Constants.RIGHT_MOVE -> new Position(oldPosition.getRow(), oldPosition.getCol() + 1);
	  case Constants.BACK_MOVE -> new Position(oldPosition.getRow() + 1, oldPosition.getCol());
	  case Constants.LEFT_MOVE -> new Position(oldPosition.getRow(), oldPosition.getCol() - 1);
	  
	  default -> throw new IllegalArgumentException("Invalid direction at movement");
	  };
  }
  
  public static Position modifyPositionIfOutOfBorders(Position position) {
	  
	  if (position.getRow() >= Constants.GAMEBOARD_MAX_ROW) {
		  return new Position(0, position.getCol());
	  }
	  
	  if (position.getRow() < 0) {
		  return new Position(Constants.GAMEBOARD_MAX_ROW - 1, position.getCol());
	  }
	  
	  if (position.getCol() >= Constants.GAMEBOARD_MAX_COL) {
		  return new Position(position.getRow(), 0);
	  }
	  
	  if (position.getCol() < 0) {
		  return new Position(position.getRow(), Constants.GAMEBOARD_MAX_COL - 1);
	  }
	  
	  return position;
  }
}
