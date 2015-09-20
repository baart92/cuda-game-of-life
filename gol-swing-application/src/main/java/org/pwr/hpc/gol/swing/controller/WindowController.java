package org.pwr.hpc.gol.swing.controller;

import org.pwr.hpc.gol.swing.view.MainWindow;

import javax.swing.*;

public class WindowController {
    private MainWindow mainWindow;

    public WindowController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void transferControl(AbstractPanelController controller) {
        changeDisplayedPanel(controller.getView());
    }

    private void changeDisplayedPanel(JPanel panel) {
        mainWindow.changeDisplayedPanel(panel);
    }
}
