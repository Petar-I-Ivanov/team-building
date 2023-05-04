package com.github.teambuilding.services.building.helpers;

import com.github.teambuilding.models.building.BigBuilding;
import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.models.building.MediumBuilding;
import com.github.teambuilding.models.building.SmallBuilding;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;

public class BuildingsStartingPositions {

  private BuildingsStartingPositions() {}

  public static List<Building> generateStartingPositions() {

    SmallBuilding small;
    MediumBuilding medium;
    BigBuilding big;

    do {
      small = smallBuildingSP();
      medium = mediumBuildingSP();
      big = largeBuildingSP();

    } while (BuildingStartingPositionValidator.validate(small, medium, big));

    List<Building> buildings = new ArrayList<>();

    buildings.add(small);
    buildings.add(medium);
    buildings.add(big);

    return buildings;
  }

  private static SmallBuilding smallBuildingSP() {

    Position startingPosition = generate(Constants.SMALL_B_MAX_ROW, Constants.SMALL_B_MAX_COL);

    SmallBuilding smallBuilding = new SmallBuilding();
    smallBuilding.setLocation(startingPosition);

    return smallBuilding;
  }

  private static MediumBuilding mediumBuildingSP() {

    Position startingPosition = generate(Constants.MEDIUM_B_MAX_ROW, Constants.MEDIUM_B_MAX_COL);

    MediumBuilding mediumBuilding = new MediumBuilding();
    mediumBuilding.setLocation(startingPosition);

    return mediumBuilding;
  }

  private static BigBuilding largeBuildingSP() {

    Position startingPosition = generate(Constants.BIG_B_MAX_ROW, Constants.BIG_B_MAX_COL);

    BigBuilding largeBuilding = new BigBuilding();
    largeBuilding.setLocation(startingPosition);

    return largeBuilding;
  }

  private static Position generate(int height, int width) {

    Position startingPosition = new Position();

    while (!canSetBuilding(startingPosition, height, width)) {
      startingPosition = new Position();
    }

    return startingPosition;
  }

  private static boolean canSetBuilding(Position position, int height, int width) {

    return willBuildingNotBeOnBorders(position, height, width)
        && willBuildingNotBeNearHearoes(position, height, width);
  }

  private static boolean willBuildingNotBeOnBorders(Position position, int height, int width) {

    return (position.getRow() > 0 && position.getRow() < Constants.GAMEBOARD_MAX_ROW - 1 - height)
        && (position.getCol() > 0 && position.getCol() < Constants.GAMEBOARD_MAX_COL - 1 - width);
  }

  private static boolean willBuildingNotBeNearHearoes(Position position, int height, int width) {
    return !((position.getRow() + height > 8) && (position.getCol() + width > 4));
  }
}
