package pacman.core;

public class CoordinateConverter {
    public static final int block_size = 24;
    public static int toScreenCoordinate(int position) { // to get screen coordinate from logical
        return position*block_size;
    }

    public static int fromScreenCoordinate(int position) { //to get logical coordinate from screen
        return position/block_size;
    }


}
