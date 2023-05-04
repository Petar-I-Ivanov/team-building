package com.github.teambuilding.services.hero;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.hero.Hero;
import com.github.teambuilding.utility.Position;
import java.util.List;

public interface HeroService {

  void generateHeroes(Game game);

  List<Hero> getAllForGameId(Long gameId);

  void makeAction(Long gameId, char command, char heroPick, short turn);

  void kill(Long gameId);

  void killAtPosition(Long gameId, Position position);

  boolean isSaboteurKilled(Long gameId);

  boolean isHeroAtPosition(Long gameId, Position position);

  void deleteAllWhereGameId(Long gameId);
}
