package com.tealorange.mancala.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pit {
    private PlayerBoard player;
    private String id;
    private int count;
    private Pit nextPit;
    private Pit acrossPit;

    protected Pit() {
    }

    public Pit(PlayerBoard player, String id, int count) {
        this.player = player;
        this.id = id;
        this.count = count;
    }

    public boolean isNotEmpty() {
        return count != 0;
    }

    public void addStone(int aCount) {
        count += aCount;
    }

}
