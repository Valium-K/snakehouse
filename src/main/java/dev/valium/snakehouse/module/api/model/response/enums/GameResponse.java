package dev.valium.snakehouse.module.api.model.response.enums;

public enum GameResponse {
    NO_SUCH_GAME(-2000, "해당 게임을 찾을 수 없습니다.");

    private final int code;
    private final String msg;

    GameResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
}
