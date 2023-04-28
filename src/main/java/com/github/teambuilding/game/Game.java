package com.github.teambuilding.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Game {

  private static Long counter = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private short turn;

  @Enumerated(EnumType.STRING)
  private GameStatusEnum status;

  public Game() {

    this.id = counter++;
    this.turn = 0;
    this.status = GameStatusEnum.ONGOING;
  }
}
