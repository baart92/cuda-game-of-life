package org.pwr.hpc.gol.api;

import java.util.HashMap;
import java.util.Map;

public abstract class BoardContext {
    private final Integer width;
    private final Integer height;

    protected Map<Position, Cell> cells = new HashMap<Position, Cell>();

    public BoardContext(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public abstract void processOneStep();

    public void updateContext(Position position, Cell cell) {
        cells.put(position, cell);
    }

    public Cell get(Position position) {
        if (position.getX() < 0 || position.getX() >= width) {
            return null;
        } else if (position.getY() < 0 || position.getY() >= height) {
            return null;
        } else {
            return cells.get(position);
        }
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    protected void commitColors() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.get(Position.get(x, y)).commitColor();
            }
        }
    }
}
