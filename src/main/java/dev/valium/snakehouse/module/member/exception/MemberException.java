package dev.valium.snakehouse.module.member.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberException extends RuntimeException {
    public MemberException() {
        super();
    }

    public MemberException(String message) {
        super(message);
    }

    public MemberException(Long id) {
        String s = id + "에 해당하는 맴버가 없습니다.";
        log.error(s);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException(Throwable cause) {
        super(cause);
    }

    protected MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
