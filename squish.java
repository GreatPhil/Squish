import java.io.*;

public class squish {

  public static void main(String[] args) {

    compressor com = new compressor((new Integer(8)).byteValue());
    decompressor decom = new decompressor();
    TreeMaker tm = new TreeMaker();

    if (args.length == 1 && args[0].equals("-h")) {
      //print help message.
      System.out.println ("        >>> Welcome to SQUISH <<<");
      System.out.println ("      This is a hella slow program.");
      System.out.println ("      Thank you for being patient.");

    } else if (args.length == 3) {

      try{

        String command = args[0];
        FileOutputStream WriteFile;
        FileInputStream  ReadFile1;
        FileInputStream  ReadFile2;

        WriteFile = new FileOutputStream(args[2]);
        ReadFile1 = new FileInputStream(args[1]);
        ReadFile2 = new FileInputStream(args[1]);

        if (command.equals("-c")) {

          System.out.print("Building Tree...     ");
          HuffTree HT = tm.makeTree(ReadFile1);
          System.out.println("DONE");

          System.out.print("Compressing Data...  ");
          com.compress(WriteFile, ReadFile2, HT);
          System.out.println("DONE");

        } else if (command.equals("-d")) {
          try {
            decom.decompress(WriteFile, ReadFile1);
          } catch (Exception decomE) {
            System.out.println("ERROR : not a compressed file.");
            System.exit(0);
          }
        } else {
          System.out.println("ERROR: not a proper command line.");
        }

      } catch (IOException e) {System.out.println(e);}

    } else {
      System.out.println("ERROR: not a proper command line.");
    }
  }
}
