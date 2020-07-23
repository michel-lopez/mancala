package com.tealorange.mancala.model;

import lombok.Data;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlayerBoard {
    private List<Pit> pits;
    private MancalaPit mancalaPit;
    private String id;

    public PlayerBoard(String aId, List<Integer> aPitCounts, int aMancalaCount) {
        id = aId;
        pits = new ArrayList<>();
        Pit prevPit = null;
        for (int i = 0; i < aPitCounts.size(); i++) {
            String pitId = MessageFormat.format("{0}Pit{1}", aId, i);
            Pit pit = new Pit(this, pitId, aPitCounts.get(i));
            pits.add(pit);
            if (prevPit != null) {
                prevPit.setNextPit(pit);
            }
            prevPit = pit;
        }
        String mancalaId = aId + "MancalaPit";
        mancalaPit = new MancalaPit(this, mancalaId, aMancalaCount);
        prevPit.setNextPit(mancalaPit);
    }

    public static void linkAcrossPits(List<Pit> aPlayer1Pits, List<Pit> aPlayer2Pits) {
        int size = aPlayer1Pits.size();
        for (int i = 0; i < size; i++) {
            Pit pit = aPlayer1Pits.get(i);
            Pit acrossPit = aPlayer2Pits.get(size - 1 - i);
            pit.setAcrossPit(acrossPit);
            acrossPit.setAcrossPit(pit);
        }
    }

    public static void linkBoards(PlayerBoard aPlayer1, PlayerBoard aPlayer2) {
        aPlayer1.getMancalaPit().setNextPit(aPlayer2.getFirstPit());
        aPlayer2.getMancalaPit().setNextPit(aPlayer1.getFirstPit());
    }

    public List<Pit> getAvailablePits() {
        return pits.stream()
                .filter(Pit::isNotEmpty)
                .collect(Collectors.toList());
    }

    public Pit getFirstPit() {
        return pits.get(0);
    }

    public boolean noMoreMoves() {
        return getAvailablePits().isEmpty();
    }

    public int getScore() {
        return mancalaPit.getCount();
    }

    public void linkAcrossPits(PlayerBoard aAnotherPlayer) {
        linkAcrossPits(this.getPits(), aAnotherPlayer.getPits());
    }

    public void linkBoards(PlayerBoard aAnotherPlayer) {
        linkBoards(this, aAnotherPlayer);
    }
}



