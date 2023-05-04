package com.github.teambuilding.repositories;

import com.github.teambuilding.models.hero.Hero;
import com.github.teambuilding.utility.Position;
import java.util.Comparator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class HeroRepository {

  private EntityManager entityManager;

  public HeroRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Hero> findByGameId(Long gameId) {

    String jpql = "SELECT h FROM Hero h WHERE h.game.id = ?1";
    List<Hero> heroes =
        entityManager.createQuery(jpql, Hero.class).setParameter(1, gameId).getResultList();

    heroes.sort(Comparator.comparing(Hero::getOrderPosition));
    return heroes;
  }

  public Hero findByGameIdAndOrderPositionOne(Long gameId) {

    String jpql = "SELECT h FROM Hero h WHERE h.game.id = ?1 AND h.orderPosition = 1";
    return entityManager.createQuery(jpql, Hero.class).setParameter(1, gameId).getSingleResult();
  }

  public Hero findByGameIdAndPosition(Long gameId, Position position) {

    String jpql =
        "SELECT h FROM Hero h WHERE h.game.id = ?1 AND h.rowLocation = ?2 AND h.colLocation = ?3";

    try {

      return entityManager.createQuery(jpql, Hero.class).setParameter(1, gameId)
          .setParameter(2, (byte) position.getRow()).setParameter(3, (byte) position.getCol())
          .getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public Hero findByGameIdAndSign(Long gameId, String sign) {

    String jpql = "SELECT h FROM Hero h WHERE h.game.id = ?1 AND h.sign = ?2";

    try {

      return entityManager.createQuery(jpql, Hero.class).setParameter(1, gameId)
          .setParameter(2, sign).getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public void save(Hero hero) {

    if (hero.getId() != null) {
      entityManager.merge(hero);
      return;
    }

    entityManager.persist(hero);
  }

  public void save(List<Hero> heroes) {

    for (Hero hero : heroes) {
      save(hero);
    }
  }

  public void delete(Hero hero) {
    entityManager.remove(entityManager.find(Hero.class, hero.getId()));
  }

  public void deleteWhereGameId(Long gameId) {

    String jpql = "DELETE FROM Hero h WHERE h.game.id = ?1";
    entityManager.createQuery(jpql).setParameter(1, gameId).executeUpdate();
  }
}
