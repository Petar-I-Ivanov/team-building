package com.github.teambuilding.building.service;

import com.github.teambuilding.building.model.Building;
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

  public List<Building> getByGameId(Long gameId) {

    return entityManager
        .createQuery("SELECT b FROM Building b WHERE b.game.id = ?1", Building.class)
        .setParameter(1, gameId).getResultList();
  }

  public Building getByGameIdAndPosition(Long gameId, Position position) {

    try {

      return entityManager.createQuery(
          "SELECT b FROM Building b WHERE b.game.id = ?1 AND b.rowLocation = ?2 AND b.colLocation = ?3",
          Building.class).setParameter(1, gameId).setParameter(2, (byte) position.getRow())
          .setParameter(3, (byte) position.getCol()).getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public List<Building> getByGameIdAndSign(Long gameId, String sign) {

    try {

      return entityManager
          .createQuery("SELECT b FROM Building b WHERE b.game.id = ?1 AND b.sign = ?2",
              Building.class)
          .setParameter(1, gameId).setParameter(2, sign).getResultList();
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
}
