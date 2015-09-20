package org.pwr.hpc.gol.swing.controller;

import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.api.utils.BoardInitialization;
import org.pwr.hpc.gol.api.utils.RandomFillingManager;
import org.pwr.hpc.gol.swing.model.AppModes;
import org.pwr.hpc.gol.swing.model.AppProperties;
import org.pwr.hpc.gol.swing.utils.BoardContextFactory;
import org.pwr.hpc.gol.swing.utils.RepaintInvoker;
import org.pwr.hpc.gol.swing.view.GamePanel;

public class GamePanelController extends AbstractPanelController<GamePanel, AppProperties> {
    private final BoardInitialization boardInitialization = new BoardInitialization();
    private final RandomFillingManager fillingManager = new RandomFillingManager();

    private BoardContext boardContext;
    private RepaintInvoker repaintInvoker;

    public GamePanelController(WindowController windowController, GamePanel view, AppProperties properties) {
        super(windowController, view, properties);
        this.boardContext = chooseBoardContext(properties);
        boardInitialization.init(boardContext);
        fillingManager.fill(calculateAmount(properties), boardContext);
        view.repaintCells(boardContext);
        repaintInvoker = new RepaintInvoker(boardContext, view, properties.getSpeed());
        repaintInvoker.start();
    }

    private BoardContext chooseBoardContext(AppProperties properties) {
        Integer width = properties.getWidth();
        Integer height = properties.getHeight();

        if(properties.getMode().equals(AppModes.CPU)){
            return BoardContextFactory.getCpuBoardContext(width, height);
        } else if(properties.getMode().equals(AppModes.GPU)){
            return BoardContextFactory.getCudaBoardContext(width, height, BoardContextFactory.CudaMode.DEVICE_MEMORY);
        } else{
            return BoardContextFactory.getCudaBoardContext(width, height, BoardContextFactory.CudaMode.SHARED_MEMORY);
        }
    }

    private int calculateAmount(AppProperties properties) {
        Integer width = properties.getWidth();
        Integer height = properties.getHeight();
        Double percent = properties.getPercentOfLivingCellsAtStart();

        double amount = width * height * percent;
        return Double.valueOf(amount).intValue();
    }
}
