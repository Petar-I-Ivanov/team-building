package com.github.teambuilding.services.building.helpers;

import com.github.teambuilding.models.building.BigBuilding;
import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.models.building.MediumBuilding;
import com.github.teambuilding.models.building.SmallBuilding;
import com.github.teambuilding.utility.Constants;

public class BuildingStartingPositionValidator {

  private BuildingStartingPositionValidator() {}

  public static boolean validate(SmallBuilding smallB, MediumBuilding mediumB, BigBuilding bigB) {
    return isSpaceBetweenBuildingsIncompleate(smallB, mediumB, bigB);
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
}
