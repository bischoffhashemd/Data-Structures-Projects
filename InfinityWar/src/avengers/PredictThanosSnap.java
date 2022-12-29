package avengers;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
    	// WRITE YOUR CODE HERE
        String predictThanosSnapInputFile = args[0];
        String predictThanosSnapOutputFile = args[1];

        // String predictThanosSnapInputFile = "predictthanossnap1.in";
        // String predictThanosSnapOutputFile = "predictthanossnap.out";

        StdIn.setFile(predictThanosSnapInputFile);
        StdOut.setFile(predictThanosSnapOutputFile);

        StdRandom.setSeed(StdIn.readLong());
        
        int adjMatrixDimension = StdIn.readInt();
        int adjMatrix[][] = new int[adjMatrixDimension][adjMatrixDimension];

        // put input into adjecency matrix
        for (int r = 0; r < adjMatrix.length; r++)
        {
            for (int c = 0; c < adjMatrix[r].length; c++)
            {
                adjMatrix[r][c] = StdIn.readInt();
            }
        }

        deleteHalfPopulation(adjMatrix);
        //StdOut.print(isConnected);

    }

    // use random function to delete half of the population
    private static void deleteHalfPopulation (int adjMatrix[][]) {

        int alivePopulation = adjMatrix.length; //check... maybe length - 1?
        boolean alive[] = new boolean[adjMatrix.length]; // create array to determine which people are still alive

        for (int i = 0; i < alive.length; i++)
        {
            alive[i] = true;
        }
        for (int vertex = 0; vertex < adjMatrix.length; vertex++)
        {
            if (StdRandom.uniform() <= 0.5)
            {
                alive[vertex] = false;
                alivePopulation -= 1;

                for (int position = 0; position < adjMatrix[vertex].length; position++)
                {
                    adjMatrix[vertex][position] = 0;
                    adjMatrix[position][vertex] = 0;
                }
            }
        }

        boolean[] visited = new boolean[adjMatrix.length];
        populationConnected(0, 0, adjMatrix, visited, alive);

        int connectedPopulation = 0;
        for (int vertex = 0; vertex < visited.length; vertex++)
        {
            if (visited[vertex]) connectedPopulation += 1;
        }

        //print result
        if (connectedPopulation == alivePopulation) StdOut.print(true);
        else if (connectedPopulation < alivePopulation) StdOut.print (false);
        else if (connectedPopulation > alivePopulation) StdOut.print("error");
    }

    // check if all members of population are connected to each other
    private static void populationConnected (int currentVertex, int vertexCount, int adjMatrix[][], boolean visited[], boolean alive[]){
       
        while (!alive[currentVertex]) currentVertex += 1;
        vertexCount += 1; // check if counter works correctly bc having issues in other method
        visited[currentVertex] = true;

        for (int c = 0; c < adjMatrix.length; c++)
        {
            if (adjMatrix[currentVertex][c] == 1 && !visited[c] && alive[c])
            {
                populationConnected(c, vertexCount, adjMatrix, visited, alive);
            }
        }
    }
}
