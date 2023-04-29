package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.BigBuilding;
import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.building.model.MediumBuilding;
import com.github.teambuilding.building.model.SmallBuilding;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;

public class GenerateBuilding {

  private static byte BETWEEN_BUILDINGS_SPACE = 3;

  public static List<Building> generate() {

    SmallBuilding smallBuilding = getSmallBuilding();
    MediumBuilding mediumBuilding = getMediumBuilding();
    BigBuilding bigBuilding = getBigBuilding();

    if (isSpaceBetweenBuildingsIncompleate(smallBuilding, mediumBuilding, bigBuilding)) {
      return null;
    }

    List<Building> buildings = new ArrayList<>();
    buildings.add(smallBuilding);
    buildings.add(mediumBuilding);
    buildings.add(bigBuilding);

    return buildings;
  }

  private static SmallBuilding getSmallBuilding() {

    SmallBuilding smallB = new SmallBuilding(new Position());

    while (!smallB.isBuildingSet()) {
      smallB = new SmallBuilding(new Position());
    }

    return smallB;
  }

  private static MediumBuilding getMediumBuilding() {

    MediumBuilding mediumB = new MediumBuilding(new Position());

    while (!mediumB.isBuildingSet()) {
      mediumB = new MediumBuilding(new Position());
    }

    return mediumB;
  }

  private static BigBuilding getBigBuilding() {

    BigBuilding bigB = new BigBuilding(new Position());

    while (!bigB.isBuildingSet()) {
      bigB = new BigBuilding(new Position());
    }
    return bigB;
  }

  private static boolean isSpaceBetweenBuildingsIncompleate(SmallBuilding smallB,
      MediumBuilding mediumB, BigBuilding bigB) {

    return !(doTwoBuildingsHaveNeededRowOrColDifference(smallB, mediumB)
        && doTwoBuildingsHaveNeededRowOrColDifference(mediumB, bigB)
        && doTwoBuildingsHaveNeededRowOrColDifference(smallB, bigB));
  }

  private static boolean doTwoBuildingsHaveNeededRowOrColDifference(Building buildingOne,
      Building buildingTwo) {

    int expectedRowDifference = getExpectedRowDifference(buildingOne, buildingTwo);
    int expectedColDifference = getExpectedColDifference(buildingOne, buildingTwo);

    int actualRowDifference = getActualRowDifference(buildingOne, buildingTwo);
    int actualColDifference = getActualColDifference(buildingOne, buildingTwo);

    return actualRowDifference >= expectedRowDifference
        || actualColDifference >= expectedColDifference;
  }

  private static int getExpectedRowDifference(Building buildingOne, Building buildingTwo) {

    int rowOfBuildingOne = buildingOne.getStartingPosition().getRow();
    int rowOfBuildingTwo = buildingTwo.getStartingPosition().getRow();

    int height =
        (rowOfBuildingOne < rowOfBuildingTwo) ? buildingOne.getHEIGHT() : buildingTwo.getHEIGHT();

    return height + BETWEEN_BUILDINGS_SPACE;
  }

  private static int getExpectedColDifference(Building buildingOne, Building buildingTwo) {

    int colOfBuildingOne = buildingOne.getStartingPosition().getCol();
    int colOfBuildingTwo = buildingTwo.getStartingPosition().getCol();

    int width =
        (colOfBuildingOne < colOfBuildingTwo) ? buildingOne.getWIDTH() : buildingTwo.getWIDTH();

    return width + BETWEEN_BUILDINGS_SPACE;
  }

  private static int getActualRowDifference(Building buildingOne, Building buildingTwo) {

    return Math.abs(
        buildingOne.getStartingPosition().getRow() - buildingTwo.getStartingPosition().getRow());
  }

  private static int getActualColDifference(Building buildingOne, Building buildingTwo) {

    return Math.abs(
        buildingOne.getStartingPosition().getCol() - buildingTwo.getStartingPosition().getCol());
  }
}
