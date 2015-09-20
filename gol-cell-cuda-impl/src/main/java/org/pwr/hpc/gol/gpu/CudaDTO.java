package org.pwr.hpc.gol.gpu;

import jcuda.driver.CUdeviceptr;
import jcuda.driver.CUfunction;

public class CudaDTO {
    private final CUdeviceptr deviceInput;
    private final CUdeviceptr deviceOutput;
    private final int size;
    private final CUfunction function;

    public CudaDTO(CUdeviceptr deviceInput, CUdeviceptr deviceOutput, int size, CUfunction function) {
        this.deviceInput = deviceInput;
        this.deviceOutput = deviceOutput;
        this.size = size;
        this.function = function;
    }

    public CUdeviceptr getDeviceInput() {
        return deviceInput;
    }

    public CUdeviceptr getDeviceOutput() {
        return deviceOutput;
    }

    public int getSize() {
        return size;
    }

    public CUfunction getFunction() {
        return function;
    }
}
