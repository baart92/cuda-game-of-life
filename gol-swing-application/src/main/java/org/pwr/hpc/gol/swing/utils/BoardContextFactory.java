package org.pwr.hpc.gol.swing.utils;

import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.cpu.CpuBoardContext;
import org.pwr.hpc.gol.gpu.CudaBoardContext;

public final class BoardContextFactory {
    public enum CudaMode{
        DEVICE_MEMORY, SHARED_MEMORY;
    }
    public static BoardContext getCpuBoardContext(Integer width, Integer height) {
        return new CpuBoardContext(width, height);
    }

    public static BoardContext getCudaBoardContext(Integer width, Integer height, CudaMode cudaMode) {
        if(cudaMode.equals(CudaMode.SHARED_MEMORY)){
            return new CudaBoardContext(width, height, true);
        } else{
            return new CudaBoardContext(width, height, false);
        }
    }
}
