package org.pwr.hpc.gol.api;

import java.util.List;

public interface Cell {
    CellColor getColor();

    void setColor(CellColor color);

    Position getPosition();

    Cell getN();

    Cell getNE();

    Cell getE();

    Cell getSE();

    Cell getS();

    Cell getSW();

    Cell getW();

    Cell getNW();

    List<Cell> getNeighbourhood();

    boolean isDead();

    void commitColor();
}
