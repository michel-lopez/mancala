package com.tealorange.mancala.service;

import com.tealorange.mancala.model.GameRound;
import com.tealorange.mancala.model.MancalaGame;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Scope("singleton")
public class GameService {
    private List<MancalaGame> games = new ArrayList<>();

    public GameRound createGame() {
        MancalaGame game = new MancalaGame(games.size());
        games.add(game);
        return game.newRound();
    }

    public MancalaGame getGame(int aGameId) {
        return games.get(aGameId);
    }

    public List<MancalaGame> getGames() {
        return games;
    }

    public GameRound loadGame(int aGameId) {
        return getGame(aGameId).newRound();
    }

    public MancalaGame getLastGame() {
        if (games.isEmpty()) {
            games.add(new MancalaGame((0)));
        }
        return games.get(games.size() - 1);
    }
}