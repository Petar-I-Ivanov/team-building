package com.github.teambuilding.guard;

import com.github.teambuilding.game.Game;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Guard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 1, nullable = false)
  private String sign;

  @Column(name = "row_location", nullable = false)
  private byte rowLocation;

  @Column(name = "col_location", nullable = false)
  private byte colLocation;

  @Column(name = "is_sleep", nullable = false)
  private boolean isSleep;

  @Column(name = "turn_set_to_sleep", nullable = false)
  private short turnSetToSleep;

  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Guard() {
    this.sign = Constants.GUARD;
    this.rowLocation = 0;
    this.colLocation = 0;
    this.isSleep = false;
    this.turnSetToSleep = 0;
  }

  public Position getLocation() {
    return new Position(this.rowLocation, this.colLocation);
  }

  public void setLocation(Position position) {
    this.rowLocation = (byte) position.getRow();
    this.colLocation = (byte) position.getCol();
  }
}
