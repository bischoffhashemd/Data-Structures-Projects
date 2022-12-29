package conwaygame;

public class DriverPractice
{   
    public static void main(String[] args)
    {
        StdIn.setFile("grid0.txt");
        int r = StdIn.readInt();
        StdIn.readLine();
        int c = StdIn.readInt();
        boolean[][] grid = new boolean[r][c];
        for (int row = 0; row < r; row++)
        {
            StdIn.readLine();
            for (int col = 0; col < c; col++)
            {
                boolean cellStatus = StdIn.readBoolean();
                grid[row][col] = cellStatus;

            }

        }

        
    }

}





