package avengers;

import java.util.ArrayList;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String useTimeStoneInput = args[0];
        String useTimeStoneOutput = args[1];

        // String useTimeStoneInput = "usetimestone3.in";
        // String useTimeStoneOutput = "usetimestone.out";

        StdIn.setFile(useTimeStoneInput);
        StdOut.setFile(useTimeStoneOutput);

        int euThreshhold = StdIn.readInt();
        int numberOfEvents = StdIn.readInt();

        // array representing each event and its probability
        int vertexAndEU[][] = new int[numberOfEvents][2];
        for (int r = 0; r < numberOfEvents; r++)
        {
            vertexAndEU[r][0] = StdIn.readInt();
            vertexAndEU[r][1] =StdIn.readInt();
        }

        // adjecency matrix of each possible event
        int adjMatrix[][] = new int[numberOfEvents][numberOfEvents];
        for (int r = 0; r < numberOfEvents; r++)
        {
            for (int c = 0; c < numberOfEvents; c++)
            {
                adjMatrix[r][c] = StdIn.readInt();
            }
        }
        ArrayList<Integer> totalNumPaths = new ArrayList<Integer>();
        ArrayList<Integer> numPathsMeetThreshhold = new ArrayList<Integer>();

        numberOfPaths (euThreshhold, 0, totalNumPaths, numPathsMeetThreshhold, 0, vertexAndEU, adjMatrix);
        StdOut.println(totalNumPaths.size());
        StdOut.print(numPathsMeetThreshhold.size());
    }

    // number of paths with EUs greater than or equal to EU of avengers winning
    private static void numberOfPaths (int euThreshhold, int currentVertex, ArrayList<Integer> totalNumPaths, ArrayList<Integer> numPathsMeetThreshhold, int totalEU, int vertexAndEU[][], int adjMatrix [][]) {

        totalEU += vertexAndEU[currentVertex][1];
        totalNumPaths.add(1);
        if (totalEU >= euThreshhold) numPathsMeetThreshhold.add(1);

        for (int col = 0; col < adjMatrix.length; col++)
        {
            if (adjMatrix[currentVertex][col] == 1)
            {
                numberOfPaths (euThreshhold, col, totalNumPaths, numPathsMeetThreshhold, totalEU, vertexAndEU, adjMatrix);
            }
        }
    }
    
}
