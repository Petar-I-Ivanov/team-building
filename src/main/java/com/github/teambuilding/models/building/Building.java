package com.github.teambuilding.models.building;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.GameboardObject;
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
}
