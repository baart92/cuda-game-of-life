package org.pwr.hpc.gol.cpu;

import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.api.Cell;
import org.pwr.hpc.gol.api.CellColor;
import org.pwr.hpc.gol.api.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CpuBoardContext extends BoardContext {
    public CpuBoardContext(Integer width, Integer height) {
        super(width, height);
    }

    @Override
    public void processOneStep() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                processSingleCell(get(Position.get(x, y)));
            }
        }
        commitColors();
    }

    private void processSingleCell(Cell cell) {
        int livingNeighbours = calculateLivingNeighbours(cell);
        if (cell.isDead() && livingNeighbours == 3) {
            cell.setColor(calculateColorForAlivedCell(cell));
        } else if (!cell.isDead() && livingNeighbours != 2 && livingNeighbours != 3) {
            cell.setColor(CellColor.DEAD);
        }
    }

    private int calculateLivingNeighbours(Cell cell) {
        int counter = 0;
        List<Cell> neighbourhood = cell.getNeighbourhood();
        for (Cell neighbor : neighbourhood) {
            if (!neighbor.isDead()) {
                counter++;
            }
        }
        return counter;
    }

    private CellColor calculateColorForAlivedCell(Cell cell) {
        List<Cell> neighbourhood = cell.getNeighbourhood();

        Map<CellColor, Integer> stats = new HashMap<CellColor, Integer>();
        stats.put(CellColor.BLUE, 0);
        stats.put(CellColor.RED, 0);
        stats.put(CellColor.GREEN, 0);
        stats.put(CellColor.YELLOW, 0);

        for (Cell neighbor : neighbourhood) {
            if (neighbor.isDead()) {
                continue;
            }

            CellColor neighColor = neighbor.getColor();
            stats.put(neighColor, stats.get(neighColor) + 1);
        }

        return processStats(stats);
    }

    private CellColor processStats(Map<CellColor, Integer> stats) {
        int max = 0;
        CellColor maxColor = null;
        CellColor zeroColor = null;
        Set<Map.Entry<CellColor, Integer>> entries = stats.entrySet();
        for (Map.Entry<CellColor, Integer> entry : entries) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxColor = entry.getKey();
            }
            if (entry.getValue() == 0) {
                zeroColor = entry.getKey();
            }
        }
        return max != 1 ? maxColor : zeroColor;
    }
}
