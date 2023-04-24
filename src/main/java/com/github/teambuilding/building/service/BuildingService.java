package com.github.teambuilding.building.service;

import javax.inject.Singleton;

import com.github.teambuilding.building.model.BigBuilding;
import com.github.teambuilding.building.model.MediumBuilding;
import com.github.teambuilding.building.model.SmallBuilding;
import com.github.teambuilding.utility.Position;

@Singleton
public class BuildingService {

	private SmallBuilding smallB;
	private MediumBuilding mediumB;
	private BigBuilding bigB;
	
	private BaseBuildingService baseBuildingService;
	private GenerateBuildingService generateBuildingService;
	
	public BuildingService() {
		
		generateBuildingInstances();
		generateServices();
		
		this.generateBuildingService.generateBuildings();
	}
	
	public String getSign(Position position) {
		return baseBuildingService.getSign(position);
	}

	public boolean isPositionBuilding(Position position) {
		return baseBuildingService.isPositionBuilding(position);
	}

	public boolean isEntryPossible(Position position) {
		return baseBuildingService.isEntryPossible(position);
	}

	public void explodePosition(Position position) {
		baseBuildingService.explodePosition(position);
	}

	public boolean areBuildingsDestroyed() {
		return baseBuildingService.areBuildingsDestroyed();
	}
	
	private void generateBuildingInstances() {
		
		this.smallB = new SmallBuilding();
		this.mediumB = new MediumBuilding();
		this.bigB = new BigBuilding();
	}
	
	private void generateServices() {
		
		this.baseBuildingService = new BaseBuildingService(smallB, mediumB, bigB);
		this.generateBuildingService = new GenerateBuildingService(smallB, mediumB, bigB);
	}
}
