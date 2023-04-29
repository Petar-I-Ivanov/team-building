package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BuildingService {

  private BuildingRepository buildingRepository;

  public BuildingService(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }

  public void generateBuildings(Long gameId) {

    List<Building> buildings = GenerateBuilding.generate();

    while (buildings == null) {
      buildings = GenerateBuilding.generate();
    }

    for (Building building : buildings) {
      building.setGameId(gameId);
    }

    buildingRepository.save(buildings);
  }

  public String getSign(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return (building != null) ? building.getSign() : null;
  }

  public boolean isPositionBuilding(Position position, Long gameId) {
    return getBuildingByPosition(position, gameId) != null;
  }

  public boolean isEntryPossible(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return building != null && building.isEntryPossible(position);
  }

  public void explodePosition(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);

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

  public boolean areBuildingsDestroyed(Long gameId) {

    List<Building> buildings = buildingRepository.getByGameId(gameId);

    for (Building building : buildings) {
      if (!building.isDestroyed()) {
        return false;
      }
    }

    return true;
  }

  private Building getBuildingByPosition(Position position, Long gameId) {
    return buildingRepository.getByGameIdAndPosition(gameId, position);
  }
}
