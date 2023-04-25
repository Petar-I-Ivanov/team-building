package com.github.teambuilding.guard;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;

import lombok.Data;

@Data
public class Guard {

	private String sign;
	private Position location;
	private boolean isSleep;
	private int turnToBeAsleep;
	
	public Guard(Position startingLocation) {
		
		this.sign = Constants.GUARD;
		this.location = startingLocation;
		this.isSleep = false;
		this.turnToBeAsleep = 0;
	}
}
