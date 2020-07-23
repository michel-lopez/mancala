package com.tealorange.mancala.model;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tealorange.mancala.model.GameRoundAction.Action.*;

@Log4j2
@AllArgsConstructor
public class GameAction {

    public static List<GameRoundAction> removeStonesAction(MancalaGame aGame, Pit aFirstPit) {
        log.info(() -> "Removing stones for pit: " + aFirstPit);
        List<GameRoundAction> actions = new ArrayList<>();
        Pit pit = aFirstPit;
        PlayerBoard activePlayer = aGame.getActivePlayer();
        int count = aFirstPit.getCount();
        for (int i = 0; i < count; i++) {
            pit = getNextPit(activePlayer, pit);
            actions.addAll(transferOneStone(aFirstPit, pit));
        }
        actions.addAll(lastStoneAction(aGame, pit));
        return actions;
    }

    private static Pit getNextPit(PlayerBoard aActivePlayer, Pit aPit) {
        Pit pit = aPit.getNextPit();
        if (pit instanceof MancalaPit && pit.getPlayer() != aActivePlayer) {
            return pit.getNextPit();
        }
        return pit;
    }

    public static List<GameRoundAction> transferOneStone(Pit aFromPit, Pit aToPit) {
        aFromPit.addStone(-1);
        aToPit.addStone(1);
        return Collections.singletonList(new GameRoundAction(TRANSFER, aFromPit.getId(), aToPit.getId()));
    }

    public static List<GameRoundAction> transferAllStones(Pit aFromPit, MancalaPit aToPit) {
        aToPit.addStone(aFromPit.getCount());
        aFromPit.setCount(0);
        return Collections.singletonList(new GameRoundAction(TRANSFER_ALL, aFromPit.getId(), aToPit.getId()));
    }


    public static List<GameRoundAction> lastStoneAction(MancalaGame aGame, Pit aLastPit) {
        List<GameRoundAction> actions = new ArrayList<>();
        if (aGame.getActivePlayer().getMancalaPit() == aLastPit) {
            actions.addAll(gameOverAction(aGame));
            return actions;
        }
        actions.addAll(steal(aGame, aLastPit));
        actions.addAll(gameOverAction(aGame));
        aGame.nextPlayer();
        return actions;
    }

    private static List<GameRoundAction> steal(MancalaGame aGame, Pit aLastPit) {
        if (aLastPit instanceof MancalaPit || aGame.getActivePlayer() != aLastPit.getPlayer()) {
            return Collections.emptyList();
        }
        Pit acrossPit = aLastPit.getAcrossPit();
        log.info(() -> "Stealing from " + acrossPit);
        MancalaPit mancalaPit = aLastPit.getPlayer().getMancalaPit();
        List<GameRoundAction> actions = new ArrayList<>();
        actions.addAll(transferAllStones(acrossPit, mancalaPit));
        actions.addAll(transferOneStone(aLastPit, mancalaPit));
        return actions;
    }

    public static List<GameRoundAction> gameOverAction(MancalaGame game) {
        List<GameRoundAction> actions = new ArrayList<>();
        if (!game.getPlayers().stream().filter(PlayerBoard::noMoreMoves).findFirst().isPresent()) {
            return actions;
        }

        game.setGameOver(true);
        game.getPlayers().stream()
                .map(PlayerBoard::getAvailablePits)
                .flatMap(List::stream)
                .map(pit -> transferAllStones(pit, pit.getPlayer().getMancalaPit()))
                .forEach(actions::addAll);

        actions.add(new GameRoundAction(GAME_OVER));
        return actions;
    }
}
