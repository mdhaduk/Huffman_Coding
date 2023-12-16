
/*  Student information for assignment:
 *
 *  On OUR honor, Rakesh and Milan, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 1
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID:mpd2292
 *  email address: mdhaduk7@gmail.com
 *  Grader name: Casey
 *
 *  Student 2
 *  UTEID: rps2439
 *  email address: rakesh.p.singh.college@gmail.com
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//Class manages decompress functionality
public class Decompress implements IHuffConstants {
    private BitInputStream inputStream;
    private BitOutputStream outputStream;

    // The number of bits written to the uncompressed file/stream
    private int decompressedSize;

    /*
     * Decompress constructor
     * pre: inputStream !=null && outputStream != null
     * post: creates Decompress Object
     */
    public Decompress(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            throw new IllegalArgumentException("Decompress. inputStream and" +
                    "outputStream cannot be null");
        }
        this.inputStream = new BitInputStream(inputStream);
        this.outputStream = new BitOutputStream(outputStream);
    }

    /*
     * Read in header and compressed data
     * pre: none
     * post: reads header of compressed file: magic number and header format
     */
    public void readAllData() throws IOException {
        // If file doesn't begin with MAGIC_NUMBER, it's not compressed
        if (inputStream.readBits(BITS_PER_INT) != MAGIC_NUMBER) {
            inputStream.close();
            outputStream.close();
            throw new IOException("File is not compressed");
        }

        // Read in headerFormat
        int inbits = inputStream.readBits(BITS_PER_INT);
        if (inbits == STORE_COUNTS) {
            // +1 for PSEUDO_EOF
            int[] uncompFreqs = new int[ALPH_SIZE + 1];

            // Read in frequency of every 8-bit integer [0,255], NOT PSEUDO_EOF!
            for (int i = 0; i < ALPH_SIZE; i++) {
                uncompFreqs[i] = inputStream.readBits(BITS_PER_INT);
            }

            // Explicity set frequency of PSEUDO_EOF to 1
            uncompFreqs[PSEUDO_EOF] = 1;
            HuffmanTree tree = new HuffmanTree(uncompFreqs);

            // Traverse through tree
            treeTraverse(tree);

        } else if (inbits == STORE_TREE) {
            // Read in size of tree
            inputStream.readBits(BITS_PER_INT);

            // Create tree
            TreeNode root = headerSTF();
            HuffmanTree tree = new HuffmanTree(root);

            // Traverse through tree
            treeTraverse(tree);
        }

        inputStream.close();
        outputStream.close();
    }

    /*
     * Builds HuffmanTree from Standard Tree Format header
     * pre: none
     * post: returns the root of the tree
     */
    private TreeNode headerSTF() throws IOException {

        // Read 1 bit at a time
        int bitVal = inputStream.readBits(1);

        // Internal node
        if (bitVal == 0) {

            // Make new node, set left and right child to recursive call
            TreeNode newNode = new TreeNode(headerSTF(), 0,
                    headerSTF());
            return newNode;
        }

        // Leaf node
        if (bitVal == 1) {

            // Read in 9 more bits (value of node) and make it a new node with NO child
            TreeNode newNode = new TreeNode(inputStream.readBits((BITS_PER_WORD + 1)), 1);
            return newNode;
        }

        // Ran out of bits
        if (bitVal == -1) {
            throw new IOException("Out of bits");
        }

        return null;
    }

    /*
     * Traverses through HuffmanTree and writesBits to output file
     * based on VALUE
     * pre: tree != null
     * post: returns the root of the tree
     */
    private void treeTraverse(HuffmanTree tree) throws IOException {
        if (tree == null) {
            throw new IllegalArgumentException("treeTraverse. tree!=null");
        }
        decompressedSize = 0;
        TreeNode tempNode = tree.getRoot();
        boolean done = false;
        while (!done) {

            // Walk down tree
            int bit = inputStream.readBits(1);
            if (bit == -1) {
                throw new IOException("Error reading compressed file. \n" +
                        "unexpected end of input. No PSEUDO_EOF value.");
            } else {

                // Go left
                if (bit == 0) {
                    tempNode = tempNode.getLeft();

                    // Go right
                } else if (bit == 1) {
                    tempNode = tempNode.getRight();
                }

                if (tempNode.isLeaf()) {
                    // End of reading compressed file
                    if (tempNode.getValue() == PSEUDO_EOF) {
                        done = true;

                        // Write value to output file (leaf-node)
                    } else {
                        outputStream.writeBits(BITS_PER_WORD, tempNode.getValue());
                        decompressedSize += BITS_PER_WORD;

                        // Set node back to root to walk down next path
                        tempNode = tree.getRoot();
                    }
                }
            }
        }
    }

    /*
     * Returns size decompressedSize
     * pre: none
     * post: returns decompressedSize
     */
    public int decompressSize() {
        return decompressedSize;
    }
}
