package com.github.teambuilding.services;

import com.github.teambuilding.models.Game;
import com.github.teambuilding.models.Statistic;
import com.github.teambuilding.repositories.StatisticRepository;
import java.time.LocalTime;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StatisticService {

  private StatisticRepository statisticRepository;

  public StatisticService(StatisticRepository statisticRepository) {
    this.statisticRepository = statisticRepository;
  }

  public Statistic findByGameId(Long gameId) {
    return statisticRepository.findByGameId(gameId);
  }

  public List<Statistic> findAll() {
    return statisticRepository.findAll();
  }

  public List<Statistic> findTopTenFastestPlays() {
    return statisticRepository.findTopTenFastestPlays();
  }

  public void addStatistic(Game game) {

    Statistic statistic = new Statistic();
    statistic.setGame(game);
    statisticRepository.save(statistic);

    game.setStatistic(statistic);
  }

  public void updateStatistic(Long gameId) {

    Statistic statistic = statisticRepository.findByGameId(gameId);
    statistic.setEndTime(LocalTime.now());
    statisticRepository.save(statistic);
  }
}
