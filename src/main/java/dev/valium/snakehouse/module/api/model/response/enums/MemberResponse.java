package dev.valium.snakehouse.module.api.model.response.enums;

public enum MemberResponse {
    NO_SUCH_MEMBER_ID(-1000, "id에 해당하는 맴버를 찾을 수 없습니다."),
    DOES_NOT_MATCHES_NAME(-1001, "id와 name이 일치하지 않습니다."),
    LOGIN_ERROR(-1002, "ID가 존재하지 않거나 password가 일치하지 않습니다."),
    DUPLICATED_MEMBER(-1003, "이미 존재하는 회원입니다.");

    private final int code;
    private final String msg;

    MemberResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
}
