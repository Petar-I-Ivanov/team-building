package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.utility.Position;

public class BuildingService {

  private Building[] buildings;

  public BuildingService() {

    this.buildings = GenerateBuilding.generate();

    while (buildings == null) {
      this.buildings = GenerateBuilding.generate();
    }
  }

  public String getSign(Position position) {

    Building building = getBuildingByPosition(position);

    if (building != null) {
      return building.getSign();
    }

    return null;
  }

  public boolean isPositionBuilding(Position position) {

    return getBuildingByPosition(position) != null;
  }

  public boolean isEntryPossible(Position position) {

    for (Building building : this.buildings) {
      if (building.isEntryPossible(position)) {
        return true;
      }
    }

    return false;
  }

  public void explodePosition(Position position) {

    Building building = getBuildingByPosition(position);

    if (building == null) {
      return;
    }

    if (!building.isDestroyed()) {
      building.addExplode(position);
    }

    if (building.isDestroyed()) {
      building.cleansePositions();
    }
  }

  public boolean areBuildingsDestroyed() {

    for (Building building : this.buildings) {
      if (!building.isDestroyed()) {
        return false;
      }
    }

    return true;
  }

  private Building getBuildingByPosition(Position position) {

    for (Building building : this.buildings) {
      if (building.isPositionBuilding(position)) {
        return building;
      }
    }

    return null;
  }
}
