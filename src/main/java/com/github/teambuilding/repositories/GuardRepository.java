package com.github.teambuilding.repositories;

import com.github.teambuilding.models.Guard;
import com.github.teambuilding.utility.Position;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class GuardRepository {

  private EntityManager entityManager;

  public GuardRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Guard findByGameId(Long gameId) {

    String jpql = "SELECT g FROM Guard g WHERE g.game.id = ?1";
    return entityManager.createQuery(jpql, Guard.class).setParameter(1, gameId).getSingleResult();

  }

  public Guard findByGameIdAndPosition(Long gameId, Position position) {

    String jpql =
        "SELECT g FROM Guard g WHERE g.game.id = ?1 AND g.rowLocation = ?2 AND g.colLocation = ?3";

    try {

      return entityManager.createQuery(jpql, Guard.class).setParameter(1, gameId)
          .setParameter(2, (byte) position.getRow()).setParameter(3, (byte) position.getCol())
          .getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public void save(Guard guard) {

    if (guard.getId() != null) {
      entityManager.merge(guard);
      return;
    }

    entityManager.persist(guard);
  }

  public void delete(Guard guard) {
    entityManager.remove(entityManager.find(Guard.class, guard.getId()));
  }

  public void deleteWhereGameId(Long gameId) {

    String jpql = "DELETE FROM Guard g WHERE g.game.id = ?1";
    entityManager.createQuery(jpql).setParameter(1, gameId).executeUpdate();
  }
}
