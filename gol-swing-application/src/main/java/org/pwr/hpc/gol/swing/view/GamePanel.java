package org.pwr.hpc.gol.swing.view;

import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.api.Position;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private BoardContext boardContext;

    public void repaintCells(BoardContext boardContext) {
        this.boardContext = boardContext;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (boardContext == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;

        Integer width = boardContext.getWidth();
        Integer height = boardContext.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                g2d.setColor(boardContext.get(Position.get(x, y)).getColor().getColor());
                g2d.drawLine(x, y, x, y);
            }
        }

    }
}
