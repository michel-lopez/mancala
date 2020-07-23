package com.tealorange.mancala.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MancalaGameTest {

    public static final List<Integer> FIRST_ROUND = Arrays.asList(
            4, 4, 0, 5, 5, 5, 1,
            4, 4, 4, 4, 4, 4, 0);

    @Test
    public void initialBoard() {
        MancalaGame game = new MancalaGame(0);
        List<Integer> expected = Arrays.asList(
                4, 4, 4, 4, 4, 4, 0,
                4, 4, 4, 4, 4, 4, 0);
        Assertions.assertEquals(expected, game.getSaveState());

        List<String> expectedType = Arrays.asList(
                "P", "P", "P", "P", "P", "P", "M",
                "P", "P", "P", "P", "P", "P", "M");
        Assertions.assertEquals(expectedType, game.getPits().stream()
                .map(pit -> pit instanceof MancalaPit ? "M" : "P").collect(Collectors.toList()));
    }

    @Test
    public void loadState() {
        MancalaGame game = MancalaGame.load(0, FIRST_ROUND);
        Assertions.assertEquals(FIRST_ROUND, game.getSaveState());
    }

    @Test
    public void test() {
        MancalaGame game = new MancalaGame(0);

        GameRound gameRound = game.emptyPit("Player1Pit2");
        List<GameRoundAction> actions = gameRound.getActions();
//        Assertions.assertEquals(4, actions.size());


        Assertions.assertEquals(FIRST_ROUND, game.getSaveState());
    }


}
