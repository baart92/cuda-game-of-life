package org.pwr.hpc.gol.api.utils;

import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.api.ColorfulCell;
import org.pwr.hpc.gol.api.Position;

public class BoardInitialization {
    public void init(BoardContext context) {
        Integer width = context.getWidth();
        Integer height = context.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position p = Position.get(x, y);
                context.updateContext(p, new ColorfulCell(p, context));
            }
        }

    }
}
