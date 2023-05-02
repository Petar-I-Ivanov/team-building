package com.github.teambuilding.hero.service;

import com.github.teambuilding.hero.model.Hero;
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

    List<Hero> heroes =
        entityManager.createQuery("SELECT h FROM Hero h WHERE h.game.id = ?1", Hero.class)
            .setParameter(1, gameId).getResultList();

    heroes.sort(Comparator.comparing(Hero::getOrderPosition));
    return heroes;
  }

  public Hero findByGameIdAndPosition(Long gameId, Position position) {

    try {

      return entityManager.createQuery(
          "SELECT h FROM Hero h WHERE h.game.id = ?1 AND h.rowLocation = ?2 AND h.colLocation = ?3",
          Hero.class).setParameter(1, gameId).setParameter(2, (byte) position.getRow())
          .setParameter(3, (byte) position.getCol()).getSingleResult();
    }

    catch (Exception e) {
      return null;
    }
  }

  public Hero findByGameIdAndSign(Long gameId, String sign) {

    try {

      return entityManager
          .createQuery("SELECT h FROM Hero h WHERE h.game.id = ?1 AND h.sign = ?2", Hero.class)
          .setParameter(1, gameId).setParameter(2, sign).getSingleResult();
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
}
