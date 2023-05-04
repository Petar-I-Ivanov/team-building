package com.github.teambuilding.repositories;

import com.github.teambuilding.models.Game;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GameRepository {

  private EntityManager entityManager;

  public GameRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Game findById(Long gameId) {
    return entityManager.find(Game.class, gameId);
  }

  public Game save(Game game) {

    if (game.getId() != null) {
      return entityManager.merge(game);
    }

    entityManager.persist(game);
    return game;
  }
}
