package com.github.teambuilding.services.bomb;

import com.github.teambuilding.models.Bomb;
import com.github.teambuilding.models.Game;
import com.github.teambuilding.utility.Position;
import java.util.List;

public interface BombService {

  void addBomb(Game game, short turn, Position position);

  List<Bomb> getAllForGameId(Long gameId);

  void explodeCheck(Long gameId, short turn);

  void deleteAllWhereGameId(Long gameId);
}
