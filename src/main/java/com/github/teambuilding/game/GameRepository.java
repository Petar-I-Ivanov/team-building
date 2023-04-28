package com.github.teambuilding.game;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GameRepository implements PanacheRepository<Game> {

  public void save(Game game) {

    if (game.getId() != null) {
      getEntityManager().merge(game);
      return;
    }

    getEntityManager().persist(game);
  }
}
