package com.github.teambuilding.services.building;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.repositories.BuildingRepository;
import com.github.teambuilding.services.building.helpers.BuildingDestroyCheck;
import com.github.teambuilding.services.building.helpers.GenerateBuildings;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BuildingServiceImpl implements BuildingService {

  private BuildingRepository buildingRepository;

  public BuildingServiceImpl(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }

  @Override
  public void generateBuildings(Game game) {

    List<Building> buildings = GenerateBuildings.generate();

    for (Building building : buildings) {
      building.setGame(game);
    }

    buildingRepository.save(buildings);
    game.setBuildings(buildings);
  }

  @Override
  public List<Building> getAllForGameId(Long gameId) {
    return buildingRepository.findByGameIdAndDestroyedFalse(gameId);
  }

  @Override
  public boolean isPositionBuilding(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return building != null && !building.isExploded();
  }

  @Override
  public boolean isEntryPossible(Position position, Long gameId) {

    Building building = getBuildingByPosition(position, gameId);
    return building != null && building.isEntryPossible();
  }

  @Override
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

  @Override
  public boolean areBuildingsDestroyed(Long gameId) {

    List<Building> buildings = buildingRepository.findByGameId(gameId);
    return buildings == null || buildings.isEmpty();
  }

  private Building getBuildingByPosition(Position position, Long gameId) {
    return buildingRepository.findByGameIdAndPosition(gameId, position);
  }
}
