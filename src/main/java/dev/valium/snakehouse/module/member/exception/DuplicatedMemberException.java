package dev.valium.snakehouse.module.member.exception;

public class DuplicatedMemberException extends RuntimeException {
    public DuplicatedMemberException() {
        super();
    }

    public DuplicatedMemberException(String message) {
        super(message);
    }

    public DuplicatedMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedMemberException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
