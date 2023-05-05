package com.github.teambuilding.repositories;

import com.github.teambuilding.models.Statistic;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class StatisticRepository {

  private EntityManager entityManager;

  public StatisticRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Statistic findByGameId(Long gameId) {

    String jpql = "SELECT s FROM Statistic s WHERE s.game.id = ?1";
    return entityManager.createQuery(jpql, Statistic.class).setParameter(1, gameId)
        .getSingleResult();
  }

  public List<Statistic> findTopTenFastestPlays() {

    String jpql =
        "SELECT s FROM Statistic s WHERE s.endTime IS NOT NULL AND s.startTime IS NOT NULL ORDER BY s.endTime - s.startTime ASC";

    return entityManager.createQuery(jpql, Statistic.class).getResultList();
  }

  public List<Statistic> findAll() {

    List<Statistic> statistics;
    String jpql = "SELECT s FROM Statistic s";
    statistics = entityManager.createQuery(jpql, Statistic.class).getResultList();

    for (Statistic statistic : statistics) {
      System.out.println("Test " + statistic.getId());
    }

    return statistics;
  }

  public void save(Statistic statistic) {

    if (statistic.getId() != null) {
      entityManager.merge(statistic);
      return;
    }

    entityManager.persist(statistic);
  }
}
