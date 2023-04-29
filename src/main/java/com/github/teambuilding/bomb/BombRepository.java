package com.github.teambuilding.bomb;

import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BombRepository {

  private static Long count = 1L;
  private List<Bomb> bombs;

  public BombRepository() {
    this.bombs = new ArrayList<>();
  }

  public List<Bomb> findByGameId(Long gameId) {

    List<Bomb> bombsToReturn = new ArrayList<>();

    for (Bomb bomb : bombs) {
      if (bomb.getGameId().equals(gameId)) {
        bombsToReturn.add(bomb);
      }
    }

    return bombsToReturn;
  }

  public Bomb findByGameIdAndPosition(Long gameId, Position position) {

    for (Bomb bomb : bombs) {
      if (bomb.getGameId().equals(gameId)
          && Position.arePositionsEqual(bomb.getLocation(), position)) {
        return bomb;
      }
    }

    return null;
  }

  public void save(Bomb bomb) {

    if (bomb.getId() != null) {

      deleteById(bomb.getId());
      bombs.add(bomb);
      return;
    }

    bomb.setId(count++);
    bombs.add(bomb);
  }

  public void deleteById(Long bombId) {

    for (Bomb bomb : bombs) {
      if (bomb.getId().equals(bombId)) {
        bombs.remove(bomb);
        return;
      }
    }
  }
}
