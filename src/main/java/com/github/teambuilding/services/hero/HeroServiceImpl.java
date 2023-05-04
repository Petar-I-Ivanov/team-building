package com.github.teambuilding.services.hero;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.hero.Hero;
import com.github.teambuilding.models.hero.SaboteurHero;
import com.github.teambuilding.models.hero.SniperHero;
import com.github.teambuilding.models.hero.SpyHero;
import com.github.teambuilding.models.hero.TankHero;
import com.github.teambuilding.repositories.HeroRepository;
import com.github.teambuilding.services.bomb.BombService;
import com.github.teambuilding.utility.Constants;
import com.github.teambuilding.utility.Position;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HeroServiceImpl implements HeroService {

  private HeroRepository heroRepository;

  private HeroKillService heroKillService;
  private HeroMoveService heroMoveService;
  private HeroSwapOrder heroSwapOrder;

  private BombService bombService;

  public HeroServiceImpl(HeroRepository heroRepository, HeroKillService heroKillService,
      HeroMoveService heroMoveService, HeroSwapOrder heroSwapOrder) {

    this.heroRepository = heroRepository;

    this.heroKillService = heroKillService;
    this.heroMoveService = heroMoveService;
    this.heroSwapOrder = heroSwapOrder;
  }

  public void setBombService(BombService bombService) {
    this.bombService = bombService;
  }

  @Override
  public void generateHeroes(Game game) {

    List<Hero> heroes = new ArrayList<>();

    heroes.add(new TankHero());
    heroes.add(new SniperHero());
    heroes.add(new SpyHero());
    heroes.add(new SaboteurHero());

    for (Hero hero : heroes) {
      hero.setGame(game);
    }

    heroRepository.save(heroes);

    game.setHeroes(heroes);
  }

  @Override
  public List<Hero> getAllForGameId(Long gameId) {
    return heroRepository.findByGameId(gameId);
  }

  @Override
  public void makeAction(Long gameId, char command, char heroPick, short turn) {

    if (Constants.isCharMovementAction(command)) {
      heroMoveService.move(gameId, command);
    }

    if (Constants.isCharSpecialAbilityAction(command)) {
      setExplode(gameId, turn);
    }

    if (Constants.isCharHeroesSwapAction(command)) {
      heroSwapOrder.swapHero(gameId, heroPick);
    }
  }

  @Override
  public void kill(Long gameId) {
    heroKillService.kill(gameId);
  }

  @Override
  public void killAtPosition(Long gameId, Position position) {
    heroKillService.killAtPosition(gameId, position);
  }

  @Override
  public boolean isSaboteurKilled(Long gameId) {
    return heroRepository.findByGameIdAndSign(gameId, Constants.SABOTEUR_HERO) == null;
  }

  @Override
  public boolean isHeroAtPosition(Long gameId, Position position) {
    return heroRepository.findByGameIdAndPosition(gameId, position) != null;
  }

  @Override
  public void deleteAllWhereGameId(Long gameId) {
    heroRepository.deleteWhereGameId(gameId);
  }

  private void setExplode(Long gameId, short turn) {

    SaboteurHero saboteur =
        (SaboteurHero) heroRepository.findByGameIdAndSign(gameId, Constants.SABOTEUR_HERO);

    if (saboteur != null && saboteur.getOrderPosition() == 1) {
      bombService.addBomb(saboteur.getGame(), turn, saboteur.getLocation());
      return;
    }

    throw new IllegalArgumentException("Saboteur isn't first to place bomb!");
  }
}
