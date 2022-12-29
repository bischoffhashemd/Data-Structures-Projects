package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];
        // String locateTitanInputFile = "locatetitan3.in";
        // String locateTitanOutputFile = "locatetitan.out";

        StdIn.setFile(locateTitanInputFile);
        StdOut.setFile(locateTitanOutputFile);
        int matrixDimension = StdIn.readInt();

        // create functionality array
        double functionality [] = new double [matrixDimension];
        for (int i = 0; i < matrixDimension; i++)
        {
            int idx = StdIn.readInt();
            functionality[i] = StdIn.readDouble();
        }

        //create adjecency matrix
        int matrix [][] = new int[matrixDimension][matrixDimension];
        for (int r = 0; r < matrixDimension; r++)
        {
            for (int c = 0; c < matrixDimension; c++)
            {
                matrix[r][c] = StdIn.readInt();
            }
        }
        modifiedMatrix(functionality, matrix);
        StdOut.print(minimumCost(matrix));
    }

    //modifying original matrix to account for  functionality of portals
    private static void modifiedMatrix (double [] functionality, int [][] modifiedArray){

        for (int r = 0; r < modifiedArray.length; r++)
        {
            for (int c = 0; c < modifiedArray[r].length; c++)
            {
                double originalWeight = modifiedArray[r][c]; //get original value in current index of matrix
                double firstFunctionality = functionality[r]; // get functionality of portal r
                double secondFunctionality = functionality[c]; // get functionality of portal c
                int newWeight = (int)(originalWeight / (firstFunctionality * secondFunctionality)); //calculate new value of index of matrix, taking functionality into accoutn
                modifiedArray[r][c] = newWeight; //set current index of matrix to modified value
            }
        }
    }

    // calculating the minimum cost to get from Earth to Titan
    private static int minimumCost (int [][] modifiedArray){
     
        int minCost [] = new int[modifiedArray.length];
        minCost[0] = 0; //can delete

        for (int i = 1; i < modifiedArray.length; i++)
        {
            minCost[i] = Integer.MAX_VALUE;
        }

        boolean dijkstraSet [] = new boolean[modifiedArray.length];

        //add condition if idx is not in Dijkstra set
        int verticesProcessedCount = 0;
        while (verticesProcessedCount < modifiedArray.length)
        {
            int count = 0; 
            int idxOfMin = 0;
            int minValue = 0;
            for (int i = 0; i < minCost.length; i++)
            {
                if (!dijkstraSet[i])
                {
                    if (count == 0)
                    {
                        idxOfMin = i;
                        minValue = minCost[i];
                    } 

                    else if (minCost[i] < minValue)
                    {
                        idxOfMin = i;
                        minValue = minCost[i];
                    }
                    count++;
                }
            }

            dijkstraSet[idxOfMin] = true; // set index equal to 1 to indicate it this index has already been processed

            for (int c = 0; c < modifiedArray[idxOfMin].length; c++)
            {
                if (modifiedArray[idxOfMin][c] != 0 && !dijkstraSet[c])
                {
                    int newDistance = minCost[idxOfMin] + modifiedArray[idxOfMin][c];
                    if (newDistance < minCost[c]) minCost[c] = newDistance;
                }
            }
            verticesProcessedCount += 1;
        }
        
        return minCost[minCost.length - 1];
    }
}
