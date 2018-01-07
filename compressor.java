import java.io.*;

public class compressor {

  public byte L;
  private TreeConverter tc;
  private byteConverter bc;

  public compressor(byte L) {
    this.L = L;
    tc = new TreeConverter(L);
    bc = new byteConverter();
  }

  public void compress(FileOutputStream fos, FileInputStream fis, HuffTree HT) throws IOException{

    String leftover = "";

    // HEADER:
    fos.write(L);

    byte LL = L;
    byte symbols = (new Integer(HT.numberOfLeaves - 1)).byteValue();

    String part2 = bc.toBits(symbols, LL);
    while (part2.length() < L) {
      part2 = part2 + "0";
    }
    while (part2.length() > 7) {
      byte b = bc.toBytes(part2.substring(0,8));
      part2 = part2.substring(8);
      fos.write(b);
    }
    leftover = part2;
    
    // TREE:
    String treeString;
    treeString = tc.TreeToString(HT);
    String output = leftover + treeString;

    while (output.length() > 7) {
      byte b = bc.toBytes(output.substring(0,8));
      output = output.substring(8);
      fos.write(b);
    }
    leftover = output;

    // COMPRESS FILE:
    String full = leftover;
    int length = fis.available();
    for (int i = 0; i < length; i++) {

      Integer integ = new Integer(fis.read());
      byte current = integ.byteValue();

      String path = HT.getPath(current);
      full = full + path;

      while (full.length() > 7) {
        byte b = bc.toBytes(full.substring(0,8));
        full = full.substring(8);
        fos.write(b);
      }
    }

    leftover = full;

    // END OF FILE:
    String EoF = HT.root.EoFstring();
    String last = leftover + EoF;

    while (last.length() > 7) {
      byte b = bc.toBytes(last.substring(0,8));
      last = last.substring(8);
      fos.write(b);
    }
    while (last.length() < 8) {
      last = last + "0";
    }
    byte b = bc.toBytes(last);
    fos.write(b);

  }
}
