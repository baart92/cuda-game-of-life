package org.pwr.hpc.gol.swing.model;

import org.pwr.hpc.gol.swing.model.AppModes;

public class AppProperties {
    private AppModes mode;
    private Integer width;
    private Integer height;
    private Long speed;
    private Double percentOfLivingCellsAtStart;

    public AppModes getMode() {
        return mode;
    }

    public void setMode(AppModes mode) {
        this.mode = mode;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Double getPercentOfLivingCellsAtStart() {
        return percentOfLivingCellsAtStart;
    }

    public void setPercentOfLivingCellsAtStart(Double percentOfLivingCellsAtStart) {
        this.percentOfLivingCellsAtStart = percentOfLivingCellsAtStart;
    }

    @Override
    public String toString() {
        return "AppProperties{" +
                "mode=" + mode +
                ", width=" + width +
                ", height=" + height +
                ", speed=" + speed +
                ", percentOfLivingCellsAtStart=" + percentOfLivingCellsAtStart +
                '}';
    }
}
