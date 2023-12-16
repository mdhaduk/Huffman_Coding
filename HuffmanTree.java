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

public class HuffmanTree implements IHuffConstants {

    // The root node of the Huffman Tree
    private TreeNode root;

    // the storage for the new 'tree-path representations' of the
    // original 8-bit binary data
    private String[] paths;

    // the total bits formed by the new 'tree-path representations' of the
    // original 8-bit binary data and their frequencies
    private int newNumberBits;
    private int treeSize;
    private int leafNodeSize;

    /**
     * take the root of an already constructed tree
     * and set it to root here
     *
     * @param root the root TreeNode of an already
     *             constructed tree
     */
    public HuffmanTree(TreeNode root) {
        this.root = root;
    }

    /**
     * take an array of the frequencies of each 8 bit binary value
     * and create a Huffman tree from it
     *
     * @param freqs an array containing the frequency of
     *              each 8 bit binary value
     */
    public HuffmanTree(int[] freqs) {
        PriorityQueue314<TreeNode> q = new PriorityQueue314<>();
        for (int i = 0; i < freqs.length; i++) {
            // add the 8 bit binary representation, alongside
            // its frequencies. if its frequencies are non-zero,
            // to the priority queue
            if (freqs[i] != 0) {
                TreeNode newNode = new TreeNode(i, freqs[i]);
                q.enqueue(newNode);
            }
        }

        // remove the first and second TreeNode elements of the priority queue,
        // combine them into one TreeNode, then add back the combined treeNode
        // to the priority queue
        while (q.size() > 1) {
            TreeNode leftTemp = q.dequeue();
            TreeNode rightTemp = q.dequeue();
            TreeNode newNode = new TreeNode(leftTemp, 0, rightTemp);
            q.enqueue(newNode);
        }

        // now there is only one TreeNode left in the priority queue
        // set that element TreeNode to the root of this HuffmanTree
        root = q.getStorage().getFirst();

        // since the potential 8 bits binary values range from
        // 1-256, create an array of size 257 where the 257th
        // element is the PSEUDO-EOF
        paths = new String[ALPH_SIZE + 1];

        // finds and adds the new 'tree-path representations' of the data
        // to 'paths'
        findPath();
    }

    /**
     * finds and adds the new 'tree-path representations' of the binary data
     * to 'paths'
     * <p>
     * pre: none
     * post: updated 'paths' containing
     * new 'tree-path representations' of the binary data
     */
    public void findPath() {
        leafNodeSize = 0;
        treeSize = 0;
        findPathHelper(root, "", 0);
    }

    private void findPathHelper(TreeNode node, String currentString, int curDepth) {
        // base case
        if (node != null) {
            if (node.isLeaf()) {
                leafNodeSize++;
                // update newNumberBits
                // the curDepth represents the length of the tree path representation
                // of the TreeNode, so multiply that with the frequencies of the TreeNode
                newNumberBits += node.getFrequency() * (curDepth);
                paths[node.getValue()] = currentString;
            } else {
                // since we are not a leaf node yet, traverse through the left and right child
                // of
                // the current node
                findPathHelper(node.getLeft(), currentString + "0", curDepth + 1);
                findPathHelper(node.getRight(), currentString + "1", curDepth + 1);
            }
            treeSize++;
        }
    }

    /**
     * Gets the size of this Huffman tree
     *
     * @return the size of this Huffman Tree
     */
    public int getTreeSize() {
        return treeSize;
    }

    /**
     * Gets the number of leaf nodes in this Huffman tree
     *
     * @return the number of leaf nodes in this Huffman tree
     */
    public int getLeafNodeSize() {
        return leafNodeSize;
    }

    /**
     * Get the total bits formed by the new 'tree-path representations'
     * of the original 8-bit binary data and their frequencies
     *
     * @return the total bits formed by the new 'tree-path representations'
     *         of the original 8-bit binary data and their frequencies
     */
    public int getNewBits() {
        return newNumberBits;
    }

    /**
     * Get the root of this Huffman Tree
     *
     * @return the root of this Huffman Tree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * Get the storage for the new 'tree-path representations'
     * of the original 8-bit binary data
     *
     * @return Get the storage for the new 'tree-path representations'
     *         of the original 8-bit binary data
     */
    public String[] getPaths() {
        return paths;
    }
}
