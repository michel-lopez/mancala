package com.tealorange.mancala.model;

import lombok.Data;

@Data
public class GameRoundAction {
    private Action action;
    private Object[] parameters;
    public GameRoundAction(Action action, Object... parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public enum Action {
        TRANSFER, TRANSFER_ALL, GAME_OVER
    }
}
