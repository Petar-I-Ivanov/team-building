package com.github.teambuilding.services.building;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.utility.Position;
import java.util.List;

public interface BuildingService {

  void generateBuildings(Game game);

  List<Building> getAllForGameId(Long gameId);

  boolean isPositionBuilding(Position position, Long gameId);

  boolean isEntryPossible(Position position, Long gameId);

  void explodePosition(Position position, Long gameId);

  boolean areBuildingsDestroyed(Long gameId);
}
