public class TreeConverter {

  public String S;
  private byte L;
  public byteConverter bc;

  public TreeConverter(byte L) {
    S = "";
    this.L = L;
  }

  public String TreeToString(HuffTree ht) {
    S = "";
    return TreeToString(ht.root);
  }

  public String TreeToString(ByteNode b) {
    S = S + operate(b);
    if (b.type.equals("root") || b.type.equals("inner")) {
      TreeToString(b.left);
      TreeToString(b.right);
    }
    return S;
  }

  public String operate(ByteNode b) {
    byteConverter byco = new byteConverter();
    String ans = null;

    if (b.type.equals("leaf")) {
      ans =  "1" + byco.toBits(b.data, L);
    } else if (b.type.equals("EoF")) {
      ans = "1";
    } else if (b.type.equals("inner") || b.type.equals("root")) {
      ans = "0";
    }
    return ans;
  }

  public HuffTree recreateTree(String s, int L) {
    S = s;
    bc = new byteConverter();
    ByteNode b = recreateTree();
    b.type = "root";
    HuffTree HT = new HuffTree(L);
    HT.root = b;
    return HT;
  }

  public ByteNode recreateTree() {

    if (S.substring(0,1).equals("0")) {
      S = S.substring(1);

      ByteNode LEF = recreateTree();
      ByteNode RIG = recreateTree();

      return new ByteNode(LEF, RIG);

    } else {

      if (S.equals("1")) // if it is EoF?
        return new ByteNode();
      else {
        ByteNode answer = new ByteNode(bc.toBytes(S.substring(1,9)), 1);
        S = S.substring(9);
        return answer;
      }
    }
  }
}
