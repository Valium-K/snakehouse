package dev.valium.snakehouse.module.game;

import dev.valium.snakehouse.module.game.score.exception.NoSuchGameException;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public enum Title {
    SNAKE_NORMAL, SNAKE_HARD;

    public static void checkTitle(Title title) {
        if(!List.of(Title.values()).contains(title)) {
            throw new NoSuchGameException(title);
        }
    }
}
