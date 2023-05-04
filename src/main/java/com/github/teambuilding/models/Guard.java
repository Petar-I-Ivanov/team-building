package com.github.teambuilding.models;

import com.github.teambuilding.utility.Constants;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Guard extends GameboardObject {

  @Column(name = "is_sleep", nullable = false)
  private boolean isSleep;

  @Column(name = "turn_set_to_sleep", nullable = false)
  private short turnSetToSleep;

  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;

  public Guard() {
    this.sign = Constants.GUARD;
    this.isSleep = false;
  }
}
