package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.game.Game;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BuildingService {

  private BuildingRepository buildingRepository;

  public BuildingService(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }

  public void generateBuildings(Game game) {

    List<Building> buildings = GenerateBuildings.generate();

    while (buildings == null) {
      buildings = GenerateBuildings.generate();
    }

    for (Building building : buildings) {
      building.setGame(game);
    }

    buildingRepository.save(buildings);
    game.setBuildings(buildings);
  }

  public String getSign(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return (building != null && !building.isExploded()) ? building.getSign() : null;
  }

  public boolean isPositionBuilding(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return building != null && !building.isExploded();
  }

  public boolean isEntryPossible(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return building != null && building.isEntryPossible();
  }

  public void explodePosition(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);

    if (building == null) {
      return;
    }

    building.setExploded(true);
    buildingRepository.save(building);

    List<Building> wholeBuilding =
        buildingRepository.getByGameIdAndSign(gameId, building.getSign());

    if (BuildingDestroyCheck.isBuildingDestroyed(wholeBuilding)) {
      buildingRepository.delete(wholeBuilding);
    }
  }

  public boolean areBuildingsDestroyed(Long gameId) {

    List<Building> buildings = buildingRepository.getByGameId(gameId);
    return buildings == null || buildings.isEmpty();
  }

  private Building getBuildingByPosition(Position position, Long gameId) {
    return buildingRepository.getByGameIdAndPosition(gameId, position);
  }
}
