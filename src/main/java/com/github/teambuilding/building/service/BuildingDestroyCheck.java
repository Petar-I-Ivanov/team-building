package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.utility.Constants;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BuildingDestroyCheck {

  private BuildingDestroyCheck() {}

  public static boolean isBuildingDestroyed(List<Building> building) {

    sortByRowAndCol(building);

    return switch (building.get(0).getSign()) {

      case Constants.SMALL_BUILDING -> isSmallBuildingDestroyed(building);
      case Constants.MEDIUM_BUILDING -> isMediumBuildingDestroyed(building);
      case Constants.BIG_BUILDING -> isBigBuildingDestroyed(building);

      default -> false;
    };
  }

  private static void sortByRowAndCol(List<Building> building) {

    Collections.sort(building, new Comparator<Building>() {

      @Override
      public int compare(Building buildingPositionOne, Building buildingPositionTwo) {

        if (buildingPositionOne.getRowLocation() == buildingPositionTwo.getRowLocation()) {
          return Byte.compare(buildingPositionOne.getColLocation(),
              buildingPositionTwo.getColLocation());
        }

        return Byte.compare(buildingPositionOne.getRowLocation(),
            buildingPositionTwo.getRowLocation());
      }
    });
  }

  private static boolean isSmallBuildingDestroyed(List<Building> building) {

    for (Building buildingPosition : building) {
      if (buildingPosition.isExploded()) {
        return true;
      }
    }

    return false;
  }

  private static boolean isMediumBuildingDestroyed(List<Building> building) {

    final int[] FIRST_INDEXES_TO_CHECK = {0, building.size() - 1};
    final int[] SECOND_INDEXES_TO_CHECK = {2, 3};

    return (building.get(FIRST_INDEXES_TO_CHECK[0]).isExploded()
        && building.get(FIRST_INDEXES_TO_CHECK[1]).isExploded())
        || (building.get(SECOND_INDEXES_TO_CHECK[0]).isExploded()
            && building.get(SECOND_INDEXES_TO_CHECK[1]).isExploded());
  }

  private static boolean isBigBuildingDestroyed(List<Building> building) {

    final int[] INDEXES_TO_CHECK = {0, 2, 4, 6, 8};

    for (int index : INDEXES_TO_CHECK) {
      if (!building.get(index).isExploded()) {
        return false;
      }
    }

    return true;
  }
}
