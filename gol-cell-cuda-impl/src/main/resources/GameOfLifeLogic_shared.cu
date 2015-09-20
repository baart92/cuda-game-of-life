extern "C"
__global__ void calculate(int width, int height, int *board, int *board_result)
{
    int i = blockIdx.x * blockDim.x + threadIdx.x;
    int size = width * height;
    __shared__ int alive[size];
    if (i < size)
    {
        int cell_width = i % width;
        int cell_height = i / width;
        int cell_state = board[width*cell_height+cell_width];

        int alive_cells = 0;
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                if(x==0 && y==0)
                    continue;
                int neighbour_cell = width*(cell_height+y)+cell_width+x;
                if(board[neighbour_cell] != 0)
                    alive_cells++;
            }
        }
        alive[i] = alive_cells;
        __syncthreads();


        if(cell_state != 0 && (alive_cells == 2 || alive_cells == 3)){
            board_result[i] = board[width*cell_height+cell_width];
        }

        else if(alive_cells == 3 && cell_state == 0){
            int red_count = 0;
            int green_count = 0;
            int blue_count = 0;
            int yellow_count = 0;
            int max_count = 0;
            int max_color = 0;

            for(int x=-1; x<=1; x++){
                for(int y=-1; y<=1; y++){
                    if(x==0 && y==0)
                        continue;
                    int neighbour_cell = width*(cell_height+y)+cell_width+x;
                    int color_neighbour_cell = board[neighbour_cell];
                    if(color_neighbour_cell == 2){
                        red_count++;
                        if(max_count < red_count){
                            max_count = red_count;
                            max_color = color_neighbour_cell;
                        }
                    }
                    else if(color_neighbour_cell == 3){
                        green_count++;
                        if(max_count < green_count){
                            max_count = green_count;
                            max_color = color_neighbour_cell;
                        }
                    }
                    else if(color_neighbour_cell == 4){
                        blue_count++;
                        if(max_count < blue_count){
                            max_count = blue_count;
                            max_color = color_neighbour_cell;
                        }
                    }
                    else if(color_neighbour_cell == 5){
                        yellow_count++;
                        if(max_count < yellow_count){
                            max_count = yellow_count;
                            max_color = color_neighbour_cell;
                        }
                    }

                }
            }
            int zero_color = 0;
            if(red_count == 0)
                zero_color = 2;
            if(green_count == 0)
                zero_color = 3;
            if(blue_count == 0)
                zero_color = 4;
            if(yellow_count == 0)
                zero_color = 5;

            if(max_count != 1)
                board_result[i] = max_color;
            else
                board_result[i] = zero_color;
        }
        else{
            board_result[i] = 0;
        }
    }

}
