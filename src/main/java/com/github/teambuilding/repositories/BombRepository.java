package com.github.teambuilding.repositories;

import com.github.teambuilding.models.Bomb;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class BombRepository {

  private EntityManager entityManager;

  public BombRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Bomb> findByGameId(Long gameId) {

    String jpql = "SELECT b FROM Bomb b WHERE b.game.id = ?1";
    return entityManager.createQuery(jpql, Bomb.class).setParameter(1, gameId).getResultList();
  }

  public Bomb findByGameIdAndPosition(Long gameId, Position position) {

    String jpql =
        "SELECT b FROM Bomb b WHERE b.game.id = ?1 AND b.rowLocation = ?2 AND b.colLocation = ?3";

    try {

      return entityManager.createQuery(jpql, Bomb.class).setParameter(1, gameId)
          .setParameter(2, (byte) position.getRow()).setParameter(3, (byte) position.getCol())
          .getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public void save(Bomb bomb) {

    if (bomb.getId() != null) {
      entityManager.merge(bomb);
    }

    entityManager.persist(bomb);
  }

  public void delete(Bomb bomb) {
    entityManager.remove(entityManager.find(Bomb.class, bomb.getId()));
  }

  public void deleteWhereGameId(Long gameId) {

    String jpql = "DELETE FROM Bomb b WHERE b.game.id = ?1";
    entityManager.createQuery(jpql).setParameter(1, gameId).executeUpdate();
  }
}
