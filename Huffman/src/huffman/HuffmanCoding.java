package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);

	/* Your code goes here */

        //initialize variables
        int numOfChars = 0;
        sortedCharFreqList = new ArrayList<CharFreq>();
        int occurencesOfChar[] = new int[128];
  
        while (StdIn.hasNextChar())
        {
            //read character from text file and convert to ASCII value
            int index = (int)StdIn.readChar(); 
            occurencesOfChar[index] += 1;
            numOfChars += 1;
        }

        for (int i = 0; i < occurencesOfChar.length; i++)
        {
            // calculate frequency of each character and add the CharFreq objects to arraylist
            if (occurencesOfChar[i] != 0)
            {
                //calculate probability and create CharFreq object with character and probability
                double numOfOccurances = occurencesOfChar[i];
                double probOfOccurance = numOfOccurances/numOfChars;
                CharFreq character = new CharFreq((char)i, probOfOccurance);

                //add character and probability to array list
                sortedCharFreqList.add(character);
            }
        }

        // if there is only one unique character in the arraylist
        if (sortedCharFreqList.size() == 1)
        {
            CharFreq ObjectAtFirstIdx = sortedCharFreqList.get(0);
            int charAtFirstIdx = (int)ObjectAtFirstIdx.getCharacter();

            // if the character has ASCII code 127, wrap around to code 0 for filler character
            if (charAtFirstIdx == 127)
            {
                CharFreq fillerCharObject = new CharFreq((char)0, 0);
                sortedCharFreqList.add(fillerCharObject);
            }

            else
            {
                char fillerChar = (char)(charAtFirstIdx + 1);
                CharFreq fillerCharObject = new CharFreq(fillerChar, 0);
                sortedCharFreqList.add(fillerCharObject);
            }
        }
        Collections.sort(sortedCharFreqList);

    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

	/* Your code goes here */
        Queue<TreeNode> targetQueue = new Queue<TreeNode>();

        while (!sortedCharFreqList.isEmpty() || targetQueue.size() != 1)
        {
            // create variables
            TreeNode sourceTreeNode1 = new TreeNode(null, null, null);
            TreeNode sourceTreeNode2 = new TreeNode(null, null, null);
            TreeNode parent = new TreeNode(null, null, null);
            CharFreq sourceData1;
            CharFreq sourceData2;
            TreeNode targetData1;
            TreeNode targetData2;
            TreeNode node1;
            TreeNode node2;

            //for node1
            if (targetQueue.isEmpty() && !sortedCharFreqList.isEmpty()) //target queue is empty and source queue is not empty
            {
                sourceData1 = sortedCharFreqList.get(0);
                node1 = new TreeNode(sourceData1, null, null);
                sortedCharFreqList.remove(0);
            }

            else if (sortedCharFreqList.isEmpty() && !targetQueue.isEmpty()) //source queue is empty
            {
                targetData1 = targetQueue.dequeue();
                node1 = targetData1;
            }

            else // neither target nor source queue is empty
            {
                sourceData1 = sortedCharFreqList.get(0);
                targetData1 = targetQueue.peek();
            
                if (targetData1.getData().getProbOcc() < sourceData1.getProbOcc()) 
                {
                    node1 = targetQueue.dequeue();
                }

                else
                {
                    sourceTreeNode1.setData(sourceData1);
                    node1 = sourceTreeNode1;
                    sortedCharFreqList.remove(0);
                }
            }

            // for node2
            if (targetQueue.isEmpty() && !sortedCharFreqList.isEmpty()) //target queue is empty and source queue is not empty
            {
                sourceData2 = sortedCharFreqList.get(0);
                node2 = new TreeNode(sourceData2, null, null); 
                sortedCharFreqList.remove(0);  
            }

            else if (sortedCharFreqList.isEmpty() && !targetQueue.isEmpty()) //source queue is empty
            {
                targetData2 = targetQueue.dequeue();
                node2 = targetData2;
            }

            else // neither target nor source queue is empty
            {
                sourceData2 = sortedCharFreqList.get(0);
                targetData2 = targetQueue.peek();
            
                if (targetData2.getData().getProbOcc() < sourceData2.getProbOcc()) // probability of node in target is smaller than node in source
                {
                    node2 = targetQueue.dequeue();
                }

                else
                {
                    sourceTreeNode2.setData(sourceData2);
                    node2 = sourceTreeNode2;
                    sortedCharFreqList.remove(0);
                }

            }
           
            //create tree
            double parentProb = node1.getData().getProbOcc() + node2.getData().getProbOcc();

            CharFreq parentData = new CharFreq(null, parentProb);
            parent.setData(parentData); //set parent node to have no character and have combined probability
        
            // set left and right pointers on parent node
            parent.setLeft(node1);
            parent.setRight(node2);

            targetQueue.enqueue(parent); //enqueue tree onto target queue 
            if (sortedCharFreqList.isEmpty() && targetQueue.size() == 1) //assign root if finished creating tree (root's probability should be 1)
            {
                huffmanRoot = parent;
            }
        }
    }

    //helper method for makeEncodings 
    private void traversal(TreeNode x, String code) {
        if (x.getLeft() == null && x.getRight() == null) 
        {
           int idx = x.getData().getCharacter();
            encodings[(int)idx] = code;
            return;
        }

        traversal(x.getLeft(), code + "0"); 
        traversal(x.getRight(), code + "1");

    }
    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
	/* Your code goes here */
        encodings = new String[128];
        String code = new String("");
        traversal (huffmanRoot, code);

    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

	/* Your code goes here */
        String code = new String("");

        while (StdIn.hasNextChar())
        {
            char character = StdIn.readChar();
           code = code + encodings[(int)character];
        }
        writeBitString(encodedFile, code);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    
    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

	/* Your code goes here */
        StdIn.setFile (encodedFile);
        String huffmanCode = readBitString(encodedFile);
        char binaryArray[] = huffmanCode.toCharArray();
        String originalChars = "";
        TreeNode x = huffmanRoot;
        for (int i = 0; i < binaryArray.length; i++)
        {
            if (binaryArray[i] == '0')
            {
                x = x.getLeft();
            }
            
            else if (binaryArray[i] == '1')
            {
                x = x.getRight();
            }      

            if (x.getLeft() == null && x.getRight() == null)
            {
                char leafChar = x.getData().getCharacter();
                originalChars = originalChars + leafChar;
                x = huffmanRoot;
            }
        }
        StdOut.print(originalChars);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
