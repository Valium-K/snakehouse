package dev.valium.snakehouse.module.game.score.exception;

import dev.valium.snakehouse.module.game.Title;

public class NoPlayHistoryException extends RuntimeException {
    public NoPlayHistoryException() {
        super();
    }

    public NoPlayHistoryException(String message) {
        super(message);
    }

    public NoPlayHistoryException(Title title) {
        super(title.toString() + "에 대한 플레이 기록이 없습니다.");
    }

    public NoPlayHistoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlayHistoryException(Throwable cause) {
        super(cause);
    }

    protected NoPlayHistoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
