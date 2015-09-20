package org.pwr.hpc.gol.swing.launch;

import org.pwr.hpc.gol.swing.controller.GamePanelController;
import org.pwr.hpc.gol.swing.controller.WindowController;
import org.pwr.hpc.gol.swing.model.AppProperties;
import org.pwr.hpc.gol.swing.utils.ArgsParser;
import org.pwr.hpc.gol.swing.view.GamePanel;
import org.pwr.hpc.gol.swing.view.MainWindow;

import javax.swing.*;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        final AppProperties appProperties = ArgsParser.parse(args);
        System.out.println("Running application with properties: " + appProperties);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow mainWindow = new MainWindow(appProperties.getWidth(), appProperties.getHeight());
                WindowController windowController = new WindowController(mainWindow);
                windowController.transferControl(new GamePanelController(windowController, new GamePanel(), appProperties));
            }
        });
    }
}
