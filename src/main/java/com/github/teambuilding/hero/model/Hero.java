package com.github.teambuilding.hero.model;

import com.github.teambuilding.utility.Position;

import lombok.Data;

@Data
public abstract class Hero {

	protected String sign;
	protected Position location;
	protected boolean isAlive;
	
	protected Hero(String sign, Position location) {
		
		this.sign = sign;
		this.location = location;
		this.isAlive = true;
	}
}
