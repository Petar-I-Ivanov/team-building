package com.github.teambuilding.models;

import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class GameboardObject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(length = 1, nullable = false)
  protected String sign;

  @Column(name = "row_location", nullable = false)
  protected byte rowLocation;

  @Column(name = "col_location", nullable = false)
  protected byte colLocation;

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position location) {

    this.rowLocation = (byte) location.getRow();
    this.colLocation = (byte) location.getCol();
  }
}
