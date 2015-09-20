package org.pwr.hpc.gol.gpu.init;

import jcuda.Sizeof;
import jcuda.driver.*;
import org.pwr.hpc.gol.gpu.CudaDTO;

import java.io.IOException;

import static jcuda.driver.JCudaDriver.*;

public class CudaEnvironment {
    private static final String FUNCTION_NAME = "calculate";

    public static CudaDTO prepare(int width, int height, boolean useSharedMemory) {
        int memorySize = width * height * Sizeof.INT;

        JCudaDriver.setExceptionsEnabled(true);
        String ptxFileName = null;
        try {
            String cudaNativeCppCodePath = CudaEnvironment.class.getResource(chooseNativeSourceCode(useSharedMemory)).getPath();
            ptxFileName = NvccCompilationUtils.preparePtxFile(cudaNativeCppCodePath);
        } catch (IOException e) {
            //impossible...
        }

        cuInit(0);
        CUdevice device = new CUdevice();
        cuDeviceGet(device, 0);
        CUcontext context = new CUcontext();
        cuCtxCreate(context, 0, device);

        CUmodule module = new CUmodule();
        cuModuleLoad(module, ptxFileName);

        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, FUNCTION_NAME);

        CUdeviceptr deviceInput = new CUdeviceptr();
        cuMemAlloc(deviceInput, memorySize);

        CUdeviceptr deviceOutput = new CUdeviceptr();
        cuMemAlloc(deviceOutput, memorySize);

        return new CudaDTO(deviceInput, deviceOutput, memorySize, function);
    }

    private static String chooseNativeSourceCode(boolean useSharedMemory) {
        return useSharedMemory ? "/GameOfLifeLogic_shared.cu" : "/GameOfLifeLogic.cu";
    }
}
