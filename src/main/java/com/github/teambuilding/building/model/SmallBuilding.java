package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class SmallBuilding extends Building {

  public SmallBuilding() {
    super(Constants.SMALL_BUILDING, Constants.SMALL_B_MAX_ROW, Constants.SMALL_B_MAX_COL);
  }

  public SmallBuilding(Position position) {
    super(Constants.SMALL_BUILDING, Constants.SMALL_B_MAX_ROW, Constants.SMALL_B_MAX_COL, position);
  }
}
