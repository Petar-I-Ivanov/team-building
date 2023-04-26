package com.github.teambuilding.game;

import lombok.Data;

@Data
public class Game {

  private static int counter = 1;

  private long id;
  private int turn;
  private GameStatusEnum status;

  public Game() {

    this.id = counter++;
    this.turn = 0;
    this.status = GameStatusEnum.ONGOING;
  }
}
