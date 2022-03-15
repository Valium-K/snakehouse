package dev.valium.snakehouse.module.api.model.response.enums;

public enum AuthResponse {
    NO_AUTH(-4000, "해당 리소스에 대한 접근 권한이 없습니다."),
    ACCESS_DENIED(-4001, "보유한 권한으로 접근 할 수 없는 리소스 입니다.");

    private final int code;
    private final String msg;

    AuthResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
}