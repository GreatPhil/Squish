# Squish

### Overview

This project is a revisit to one of my very first assignments for an undergraduate data structures course (CS61B @ UC Berkeley, Summer 2002).  The project implements a file compression algorithm using a Huffman Tree to encode the bytes within the file.

### Summary

The main functions of the program are performed by:

- TreeMaker.java
- compressor.java
- decomrpessor.java

If run with the -c (compress) option, the TreeMaker beings making a tree.  First, it scans the file, creating what we called "FreqElements" objects for each type of byte ("symbol").  The FreqElements are used for storing the frequency of a byte and the byte itself, as needed for Huffman Tree encoding.  The FreqElements are then stored into an array.  We originally stored these as a Vector, but later changed this to an array data type to improve the program speed.  Next, the array is put into a sorted Vector, and a Tree is made from the Vector.

Our Trees have a ByteNode field called "root", used to access the top node of the tree, and two Hashtables called thePaths and theBytes used in decompression for memoization and thus improved speed.

Our __ByteNodes__ that make up our tree have these important fields:

    TYPE which can be "root", "inner", "leaf", or "EoF"
    DATA which is the byte, stored in the node
    LEFT and RIGHT which are the Children of this node
    FREQ which is used in building the tree according to the Huffman algorithm.

The __Compression__ has four parts:

    HEADER:        Writes the L byte, which is 8
    TREE:          We call TreeToString
    COMPRESS FILE: Performs actual file compression
    END OF FILE:   Simply outputs the EoF, padded if necessary

During the compression function, we write the bytes to the output file whenever we have enough (8) bits in our processing string.  Our TreetoString function is done simply with recursive calls and calls to our toBits() method which makes a byte into an 8-bit String.

Our __Decompressor__ has 2 major parts:

    RecreateTree
    Decompress

_RecreateTree_ is performed by a simple preorder-traversal as asked for.  _Decompression_ is performed by reading one bit at a time, and adding the bit to a current String.  Whenever that String evaluates to a real path name, we write the byte associated with that path to the file. This process was later on memoized to improve speed performance.

__NOTE:__ there are certain aspects of the code where we purposely added flexibility so that we could later on allow variable length input symbols (a primary example is the variable byte "L" for length).  This can be updated in a future enhancement to this project

### A Note on Run Time:

Note that our help command returns a statement that points out the slowness of our program, especially the decompressing process.  However, we did try the folloing steps to try to increase the program's speed:

      - We used Hashtables to memoize paths
      - We used a FreqElement array to make frequency checking take O(n) time
      - We experimented with using Buffered Input Streams -- in order to make the reading process faster
