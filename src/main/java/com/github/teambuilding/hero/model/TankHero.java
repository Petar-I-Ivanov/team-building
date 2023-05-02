package com.github.teambuilding.hero.model;

import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class TankHero extends Hero {

  public TankHero() {
    super("1", new Position(14, 11), 1);
  }
}
