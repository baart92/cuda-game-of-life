package org.pwr.hpc.gol.swing.controller;

import javax.swing.*;

public abstract class AbstractPanelController<V extends JPanel, M> {
    private WindowController windowController;
    private V view;
    private M model;

    public AbstractPanelController(WindowController windowController, V view, M model) {
        this.windowController = windowController;
        this.view = view;
        this.model = model;
    }

    public WindowController getWindowController() {
        return windowController;
    }

    public V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }
}
