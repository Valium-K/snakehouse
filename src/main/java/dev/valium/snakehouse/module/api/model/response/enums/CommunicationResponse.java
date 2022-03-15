package dev.valium.snakehouse.module.api.model.response.enums;

public enum CommunicationResponse {
    COMMUNICATION_ERROR(-5000, "통신 중 오류가 발생했습니다.");

    private final int code;
    private final String msg;

    CommunicationResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
}
