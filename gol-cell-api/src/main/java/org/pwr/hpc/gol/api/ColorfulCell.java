package org.pwr.hpc.gol.api;

import java.util.ArrayList;
import java.util.List;

public class ColorfulCell implements Cell {
    private final Position position;
    private final BoardContext context;

    private CellColor color = CellColor.DEAD;
    private CellColor newColor;

    public ColorfulCell(Position position, BoardContext context) {
        this.position = position;
        this.context = context;
    }

    public CellColor getColor() {
        return color;
    }

    public void setColor(CellColor color) {
        this.newColor = color;
    }

    public Position getPosition() {
        return position;
    }

    public Cell getN() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x, y - 1));
    }

    public Cell getNE() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x + 1, y - 1));
    }

    public Cell getE() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x + 1, y));
    }

    public Cell getSE() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x + 1, y + 1));
    }

    public Cell getS() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x, y + 1));
    }

    public Cell getSW() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x - 1, y + 1));
    }

    public Cell getW() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x - 1, y));
    }

    public Cell getNW() {
        int x = position.getX();
        int y = position.getY();
        return context.get(Position.get(x - 1, y - 1));
    }

    public List<Cell> getNeighbourhood() {
        Cell[] neighbourhood = new Cell[]{getN(), getNE(), getE(), getSE(), getS(), getSW(), getW(), getNW()};
        List<Cell> result = new ArrayList<Cell>();

        for (Cell neighbor : neighbourhood) {
            if (neighbor == null) {
                continue;
            }
            result.add(neighbor);
        }
        return result;
    }

    public boolean isDead() {
        return getColor().equals(CellColor.DEAD);
    }

    public void commitColor() {
        if (newColor != null) {
            this.color = this.newColor;
            this.newColor = null;
        }
    }
}
