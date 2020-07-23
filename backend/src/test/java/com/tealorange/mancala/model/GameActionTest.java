package com.tealorange.mancala.model;

import com.tealorange.mancala.model.GameRoundAction.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.tealorange.mancala.model.GameRoundAction.Action.*;

public class GameActionTest {

    public static final List<Integer> AFTER_FIRST_ROUND = Arrays.asList(
            4, 4, 0, 5, 5, 5, 1,
            4, 4, 4, 4, 4, 4, 0
    );

    public static final List<Integer> AFTER_SECOND_ROUND = Arrays.asList(
            4, 4, 0, 5, 5, 0, 2,
            5, 5, 5, 5, 4, 4, 0
    );

    @Test
    public void testRemoveStonesAction() {
        MancalaGame game = new MancalaGame(0);
        PlayerBoard activePlayer = game.getActivePlayer();
        List<GameRoundAction> actions = GameAction.removeStonesAction(game, game.getPits().get(2));

        Iterator<GameRoundAction> iterator = actions.iterator();
        assertTransferStone(iterator, "Player1Pit2", "Player1Pit3");
        assertTransferStone(iterator, "Player1Pit2", "Player1Pit4");
        assertTransferStone(iterator, "Player1Pit2", "Player1Pit5");
        assertTransferStone(iterator, "Player1Pit2", "Player1MancalaPit");
        Assertions.assertFalse(iterator.hasNext());

        Assertions.assertEquals(AFTER_FIRST_ROUND, game.getSaveState());
        Assertions.assertEquals(activePlayer, game.getActivePlayer());
    }

    @Test
    public void testRemoveStonesAction2() {
        MancalaGame game = MancalaGame.load(0, AFTER_FIRST_ROUND);
        PlayerBoard activePlayer = game.getActivePlayer();
        List<GameRoundAction> actions = GameAction.removeStonesAction(game, game.getPits().get(5));

        Iterator<GameRoundAction> iterator = actions.iterator();
        assertTransferStone(iterator, "Player1Pit5", "Player1MancalaPit");
        assertTransferStone(iterator, "Player1Pit5", "Player2Pit0");
        assertTransferStone(iterator, "Player1Pit5", "Player2Pit1");
        assertTransferStone(iterator, "Player1Pit5", "Player2Pit2");
        assertTransferStone(iterator, "Player1Pit5", "Player2Pit3");
        Assertions.assertFalse(iterator.hasNext());

        Assertions.assertEquals(AFTER_SECOND_ROUND, game.getSaveState());
        Assertions.assertNotEquals(activePlayer, game.getActivePlayer());
    }

    @Test
    public void testGameOverComplete() {
        List<Integer> saveData = Arrays.asList(
                0, 0, 0, 0, 0, 1, 10,
                0, 0, 9, 0, 0, 8, 20
        );
        MancalaGame game = MancalaGame.load(0, saveData);
        List<GameRoundAction> actions = GameAction.removeStonesAction(game, game.getPits().get(5));

        Iterator<GameRoundAction> iterator = actions.iterator();
        assertTransferStone(iterator, "Player1Pit5", "Player1MancalaPit");

        assertTransferAllStone(iterator, "Player2Pit2", "Player2MancalaPit");
        assertTransferAllStone(iterator, "Player2Pit5", "Player2MancalaPit");
        Assertions.assertEquals(GAME_OVER, iterator.next().getAction());
        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertEquals("Player2", game.getWinner().getId());
    }

    @Test
    public void testGameOver() {
        List<Integer> saveData = Arrays.asList(
                0, 0, 0, 0, 0, 0, 11,
                0, 0, 9, 0, 0, 8, 20
        );
        MancalaGame game = MancalaGame.load(0, saveData);
        List<GameRoundAction> actions = GameAction.gameOverAction(game);

        Iterator<GameRoundAction> iterator = actions.iterator();
        assertTransferAllStone(iterator, "Player2Pit2", "Player2MancalaPit");
        assertTransferAllStone(iterator, "Player2Pit5", "Player2MancalaPit");
        Assertions.assertEquals(GAME_OVER, iterator.next().getAction());
        Assertions.assertFalse(iterator.hasNext());
    }

    private void assertTransferStone(Iterator<GameRoundAction> aIterator, String aId, String aId2) {
        assertTransferStone(aIterator, TRANSFER, aId, aId2);
    }

    private void assertTransferAllStone(Iterator<GameRoundAction> aIterator, String aId, String aId2) {
        assertTransferStone(aIterator, TRANSFER_ALL, aId, aId2);
    }

    private void assertTransferStone(Iterator<GameRoundAction> aIterator, Action aType, String aId, String aId2) {
        GameRoundAction action = aIterator.next();
        Assertions.assertEquals(aType, action.getAction());
        Assertions.assertEquals(aId, action.getParameters()[0]);
        Assertions.assertEquals(aId2, action.getParameters()[1]);
    }
}
