package com.github.teambuilding.services.building.helpers;

import com.github.teambuilding.models.building.BigBuilding;
import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.models.building.MediumBuilding;
import com.github.teambuilding.models.building.SmallBuilding;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import java.util.ArrayList;
import java.util.List;

public class GenerateBuildings {

  public static List<Building> generate() {

    List<Building> buildings = new ArrayList<>();
    List<Building> startingBuildings = BuildingsStartingPositions.generateStartingPositions();

    for (Building building : startingBuildings) {

      buildings.addAll(setTheWholeBuilding(building));
    }

    return buildings;
  }

  private static List<Building> setTheWholeBuilding(Building startingBuilding) {

    return switch (startingBuilding.getSign()) {

      case Constants.SMALL_BUILDING -> set(SmallBuilding::new, startingBuilding);
      case Constants.MEDIUM_BUILDING -> set(MediumBuilding::new, startingBuilding);
      case Constants.BIG_BUILDING -> set(BigBuilding::new, startingBuilding);

      default -> throw new IllegalArgumentException("Invalid building sign at generating it.");
    };
  }

  private static List<Building> set(Supplier<Building> supplier, Building startingBuilding) {

    int startRow = startingBuilding.getRowLocation();
    int startCol = startingBuilding.getColLocation();

    int endRow = startRow + startingBuilding.getHEIGHT();
    int endCol = startCol + startingBuilding.getWIDTH();

    List<Building> building = new ArrayList<>();

    for (int row = startRow; row < endRow; row++) {
      for (int col = startCol; col < endCol; col++) {

        Building temp = supplier.get();
        temp.setLocation((new Position(row, col)));
        building.add(temp);
      }
    }

    return building;
  }
}
