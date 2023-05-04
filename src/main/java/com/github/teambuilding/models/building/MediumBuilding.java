package com.github.teambuilding.models.building;

import com.github.teambuilding.utility.Constants;
import javax.persistence.Entity;

@Entity
public class MediumBuilding extends Building {

  public MediumBuilding() {
    super(Constants.MEDIUM_BUILDING, Constants.MEDIUM_B_MAX_ROW, Constants.MEDIUM_B_MAX_COL);
  }
}
