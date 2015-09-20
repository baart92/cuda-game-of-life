package org.pwr.hpc.gol.swing.utils;

import org.perf4j.StopWatch;
import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.swing.view.GamePanel;

public class RepaintInvoker extends Thread {
    private final BoardContext context;
    private final GamePanel panel;
    private final Long speed;

    public RepaintInvoker(BoardContext context, GamePanel panel, Long speed) {
        this.context = context;
        this.panel = panel;
        this.speed = speed;
    }

    public void run() {
        Long counter = 1L;
        while (true) {
            try {
                StopWatch watch = new StopWatch();
                context.processOneStep();
                long time = watch.getElapsedTime();
                panel.repaintCells(context);
                System.out.println(String.format("Round %d finished! Time: %d ms", counter++, time));
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
