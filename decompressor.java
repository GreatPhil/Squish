import java.io.*;

public class decompressor {

  byte L;
  HuffTree HT;
  byteConverter bc = new byteConverter();
  TreeConverter tc;


  public decompressor() {
  }

  public void decompress(FileOutputStream fos, FileInputStream fis) throws IOException {

    // Get Header:
    int integ = fis.read();
    L = (new Integer(integ)).byteValue();
    tc = new TreeConverter(L);


    int sofar = 0; // I think I added this variable for "-v".
    int symbolCount= fis.read();

    // Extract Tree:
    System.out.print("Building Tree...     ");
    int current = 0;
    String theTree = "";
    String leftover = "";
    boolean nextIsByte = false;

    while (current < symbolCount + 2) { //

      if(leftover.length() == 0) {
        byte symb = (new Integer(fis.read())).byteValue();
        leftover = bc.toBits(symb, L);
      }

      else if (nextIsByte) {

        while (leftover.length() < L) {
          byte b = (new Integer(fis.read())).byteValue();
          leftover = leftover + bc.toBits(b, L);
        }
        theTree = theTree + leftover.substring(0, L);
        leftover = leftover.substring(L);
        nextIsByte = false;
      }

      else {

        if (leftover.charAt(0) == '1') {
          theTree = theTree + "1";
          current = current + 1;
          leftover = leftover.substring(1);
          nextIsByte = true;
        } else {
          theTree = theTree + "0";
          leftover = leftover.substring(1);
        }
      }
    }
    HuffTree RHT = tc.recreateTree(theTree, symbolCount);
    System.out.println("DONE");

    // Decompress:
    System.out.print("Decompressing Data...");
    String path = "";

    String EoF = RHT.root.EoFstring();

    while (true) { // loop through the compressed data

      if (leftover.length() == 0) {

        byte symb = (new Integer(fis.read())).byteValue();
        leftover = bc.toBits(symb, L);
      }

      else if (path.equals(EoF))
      break;

      else {
        path = path + leftover.substring(0,1);
        leftover = leftover.substring(1);
        if (RHT.isPath(path)) {
          fos.write(RHT.getByte(path));
          path = "";
        }
      }

    }
    System.out.println("DONE");
  }
}
