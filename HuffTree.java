import java.util.*;

public class HuffTree {

  public ByteNode root;
  public int numberOfLeaves;

  public Hashtable thePaths;
  public Hashtable theBytes;

  public HuffTree(int leaves) {
    numberOfLeaves = leaves;
    thePaths = new Hashtable();
    theBytes = new Hashtable();
  }

  // Assumes byte (b) has path
  public String getPath(byte b) {
    if (theBytes.get(new Byte(b)) == null) {
      String answer = root.getPath(b);
      theBytes.put(new Byte(b), answer);
      return answer;
    } else {
      return (String)theBytes.get(new Byte(b));
    }
  }

  public boolean isPath(String path) {
    if (thePaths.get(path) == null) {
      boolean answer = root.isPath(path);
      if (answer)
        thePaths.put(path, new ByteHash(root.getByte(path)));
      else
        thePaths.put(path, "no");
      return answer;
    } else if (thePaths.get(path).equals("no")) {
      return false;
    } else
    return true;
  }

  // Assumes isPath(String s)
  public byte getByte(String s) {
    ByteHash bh = (ByteHash)thePaths.get(s);
    return bh.val;
  }

}
