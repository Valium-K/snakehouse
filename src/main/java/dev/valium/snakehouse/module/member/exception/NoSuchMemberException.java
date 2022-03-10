package dev.valium.snakehouse.module.member.exception;


public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException() {
        super();
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException(Long id) {
        super("id: " + id + " 에 해당하는 맴버가 없습니다.");
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }

    protected NoSuchMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
