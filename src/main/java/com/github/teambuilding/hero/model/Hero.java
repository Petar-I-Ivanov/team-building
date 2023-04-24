package com.github.teambuilding.hero.model;

import com.github.teambuilding.utility.Position;

import lombok.Data;

@Data
public abstract class Hero {

	protected String sign;
	protected Position location;
	protected byte orderPosition;
	protected boolean isAlive;
	
	protected Hero(String sign, Position location, int orderPosition) {
		this.sign = sign;
		this.location = location;
		this.orderPosition = (byte) orderPosition;
		isAlive = true;
	}
}
