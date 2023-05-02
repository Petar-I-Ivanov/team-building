package com.github.teambuilding.guard;

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

    return entityManager.createQuery("SELECT g FROM Guard g WHERE g.game.id = ?1", Guard.class)
        .setParameter(1, gameId).getSingleResult();

  }

  public Guard findByGameIdAndPosition(Long gameId, Position position) {

    try {

      return entityManager.createQuery(
          "SELECT g FROM Guard g WHERE g.game.id = ?1 AND g.rowLocation = ?2 AND g.colLocation = ?3",
          Guard.class).setParameter(1, gameId).setParameter(2, (byte) position.getRow())
          .setParameter(3, (byte) position.getCol()).getSingleResult();
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
}
