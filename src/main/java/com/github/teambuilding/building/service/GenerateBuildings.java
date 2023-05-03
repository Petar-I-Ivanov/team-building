package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.BigBuilding;
import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.building.model.MediumBuilding;
import com.github.teambuilding.building.model.SmallBuilding;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;

public class GenerateBuildings {

  public static List<Building> generate() {

    List<Building> buildings = new ArrayList<>();

    List<Building> startingBuildingPositions = getStartingPositions();

    while (startingBuildingPositions == null) {
      startingBuildingPositions = getStartingPositions();
    }

    for (Building startingPositionBuilding : startingBuildingPositions) {

      for (Building building : setBuildingPositions(startingPositionBuilding)) {
        buildings.add(building);
      }
    }

    return buildings;
  }

  private static List<Building> getStartingPositions() {

    SmallBuilding smallBuildingSP = getSmallBuildingStartingPosition();
    MediumBuilding mediumBuildingSP = getMediumBuildingStartingPosition();
    BigBuilding bigBuildingSP = getBigBuildingStartingPosition();

    if (isSpaceBetweenBuildingsIncompleate(smallBuildingSP, mediumBuildingSP, bigBuildingSP)) {
      return null;
    }

    List<Building> buildings = new ArrayList<>();
    buildings.add(smallBuildingSP);
    buildings.add(mediumBuildingSP);
    buildings.add(bigBuildingSP);

    return buildings;
  }

  private static SmallBuilding getSmallBuildingStartingPosition() {

    SmallBuilding smallB = new SmallBuilding(new Position());

    while (Position.arePositionsEqual(new Position(0, 0), smallB.getLocation())) {
      smallB = new SmallBuilding(new Position());
    }

    return smallB;
  }

  private static MediumBuilding getMediumBuildingStartingPosition() {

    MediumBuilding mediumB = new MediumBuilding(new Position());

    while (Position.arePositionsEqual(new Position(0, 0), mediumB.getLocation())) {
      mediumB = new MediumBuilding(new Position());
    }

    return mediumB;
  }

  private static BigBuilding getBigBuildingStartingPosition() {

    BigBuilding bigB = new BigBuilding(new Position());

    while (Position.arePositionsEqual(new Position(0, 0), bigB.getLocation())) {
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

    int rowOfBuildingOne = buildingOne.getLocation().getRow();
    int rowOfBuildingTwo = buildingTwo.getLocation().getRow();

    int height =
        (rowOfBuildingOne < rowOfBuildingTwo) ? buildingOne.getHEIGHT() : buildingTwo.getHEIGHT();

    return height + Constants.SPACE_BETWEEN_BUILDINGS;
  }

  private static int getExpectedColDifference(Building buildingOne, Building buildingTwo) {

    int colOfBuildingOne = buildingOne.getLocation().getCol();
    int colOfBuildingTwo = buildingTwo.getLocation().getCol();

    int width =
        (colOfBuildingOne < colOfBuildingTwo) ? buildingOne.getWIDTH() : buildingTwo.getWIDTH();

    return width + Constants.SPACE_BETWEEN_BUILDINGS;
  }

  private static int getActualRowDifference(Building buildingOne, Building buildingTwo) {
    return Math.abs(buildingOne.getLocation().getRow() - buildingTwo.getLocation().getRow());
  }

  private static int getActualColDifference(Building buildingOne, Building buildingTwo) {
    return Math.abs(buildingOne.getLocation().getCol() - buildingTwo.getLocation().getCol());
  }

  private static List<Building> setBuildingPositions(Building startingPositionBuilding) {

    List<Building> building = new ArrayList<>();
    Position startingPosition = startingPositionBuilding.getLocation();
    Position endPosition =
        new Position(startingPosition.getRow() + startingPositionBuilding.getHEIGHT(),
            startingPosition.getCol() + startingPositionBuilding.getWIDTH());

    for (int row = startingPosition.getRow(); row < endPosition.getRow(); row++) {
      for (int col = startingPosition.getCol(); col < endPosition.getCol(); col++) {

        Building buildingToAdd =
            setBuildingPosition(startingPositionBuilding, new Position(row, col));

        if (buildingToAdd.getSign().equals(Constants.MEDIUM_BUILDING)
            && (col != startingPosition.getCol() && col != endPosition.getCol())) {
          buildingToAdd.setEntryPossible(false);
        }

        building.add(buildingToAdd);
      }
    }

    return building;
  }

  private static Building setBuildingPosition(Building building, Position position) {

    Building buildingToSet = switch (building.getSign()) {
      case Constants.SMALL_BUILDING -> new SmallBuilding();
      case Constants.MEDIUM_BUILDING -> new MediumBuilding();
      case Constants.BIG_BUILDING -> new BigBuilding();
      default -> throw new IllegalArgumentException(
          "Unexpected building sign type at building setting");
    };

    buildingToSet.setLocation(position);
    return buildingToSet;
  }
}
