package dev.valium.snakehouse.module.api.model.response.enums;

public enum LeaderboardResponse {
    NO_PLAY_HISTORY(-3000, "게임 플레이 기록이 없습니다.");

    private final int code;
    private final String msg;

    LeaderboardResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
}
