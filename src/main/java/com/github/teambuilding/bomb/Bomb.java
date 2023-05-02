package com.github.teambuilding.bomb;

import com.github.teambuilding.game.Game;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Bomb {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 1, nullable = false)
  private String sign;

  @Column(name = "turn_placed", nullable = false)
  private short turnPlaced;

  @Column(name = "row_location", nullable = false)
  private byte rowLocation;

  @Column(name = "col_location", nullable = false)
  private byte colLocation;

  @ManyToOne
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  public Bomb() {
    this.sign = Constants.BOMB;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position position) {
    this.rowLocation = (byte) position.getRow();
    this.colLocation = (byte) position.getCol();
  }
}
