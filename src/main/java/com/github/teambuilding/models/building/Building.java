package com.github.teambuilding.models.building;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.GameboardObject;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Building extends GameboardObject {

  @Transient
  private final byte HEIGHT;

  @Transient
  private final byte WIDTH;

  @Column(name = "is_entry_possible", nullable = false)
  private boolean isEntryPossible;

  @Column(name = "is_exploded", nullable = false)
  private boolean isExploded;

  @ManyToOne
  @JoinColumn(name = "game_id")
  protected Game game;

  public Building() {
    this.HEIGHT = 0;
    this.WIDTH = 0;
  }

  protected Building(String sign, byte height, byte width) {
    this.sign = sign;

    this.HEIGHT = height;
    this.WIDTH = width;

    this.isEntryPossible = true;
    this.isExploded = false;
  }

  protected Building(String sign, byte height, byte width, Position startingPosition) {
    this.sign = sign;

    this.HEIGHT = height;
    this.WIDTH = width;

    this.isEntryPossible = true;
    this.isExploded = false;

    if (canSetBuilding(startingPosition)) {
      this.rowLocation = (byte) startingPosition.getRow();
      this.colLocation = (byte) startingPosition.getCol();
    }
  }

  private boolean canSetBuilding(Position position) {
    return willBuildingNotBeOnBorders(position) && willBuildingNotBeNearHearoes(position);
  }

  private boolean willBuildingNotBeOnBorders(Position position) {

    return (position.getRow() > 0
        && position.getRow() < Constants.GAMEBOARD_MAX_ROW - 1 - this.HEIGHT)
        && (position.getCol() > 0
            && position.getCol() < Constants.GAMEBOARD_MAX_COL - 1 - this.WIDTH);
  }

  private boolean willBuildingNotBeNearHearoes(Position position) {

    return !((position.getRow() + this.HEIGHT > 8) && (position.getCol() + this.WIDTH > 4));
  }
}
