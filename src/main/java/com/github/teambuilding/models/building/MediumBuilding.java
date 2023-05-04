package com.github.teambuilding.models.building;

import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class MediumBuilding extends Building {

  public MediumBuilding() {
    super(Constants.MEDIUM_BUILDING, Constants.MEDIUM_B_MAX_ROW, Constants.MEDIUM_B_MAX_COL);
  }

  public MediumBuilding(Position position) {
    super(Constants.MEDIUM_BUILDING, Constants.MEDIUM_B_MAX_ROW, Constants.MEDIUM_B_MAX_COL,
        position);
  }
}
