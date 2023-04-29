package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BuildingRepository {

  private static Long count = 1L;
  private List<Building> buildings;

  public BuildingRepository() {
    buildings = new ArrayList<>();
  }

  public List<Building> getByGameId(Long gameId) {

    List<Building> buildingsToReturn = new ArrayList<>();

    for (Building building : buildings) {
      if (building.getGameId().equals(gameId)) {
        buildingsToReturn.add(building);
      }
    }

    return buildingsToReturn;
  }

  public Building getByGameIdAndPosition(Long gameId, Position position) {

    for (Building building : getByGameId(gameId)) {
      for (Position location : building.getLocations()) {
        if (Position.arePositionsEqual(location, position)) {
          return building;
        }
      }
    }

    return null;
  }

  public void save(Building building) {

    if (building.getId() != null) {
      deleteById(building.getId());
      buildings.add(building);
      return;
    }

    building.setId(count++);
    buildings.add(building);
  }

  public void save(List<Building> buildings) {

    for (Building building : buildings) {
      save(building);
    }
  }

  public void deleteById(Long buildingId) {

    for (Building building : buildings) {
      if (building.getId().equals(buildingId)) {
        buildings.remove(building);
        return;
      }
    }
  }
}
