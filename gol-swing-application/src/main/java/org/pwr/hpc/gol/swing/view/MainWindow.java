package org.pwr.hpc.gol.swing.view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static final String APPLICATION_TITLE = "Game Of Life";

    private JPanel panel = new JPanel();

    public MainWindow(Integer width, Integer height) {
        this.setSize(width, height);
        this.setTitle(APPLICATION_TITLE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void changeDisplayedPanel(JPanel panel) {
        this.remove(this.panel);
        this.panel = panel;
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
