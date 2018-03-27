# Squish

### Summary

This project is a revisit to one of my first projects for an undergraduate data structures course (CS61B @ UC Berkeley, Summer 2002).  The project implements a file compression algorithm using a Huffman Tree to encode the bytes within the file.

The main actions of our program are performed by:

- TreeMaker.java
- compressor.java
- decomrpessor.java

If the -c command is given, our TreeMaker starts making a tree. First, it scans the file making what we call "FreqElements" for each type of byte ("symbol")  These FreqElements are useful for storing the frequency of a byte and the byte itself.  These FreqElements are then stored in an array. We originally stored in a Vector, but later changed this to improve the program speed. Next, the array is put into a sorted Vector, and a Tree is made from the Vector.

Our Trees have a ByteNode field called root, used to access the top node of the tree, and two Hashtables called thePaths and theBytes used in decompression for memoization and thus improved speed.

Our __ByteNodes__ that make up our tree have these important fields:

    TYPE which can be "root", "inner", "leaf", or "EoF"
    DATA which is the byte
    LEFT and RIGHT which are the Children
    FREQ which is used in building the tree according to
         the Huffman algorithm.

The __Compression__ has four parts:

    -HEADER:        Writes the L byte, which is 8
    -TREE:          We call TreeToString
    -COMPRESS FILE: Performs actual file compression
    -END OF FILE:   Simply outputs the EoF, padded if necessary

All the while, we are writing bytes to the output file whenever we have enough (8) bits in our string.  Our TreetoString is done simply with recursive calls and calls to our toBits() method which makes a byte into an 8-bit String.

Our __Decompressor__ has 2 major parts:

    -recreateTree
    -decompress

RecreateTree is done by a simple preorder-traversal as asked for. The Decompressing is done by reading one bit at a time, and adding the bit to a current String.  Whenever that String equals a real path, we write the byte associated with that path to the file. This process was later on memoized to improve speed performance.

Note that there are certain parts in our code where we purposely added flexibility so that we could later on allow variable length input symbols (namely the variable byte L found all over the place).  However, we were not able to complete this extra credit, so please just ignore the L in most cases and intepret it as an 8.

### A Note on Run Time:

You may notice that our help command points our the slowness of our program, especially the decompressing process.  However, we have
taken several steps to try to amke it go faster.  For example:

      - We used Hashtables to memoize paths
      - We used a FreqElement array to make frequency checking take O(n) time.
      - We experimented with using Buffered Input Streams, which were suposed to make the reading process go faster.
