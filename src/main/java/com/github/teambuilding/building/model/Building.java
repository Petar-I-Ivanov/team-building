package com.github.teambuilding.building.model;

import com.github.teambuilding.game.Game;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Building {

  @Transient
  private final byte HEIGHT;

  @Transient
  private final byte WIDTH;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(length = 2, nullable = false)
  protected String sign;

  @Column(name = "row_location", nullable = false)
  protected byte rowLocation;

  @Column(name = "col_location", nullable = false)
  protected byte colLocation;

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

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position location) {
    this.rowLocation = (byte) location.getRow();
    this.colLocation = (byte) location.getCol();
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
