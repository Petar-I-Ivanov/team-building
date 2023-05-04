package com.github.teambuilding.utility;

public class Constants {

  // ============== GAMEBOARD SIGNS ================
  public static final String EMPTY_POSITION = "X";
  public static final String SMALL_BUILDING = "S";
  public static final String MEDIUM_BUILDING = "M";
  public static final String BIG_BUILDING = "L";
  public static final String TANK_HERO = "1";
  public static final String SNIPER_HERO = "2";
  public static final String SPY_HERO = "3";
  public static final String SABOTEUR_HERO = "4";
  public static final String GUARD = "G";
  public static final String BOMB = "B";

  // ============== INPUT ACTIONS ==================
  public static final char FORWARD_MOVE = 'w';
  public static final char BACK_MOVE = 's';
  public static final char LEFT_MOVE = 'a';
  public static final char RIGHT_MOVE = 'd';
  public static final char HEROES_SWAP = 'c';
  public static final char SPECIAL_ABILITY = 'f';

  public static final char NULL = '\0';

  // ============== OBJECTS BORDERS =============
  public static final int GAMEBOARD_MAX_ROW = 15;
  public static final int GAMEBOARD_MAX_COL = 15;

  public static final byte SMALL_B_MAX_ROW = 2;
  public static final byte SMALL_B_MAX_COL = 2;

  public static final byte MEDIUM_B_MAX_ROW = 2;
  public static final byte MEDIUM_B_MAX_COL = 3;

  public static final byte BIG_B_MAX_ROW = 3;
  public static final byte BIG_B_MAX_COL = 3;

  public static final byte SPACE_BETWEEN_BUILDINGS = 3;

  public static boolean isCharMovementAction(char value) {

    return value == FORWARD_MOVE || value == BACK_MOVE || value == LEFT_MOVE || value == RIGHT_MOVE;
  }

  public static boolean isCharHeroesSwapAction(char value) {
    return value == HEROES_SWAP;
  }

  public static boolean isCharSpecialAbilityAction(char value) {
    return value == SPECIAL_ABILITY;
  }

  public static boolean isCharHeroSign(char value) {

    String stringValue = String.valueOf(value);

    return stringValue.equals(TANK_HERO) || stringValue.equals(SNIPER_HERO)
        || stringValue.equals(SPY_HERO) || stringValue.equals(SABOTEUR_HERO);
  }
}
