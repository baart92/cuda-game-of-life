package org.pwr.hpc.gol.api;

import java.awt.*;

public enum CellColor {
    DEAD(Color.WHITE, 0),
    ALIVE(Color.BLACK, 1),
    RED(Color.RED, 2),
    GREEN(Color.GREEN, 3),
    BLUE(Color.BLUE, 4),
    YELLOW(Color.YELLOW, 5);

    private final Color color;
    private final int id;

    CellColor(Color color, int id) {
        this.color = color;
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
