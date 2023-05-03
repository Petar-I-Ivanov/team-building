package com.github.teambuilding.game;

import com.github.teambuilding.bomb.Bomb;
import com.github.teambuilding.building.model.Building;
import com.github.teambuilding.guard.Guard;
import com.github.teambuilding.hero.model.Hero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private short turn;

  @Enumerated(EnumType.STRING)
  private GameStatusEnum status;

  @OneToMany(mappedBy = "game")
  private List<Building> buildings;

  @OneToMany(mappedBy = "game")
  private List<Hero> heroes;

  @OneToOne(mappedBy = "game")
  private Guard guard;

  @OneToMany(mappedBy = "game")
  private List<Bomb> bombs;

  public Game() {
    this.turn = 1;
    this.status = GameStatusEnum.ONGOING;
    this.bombs = new ArrayList<>();
  }
}
