package com.github.teambuilding.hero.model;

import com.github.teambuilding.game.Game;
import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Hero {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(length = 1, nullable = false)
  protected String sign;

  @Column(name = "row_location", nullable = false)
  protected byte rowLocation;

  @Column(name = "col_location", nullable = false)
  protected byte colLocation;

  @Column(name = "order_position", nullable = false)
  protected byte orderPosition;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  protected Game game;

  protected Hero(String sign, Position location, int orderPosition) {

    this.sign = sign;
    this.rowLocation = (byte) location.getRow();
    this.colLocation = (byte) location.getCol();
    this.orderPosition = (byte) orderPosition;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position position) {
    this.rowLocation = (byte) position.getRow();
    this.colLocation = (byte) position.getCol();
  }
}
