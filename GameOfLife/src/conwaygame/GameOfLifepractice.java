package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */

public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
}
public GameOfLife (String file) {

    // WRITE YOUR CODE HERE
    StdIn.readLine();
    int r = StdIn.readInt();

    StdIn.readLine();
    int c = StdIn.readInt();

    grid = new boolean[r][c];
    for (int row = 0; row < r; row++)
    {
        StdIn.readLine();

            for (int col = 0; col < c; col++)
            {
                boolean cellStatus = StdIn.readBoolean();
                grid[r][c] = cellStatus;
            }
       
    }
}