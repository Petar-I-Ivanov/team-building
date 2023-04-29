package com.github.teambuilding.guard;

import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GuardRepository {

  private static Long count = 1L;
  private List<Guard> guards;

  public GuardRepository() {
    this.guards = new ArrayList<>();
  }

  public Guard findByGameId(Long gameId) {

    for (Guard guard : guards) {
      if (guard.getGameId().equals(gameId)) {
        return guard;
      }
    }

    return null;
  }

  public Guard findByGameIdAndPosition(Long gameId, Position position) {

    for (Guard guard : guards) {
      if (guard.getGameId().equals(gameId)
          && Position.arePositionsEqual(guard.getLocation(), position)) {
        return guard;
      }
    }

    return null;
  }

  public void save(Guard guard) {

    if (guard.getId() != null) {

      deleteById(guard.getId());
      guards.add(guard);
      return;
    }

    guard.setId(count++);
    guards.add(guard);
  }

  public void deleteById(Long guardId) {

    for (Guard guard : guards) {
      if (guard.getId().equals(guardId)) {
        guards.remove(guard);
        return;
      }
    }
  }
}
