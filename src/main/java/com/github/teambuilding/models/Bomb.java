package com.github.teambuilding.models;

import com.github.teambuilding.utility.Constants;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bomb extends GameboardObject {

  @Column(name = "turn_placed", nullable = false)
  private short turnPlaced;

  @ManyToOne
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  public Bomb() {
    this.sign = Constants.BOMB;
  }
}
