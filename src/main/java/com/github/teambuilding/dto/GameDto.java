package com.github.teambuilding.dto;

import com.github.teambuilding.models.GameStatusEnum;
import lombok.Data;

@Data
public class GameDto {

  private long id;
  private short turn;
  private GameStatusEnum status;
  private String[][] gameboard;
}
