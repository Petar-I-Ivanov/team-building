package com.github.teambuilding.building.model;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class BigBuilding extends Building {

  public BigBuilding() {
    super(Constants.BIG_BUILDING, Constants.BIG_B_MAX_ROW, Constants.BIG_B_MAX_COL);
  }

  public BigBuilding(Position position) {
    super(Constants.BIG_BUILDING, Constants.BIG_B_MAX_ROW, Constants.BIG_B_MAX_COL, position);
  }
}
