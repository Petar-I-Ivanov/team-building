package com.github.teambuilding.models.hero;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.GameboardObject;
import com.github.teambuilding.utility.Position;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Hero extends GameboardObject {

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
}
