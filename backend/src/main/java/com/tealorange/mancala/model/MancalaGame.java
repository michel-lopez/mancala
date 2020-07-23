package com.tealorange.mancala.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MancalaGame {
    public static final int INITIAL_STONES = 4;
    public static final int PIT_COUNT = 6;
    public static final int NUMBER_OF_PLAYERS = 2;

    private int id;
    private List<PlayerBoard> players;
    private List<Pit> pits;
    private PlayerBoard activePlayer;
    private boolean gameOver;

    public MancalaGame(int aId) {
        this(aId, loadInitialValues());
    }

    public MancalaGame(int aId, List<Integer> aValues) {
        id = aId;
        players = new ArrayList<>();

        PlayerBoard player1 = createPlayer("Player1", aValues, 0);
        players.add(player1);

        PlayerBoard player2 = createPlayer("Player2", aValues, PIT_COUNT + 1);
        players.add(player2);

        pits = createPitList(player1, player2);
        activePlayer = player1;
    }

    public static List<Integer> loadInitialValues() {
        List<Integer> list = new ArrayList<>(Collections.nCopies(PIT_COUNT * NUMBER_OF_PLAYERS, INITIAL_STONES));
        list.add(PIT_COUNT, 0);
        list.add(0);
        return list;
    }

    public static MancalaGame load(int aId, List<Integer> aSaveData) {
        return new MancalaGame(aId, aSaveData);
    }

    public PlayerBoard createPlayer(String aId, List<Integer> aValues, int aOffset) {
        return new PlayerBoard(aId, aValues.subList(aOffset, aOffset + PIT_COUNT), aValues.get(aOffset + PIT_COUNT));
    }

    private List<Pit> createPitList(PlayerBoard aPlayer1, PlayerBoard aPlayer2) {

        aPlayer1.linkAcrossPits(aPlayer2);
        aPlayer1.linkBoards(aPlayer2);

        List<Pit> allPits = new ArrayList<>();
        allPits.addAll(aPlayer1.getPits());
        allPits.add(aPlayer1.getMancalaPit());
        allPits.addAll(aPlayer2.getPits());
        allPits.add(aPlayer2.getMancalaPit());
        return allPits;
    }


    public GameRound newRound() {
        return new GameRound(this);
    }

    public PlayerBoard getActivePlayer() {
        return activePlayer;
    }

    public GameRound emptyPit(String aPitId) {
        Pit firstPit = getPit(aPitId);

        List<GameRoundAction> actions = GameAction.removeStonesAction(this, firstPit);
        GameRound round = new GameRound(this);
        round.setActions(actions);

        return round;
    }

    private Pit getPit(String aPitId) {
        Pit pit = pits.stream().filter(p -> p.getId().equals(aPitId)).findFirst().orElse(null);
        if (pit == null) {
            //FIXME!
        }
        return pit;
    }

    public void nextPlayer() {
        activePlayer = players.stream().filter(player -> player != activePlayer).findFirst().get();
    }

    public PlayerBoard getWinner() {
        return getPlayers().stream().max(Comparator.comparing(PlayerBoard::getScore)).get();
    }

    public List<Integer> getSaveState() {
        return pits.stream().map(Pit::getCount).collect(Collectors.toList());
    }
}
