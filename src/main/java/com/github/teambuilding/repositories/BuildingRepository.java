package com.github.teambuilding.repositories;

import com.github.teambuilding.models.building.Building;
import com.github.teambuilding.utility.Position;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class BuildingRepository {

  private EntityManager entityManager;

  public BuildingRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Building> findByGameId(Long gameId) {

    String jpql = "SELECT b FROM Building b WHERE b.game.id = ?1";
    return entityManager.createQuery(jpql, Building.class).setParameter(1, gameId).getResultList();
  }

  public List<Building> findByGameIdAndDestroyedFalse(Long gameId) {

    String jpql = "SELECT b FROM Building b WHERE b.game.id = ?1 AND b.isExploded = false";
    return entityManager.createQuery(jpql, Building.class).setParameter(1, gameId).getResultList();
  }

  public Building findByGameIdAndPosition(Long gameId, Position position) {

    String jpql =
        "SELECT b FROM Building b WHERE b.game.id = ?1 AND b.rowLocation = ?2 AND b.colLocation = ?3";

    try {

      return entityManager.createQuery(jpql, Building.class).setParameter(1, gameId)
          .setParameter(2, (byte) position.getRow()).setParameter(3, (byte) position.getCol())
          .getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public List<Building> findByGameIdAndSign(Long gameId, String sign) {

    String jpql = "SELECT b FROM Building b WHERE b.game.id = ?1 AND b.sign = ?2";

    try {

      return entityManager.createQuery(jpql, Building.class).setParameter(1, gameId)
          .setParameter(2, sign).getResultList();
    }

    catch (Exception e) {
      return null;
    }
  }

  public void save(Building building) {

    if (building.getId() != null) {
      entityManager.merge(building);
      return;
    }

    entityManager.persist(building);
  }

  public void save(List<Building> buildings) {

    for (Building building : buildings) {
      save(building);
    }
  }

  public void delete(Building building) {
    entityManager.remove(entityManager.find(Building.class, building.getId()));
  }

  public void delete(List<Building> buildings) {

    for (Building building : buildings) {
      delete(building);
    }
  }

  public void deleteWhereGameId(Long gameId) {

    String jpql = "DELETE FROM Building b WHERE b.game.id = ?1";
    entityManager.createQuery(jpql).setParameter(1, gameId).executeUpdate();
  }
}
