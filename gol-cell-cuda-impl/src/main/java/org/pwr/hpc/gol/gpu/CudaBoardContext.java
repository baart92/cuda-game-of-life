package org.pwr.hpc.gol.gpu;

import jcuda.Pointer;
import org.pwr.hpc.gol.api.BoardContext;
import org.pwr.hpc.gol.api.CellColor;
import org.pwr.hpc.gol.api.ColorfulCell;
import org.pwr.hpc.gol.api.Position;
import org.pwr.hpc.gol.gpu.init.CudaEnvironment;

import java.util.HashMap;

import static jcuda.driver.JCudaDriver.*;


public class CudaBoardContext extends BoardContext {
    private static final int BLOCK_SIZE = 256;

    private boolean isNotRunningYet = true;
    private boolean useSharedMemory;
    private CudaDTO cudaDTO;

    public CudaBoardContext(Integer width, Integer height, boolean useSharedMemory) {
        super(width, height);
        this.useSharedMemory = useSharedMemory;
    }

    @Override
    public void processOneStep() {
        if (isNotRunningYet) {
            cudaDTO = CudaEnvironment.prepare(getWidth(), getHeight(), useSharedMemory);
            isNotRunningYet = false;
        }

        int[] hostArray = this.parseObjectStateToDeviceFormat();
        cuMemcpyHtoD(cudaDTO.getDeviceInput(), Pointer.to(hostArray),
                cudaDTO.getSize());

        Pointer kernelParameters = Pointer.to(
                Pointer.to(new int[]{getWidth()}),
                Pointer.to(new int[]{getHeight()}),
                Pointer.to(cudaDTO.getDeviceInput()),
                Pointer.to(cudaDTO.getDeviceOutput())
        );

        // Call the kernel function.
        int gridSizeX = (int) Math.ceil((double) (getWidth() * getHeight()) / BLOCK_SIZE);
        cuLaunchKernel(cudaDTO.getFunction(),
                gridSizeX, 1, 1,      // Grid dimension
                BLOCK_SIZE, 2,2,      // Block dimension
                sharedMemorySize(), null,               // Shared memory size and stream
                kernelParameters, null // Kernel- and extra parameters
        );

        cuCtxSynchronize();

        int deviceOutputArray[] = new int[getWidth() * getHeight()];
        cuMemcpyDtoH(Pointer.to(deviceOutputArray), cudaDTO.getDeviceOutput(),
                cudaDTO.getSize());

        parseDeviceDataToObject(deviceOutputArray);
        commitColors();
    }

    private int sharedMemorySize() {
        return useSharedMemory ? 128 : 0;
    }

    public int[] parseObjectStateToDeviceFormat() {
        int boardArray[] = new int[getWidth() * getHeight()];
        int i = 0;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                CellColor color = get(Position.get(x, y)).getColor();
                boardArray[i++] = color.getId();
            }
        }
        return boardArray;
    }

    public void parseDeviceDataToObject(int[] deviceData) {
        if (deviceData.length != getWidth() * getHeight()) {
            throw new IllegalArgumentException();
        }

        cells = new HashMap<>();
        for (int i = 0; i < deviceData.length; i++) {
            int cellWidth = i % getWidth();
            int cellHeight = i / getWidth();
            Position cellPosition = Position.get(cellWidth, cellHeight);
            ColorfulCell cell = new ColorfulCell(cellPosition, this);

            if (deviceData[i] == 0) {
                cell.setColor(CellColor.DEAD);
            } else if (deviceData[i] == 1) {
                cell.setColor(CellColor.ALIVE);
            } else if (deviceData[i] == 2) {
                cell.setColor(CellColor.RED);
            } else if (deviceData[i] == 3) {
                cell.setColor(CellColor.GREEN);
            } else if (deviceData[i] == 4) {
                cell.setColor(CellColor.BLUE);
            } else if (deviceData[i] == 5) {
                cell.setColor(CellColor.YELLOW);
            }
            updateContext(cellPosition, cell);
        }
    }

    protected void finalize() throws Throwable {
        if (cudaDTO != null) {
            if (cudaDTO.getDeviceInput() != null)
                cuMemFree(cudaDTO.getDeviceInput());
            if (cudaDTO.getDeviceOutput() != null)
                cuMemFree(cudaDTO.getDeviceOutput());
        }
    }
}
