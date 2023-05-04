package com.github.teambuilding.services.guard;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.Guard;
import com.github.teambuilding.utility.Position;

public interface GuardService {

  void generateGuard(Game game);

  Guard getAllForGameId(Long gameId);

  void move(Long gameId, short turn);

  void kill(Long gameId, short turn);

  boolean isGuardAtPosition(Long gameId, Position position);

  void deleteAllWhereGameId(Long gameId);
}
