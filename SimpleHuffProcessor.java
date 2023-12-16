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

public class SimpleHuffProcessor implements IHuffProcessor {
    // Check if preprocess has been called before calling compress
    private boolean preprocessCalled;

    // Which headerFormat used (SCF or STF)
    private int format;

    // Frequency of each 8-bit chunk from uncompressed file
    private int[] freqs;

    // Original bits - compressed bits
    private int bitsSaved;

    private HuffmanTree tree;
    private IHuffViewer myViewer;

    /**
     * Preprocess data so that compression is possible ---
     * count characters/create tree/store state so that
     * a subsequent call to compress will work. The InputStream
     * is <em>not</em> a BitInputStream, so wrap it int one as needed.
     *
     * @param in           is the stream which could be subsequently compressed
     * @param headerFormat a constant from IHuffProcessor that determines what kind
     *                     of
     *                     header to use, standard count format, standard tree
     *                     format, or
     *                     possibly some format added in the future.
     * @return number of bits saved by compression or some other measure
     *         Note, to determine the number of
     *         bits saved, the number of bits written includes
     *         ALL bits that will be written including the
     *         magic number, the header format number, the header to
     *         reproduce the tree, AND the actual data.
     * @throws IOException if an error occurs while reading from the input file.
     */
    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("preprocessCompress. in != null");
        }
        preprocessCalled = true;

        format = headerFormat;

        Compressed preCompressing = new Compressed(in, format);
        freqs = preCompressing.makeFreqs();
        tree = preCompressing.makeTree(freqs);
        bitsSaved = preCompressing.bitsSaved(tree);

        return bitsSaved;
    }

    /**
     * Compresses input to output, where the same InputStream has
     * previously been pre-processed via <code>preprocessCompress</code>
     * storing state used by this call.
     * <br>
     * pre: <code>preprocessCompress</code> must be called before this method
     *
     * @param in    is the stream being compressed (NOT a BitInputStream)
     * @param out   is bound to a file/stream to which bits are written
     *              for the compressed file (not a BitOutputStream)
     * @param force if this is true create the output file even if it is larger than
     *              the input file.
     *              If this is false do not create the output file if it is larger
     *              than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        if (!preprocessCalled) {
            throw new IOException("compress. preprocessCompress not called.");
        }

        // Do not compress if uncompressed file is larger than compressed AND force
        // compress is off
        if (!force && bitsSaved < 0) {
            myViewer.showError("Compressed file has " + -bitsSaved +
                    " more bits than uncompressed file. \nSelect \"force compression\" " +
                    "option to compress");
            return 0;
        }

        Compressed compressing = new Compressed(in, out, format);
        compressing.makeHeader(freqs, tree);
        compressing.compressedData(tree);
        return tree.getNewBits();
    }

    /**
     * Uncompress a previously compressed stream in, writing the
     * uncompressed bits/data to out.
     *
     * @param in  is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.
     */
    public int uncompress(InputStream in, OutputStream out) throws IOException {
        Decompress decompress = new Decompress(in, out);
        decompress.readAllData();
        return decompress.decompressSize();
    }

    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }
}
