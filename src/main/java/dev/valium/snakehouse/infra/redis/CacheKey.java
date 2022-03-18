package dev.valium.snakehouse.infra.redis;

public class CacheKey {
    public static final int DEFAULT_EXPIRE_SEC = 60; // 1 minutes

    public static final String MEMBER = "member";
    public static final int MEMBER_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String LEADERBOARD = "leaderboard";
    public static final int LEADERBOARD_EXPIRE_SEC = 60 * 10; // 10 minutes

    public static final String GAME_SCORE = "gameScore";
    public static final int GAME_SCORE_EXPIRE_SEC = 60 * 5; // 5 minutes
}