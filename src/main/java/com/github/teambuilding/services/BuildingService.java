package com.github.teambuilding.services;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.repositories.BuildingRepository;
import com.github.teambuilding.utility.Position;
import com.github.teambuilding.utility.building.BuildingDestroyCheck;
import com.github.teambuilding.utility.building.GenerateBuildings;
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

  public List<Building> getAllForGameId(Long gameId) {
    return buildingRepository.findByGameIdAndDestroyedFalse(gameId);
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
        buildingRepository.findByGameIdAndSign(gameId, building.getSign());

    if (BuildingDestroyCheck.isBuildingDestroyed(wholeBuilding)) {
      buildingRepository.delete(wholeBuilding);
    }
  }

  public boolean areBuildingsDestroyed(Long gameId) {

    List<Building> buildings = buildingRepository.findByGameId(gameId);
    return buildings == null || buildings.isEmpty();
  }

  private Building getBuildingByPosition(Position position, Long gameId) {
    return buildingRepository.findByGameIdAndPosition(gameId, position);
  }
}
