package com.tealorange.mancala.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PlayerBoardTest {

    @Test
    public void testLinkAcrossPits() {
        Pit one1 = new Pit();
        Pit one2 = new Pit();
        Pit one3 = new Pit();
        List<Pit> playerOne = Arrays.asList(one1, one2, one3);

        Pit two1 = new Pit();
        Pit two2 = new Pit();
        Pit two3 = new Pit();
        List<Pit> playerTwo = Arrays.asList(two1, two2, two3);

        PlayerBoard.linkAcrossPits(playerOne, playerTwo);

        Assertions.assertEquals(two3, one1.getAcrossPit());
        Assertions.assertEquals(two2, one2.getAcrossPit());

        Assertions.assertEquals(one2, two2.getAcrossPit());
        Assertions.assertEquals(one1, two3.getAcrossPit());
    }
}
