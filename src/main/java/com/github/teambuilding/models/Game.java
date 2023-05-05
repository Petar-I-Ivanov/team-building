package com.github.teambuilding.models;

import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.models.hero.Hero;
import java.time.LocalDate;
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

  private LocalDate date;

  @OneToOne(mappedBy = "game")
  private Statistic statistic;

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
    this.date = LocalDate.now();
    this.bombs = new ArrayList<>();
  }
}
