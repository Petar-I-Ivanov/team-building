package com.github.teambuilding.building.model;

import java.util.ArrayList;
import java.util.List;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

public abstract class Building {

	protected final int HEIGHT;
	protected final int WIDTH;
	
	protected String sign;
	protected Position[] locations;
	protected List<Position> explodedPositions;
	
	protected boolean isDestroyed;
	
	public Building(String sign, int height, int width) {
		this.sign = sign;
		this.HEIGHT = height;
		this.WIDTH = width;
		this.explodedPositions = new ArrayList<>();
	}
	
	public String getSign() {
		return this.sign;
	}
	
	public Position getStartingPosition() {
		return this.locations[0];
	}
	
	public void cleansePositions() {
		this.locations = new Position[0];
	}
	
	public boolean isDestroyed() {
		return this.isDestroyed;
	}
	
	public void set(Position startingPosition) {
		
		this.locations = new Position[this.HEIGHT * this.WIDTH];
		int counter = 0;
		
		for (int row = 0; row < this.HEIGHT; row++) {
			for (int col = 0; col < this.WIDTH; col++) {
				this.locations[counter++] =
						new Position(
								startingPosition.getRow() + row,
								startingPosition.getCol() + col);
			}
		}
		
		this.isDestroyed = false;
	}
	
	public boolean isPositionBuilding(Position position) {
		
		for (Position location : this.locations) {
			if (arePositionsEqual(location, position) && !isPositionExploded(position)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addExplode(Position position) {
		
		this.explodedPositions.add(position);
		
		if (isExplodeFatal()) {
			this.isDestroyed = true;
		}
	}
	
	public boolean canSetBuilding(Position position) {
	    return isPositionNotOnBorders(position) && !isPositionNearTheHeroes(position);
	}
	
	public abstract boolean isEntryPossible(Position position);
	protected abstract boolean isExplodeFatal();
	
	protected boolean arePositionsEqual(Position positionOne, Position positionTwo) {
  		return positionOne.getRow() == positionTwo.getRow() && positionOne.getCol() == positionTwo.getCol();
  	}
	
	private boolean isPositionNotOnBorders(Position position) {
      
      return (position.getRow() > 0 && position.getRow() < Constants.GAMEBOARD_MAX_ROW - 1 - this.HEIGHT) &&
             (position.getCol() > 0 && position.getCol() < Constants.GAMEBOARD_MAX_COL - 1 - this.WIDTH);
    }
	
  	private boolean isPositionNearTheHeroes(Position position) {
  	  return (position.getRow() + this.HEIGHT) >= 10 && (position.getCol() + this.WIDTH) >= 8;
  	}

	private boolean isPositionExploded(Position position) {

		for (Position location : this.explodedPositions) {
			if (arePositionsEqual(location, position)) {
				return true;
			}
		}

		return false;
	}
}
