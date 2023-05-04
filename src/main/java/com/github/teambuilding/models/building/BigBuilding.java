package com.github.teambuilding.models.building;

import com.github.teambuilding.utility.Constants;
import javax.persistence.Entity;

@Entity
public class BigBuilding extends Building {

  public BigBuilding() {
    super(Constants.BIG_BUILDING, Constants.BIG_B_MAX_ROW, Constants.BIG_B_MAX_COL);
  }
}
