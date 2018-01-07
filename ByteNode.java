public class ByteNode{

  String type; // leaf, inner, root, or EoF?
  int    freq; // frequency of occurance
  byte   data; // data item (i.e. byte) stored in node

  ByteNode left;  // left child node
  ByteNode right; // right child node


  // "To String" Function
  public String toString() {
    String s = "";
    s = s + " " + type + " " + freq + " " + data;
    return s;
  }

  // Prints the contents of the sub-tree from this node downwards.
  // This function is mainly useful for debugging.
  public void TreePrinter() {
    System.out.println(this.toString());
    if (left != null) {
      left.TreePrinter();
    }
    if (right != null) {
      right.TreePrinter();
    }
  }


  // Constructor -- Used to create a Root Node
  public ByteNode(String type, ByteNode left, ByteNode right) {
    int frequency = 0;
    if (left != null) {
      frequency += left.freq;
    }
    if (right != null) {
      frequency += right.freq;
    }
    this.freq  = frequency;
    this.type  = type; // i.e. "root" or whatever else
    this.left  = left;
    this.right = right;
    // this.data = 0;
  }

  // Constructor -- Used to create an Inner Node
  public ByteNode(ByteNode left, ByteNode right) {
    int frequency = 0;
    if (left != null) {
      frequency += left.freq;
    }
    if (right != null) {
      frequency += right.freq;
    }
    this.freq  = frequency;
    this.type  = "inner";
    this.left  = left;
    this.right = right;
    //this.data = 0;
  }

  // Constructor -- Used to create a Leaf Node
  public ByteNode(byte data, int frequency) {
    this.freq  = frequency;
    this.type  = "leaf";
    this.data  = data;
    this.left  = null;
    this.right = null;
  }

  // Constructor -- Used to make EoF Node
  public ByteNode() {
    this.type = "EoF";
    // this.freq = 0;
    // this.data = 0;
    // this.left = null;
    // this.right = null;
  }


  // Used for finding paths
  public boolean contains(byte b) {
    if (type.equals("leaf")) {
      if (data == b)
        return true;
      else
        return false;
    } else if (left.contains(b) || right.contains(b)) {
      return true;
    } else
    return false;
  }

  public String getPath(byte b) {
    return getPathHelper(b, "");
  }

  private String getPathHelper(byte b, String s) {
    if (type.equals("leaf")) {
      if (data == b) {
        return s;
      } else {
        System.out.println("ERROR: Tree does not contain " + b);
        return "YOU LOSE.";
      }
    } else if (left.contains(b)) {
      return left.getPathHelper(b, s + "0");
    } else if (right.contains(b)) {
      return right.getPathHelper(b, s + "1");
    } else {
      System.out.println("ERROR: Tree does not contain " + b);
      return "YOU LOSE.";
    }
  }

  public boolean isPath(String s) {
    if (s.equals("")) {
      if (type.equals("leaf")) {
        return true;
      } else {
        return false;
      }
    } else {
      if (s.charAt(0) == '0')
        return left.isPath(s.substring(1));
      else
        return right.isPath(s.substring(1));
    }
  }

  public byte getByte(String s) {
    if (s.equals("")) {
      if (type.equals("leaf")) {
        return data;
      } else {
        System.out.println("ERROR in ByteNode: getByte(string s)");
        return 0;
      }
    } else {
      if (s.charAt(0) == '0')
        return left.getByte(s.substring(1));
      else
        return right.getByte(s.substring(1));
    }
  }

  public void insertEoF() {
    if(right.type.equals("leaf")) {
      ByteNode newRight = new ByteNode(right, new ByteNode());
      right = newRight;
    } else {
      right.insertEoF();
    }
  }

  public String EoFstring() {
    String s = "";
    ByteNode temp = this;
    while (!(temp.type.equals("EoF"))) {
      s = s + "1";
      temp = temp.right;
    }
    return s;
  }

}
