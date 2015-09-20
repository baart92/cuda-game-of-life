package org.pwr.hpc.gol.api.utils;

import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.api.Cell;
import org.pwr.hpc.gol.api.CellColor;
import org.pwr.hpc.gol.api.Position;

import java.util.Random;

public class RandomFillingManager {
    private static Random r = new Random();
    private static CellColor[] colors = new CellColor[]{CellColor.BLUE, CellColor.GREEN, CellColor.RED, CellColor.YELLOW};

    public void fill(int amount, BoardContext context) {
        Integer width = context.getWidth();
        Integer height = context.getHeight();

        int counter = 0;

        while (++counter <= amount) {
            int randomX = r.nextInt(width);
            int randomY = r.nextInt(height);
            int randomColor = r.nextInt(colors.length);

            Cell workingCell = context.get(Position.get(randomX, randomY));
            workingCell.setColor(colors[randomColor]);
            workingCell.commitColor();
        }
    }
}
