package com.github.teambuilding.models.building;

import com.github.teambuilding.utility.Constants;
import javax.persistence.Entity;

@Entity
public class SmallBuilding extends Building {

  public SmallBuilding() {
    super(Constants.SMALL_BUILDING, Constants.SMALL_B_MAX_ROW, Constants.SMALL_B_MAX_COL);
  }
}
