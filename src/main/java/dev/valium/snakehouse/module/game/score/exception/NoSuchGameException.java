package dev.valium.snakehouse.module.game.score.exception;

import dev.valium.snakehouse.module.game.Title;

public class NoSuchGameException extends RuntimeException {
    public NoSuchGameException() {
        super();
    }

    public NoSuchGameException(String message) {
        super(message);
    }

    public NoSuchGameException(Title title) {
        super("해당 타이틀에 해당하는 게임이 없습니다.");
    }

    public NoSuchGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchGameException(Throwable cause) {
        super(cause);
    }

    protected NoSuchGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
