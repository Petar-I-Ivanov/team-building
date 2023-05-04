package com.github.teambuilding.models.hero;

import com.github.teambuilding.utility.Position;
import javax.persistence.Entity;

@Entity
public class SaboteurHero extends Hero {

  public SaboteurHero() {
    super("4", new Position(14, 14), 4);
  }
}