package com.github.teambuilding.repositories;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.GameStatusEnum;
import java.time.LocalDate;
import java.util.List;
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

  public List<Game> findOngoingGamesBefore(LocalDate date) {

    String jpql = "SELECT g FROM Game g WHERE g.date <= ?1 AND g.status = ?2";

    return entityManager.createQuery(jpql, Game.class).setParameter(1, date)
        .setParameter(2, GameStatusEnum.ONGOING).getResultList();
  }

  public Game save(Game game) {

    if (game.getId() != null) {
      return entityManager.merge(game);
    }

    entityManager.persist(game);
    return game;
  }
}
