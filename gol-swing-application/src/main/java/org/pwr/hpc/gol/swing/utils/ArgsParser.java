package org.pwr.hpc.gol.swing.utils;

import org.pwr.hpc.gol.swing.model.AppModes;
import org.pwr.hpc.gol.swing.model.AppProperties;

public class ArgsParser {
    public static final AppModes DEFAULT_APP_MODE = AppModes.GPU;
    public static final Integer DEFAULT_WIDTH = 600;
    public static final Integer DEFAULT_HEIGHT = 400;
    public static final Long DEFAULT_SPPED_IN_MILISECONDS = 500L;
    public static final Double DEFAULT_PERCENT_OF_LIVING_CELLS = 0.1;

    public static AppProperties parse(String[] args) {
        AppProperties appProperties = new AppProperties();

        appProperties.setMode(parseMode(args));
        appProperties.setWidth(parseWidth(args));
        appProperties.setHeight(parseHeight(args));
        appProperties.setSpeed(parseSpeed(args));
        appProperties.setPercentOfLivingCellsAtStart(parsePercent(args));

        return appProperties;
    }

    private static AppModes parseMode(String[] args) {
        try {
            String modeString = args[0];
            if("cpu".equalsIgnoreCase(modeString)){
                return AppModes.CPU;
            } else if ("gpu".equalsIgnoreCase(modeString)) {
                return AppModes.GPU;
            } else if ("gpu_shared".equalsIgnoreCase(modeString)){
                return AppModes.GPU_SHARED;
            }
            return DEFAULT_APP_MODE;
        } catch (Exception e) {
            return DEFAULT_APP_MODE;
        }
    }

    private static Integer parseWidth(String[] args) {
        try {
            String widthString = args[1];
            return Integer.parseInt(widthString);
        } catch (Exception e) {
            return DEFAULT_WIDTH;
        }
    }

    private static Integer parseHeight(String[] args) {
        try {
            String heightString = args[2];
            return Integer.parseInt(heightString);
        } catch (Exception e) {
            return DEFAULT_HEIGHT;
        }
    }

    private static Long parseSpeed(String[] args) {
        try {
            String speedString = args[3];
            return Long.parseLong(speedString);
        } catch (Exception e) {
            return DEFAULT_SPPED_IN_MILISECONDS;
        }
    }

    private static Double parsePercent(String[] args) {
        try {
            String percentString = args[4];
            Double percent = Double.parseDouble(percentString);
            if (percent >= 0.0 && percent <= 1.0) {
                return percent;
            }
            throw new IllegalArgumentException();
        } catch (Exception e) {
            return DEFAULT_PERCENT_OF_LIVING_CELLS;
        }
    }
}
