package com.tealorange.mancala.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class GameRound {
    private String winner;
    private int gameId;
    private List<String> availablePits;
    private Map<String, Integer> pits;
    private List<GameRoundAction> actions;

    public GameRound(MancalaGame aGame) {
        gameId = aGame.getId();

        pits = aGame.getPits().stream()
                .collect(Collectors.toMap(Pit::getId, Pit::getCount));

        if (aGame.isGameOver()) {
            //FIXME: test tie
            winner = aGame.getWinner().getId();
        } else {
            availablePits = aGame.getActivePlayer().getAvailablePits().stream()
                    .map(Pit::getId)
                    .collect(Collectors.toList());
        }
    }

    public void addRoundAction(GameRoundAction aAction) {
        actions.add(aAction);
    }
}
