package com.github.teambuilding.schedule;

import com.github.teambuilding.services.GameService;
import io.quarkus.scheduler.Scheduled;
import java.time.LocalDate;
import javax.inject.Singleton;

@Singleton
public class GameboardObjectsCleanup {

  private static final byte DAYS_BEFORE_TODAY_TO_CLEAN = 15;
  private static final String EVERY_MIDNIGHT_CRON = "0 0 0 * * ?";

  private GameService gameService;

  public GameboardObjectsCleanup(GameService gameService) {
    this.gameService = gameService;
  }

  @Scheduled(cron = EVERY_MIDNIGHT_CRON)
  public void cleanup() {

    LocalDate dayBeforeFifteenDays = LocalDate.now().minusDays(DAYS_BEFORE_TODAY_TO_CLEAN);
    gameService.oldGameboardObjectsClean(dayBeforeFifteenDays);
  }
}
