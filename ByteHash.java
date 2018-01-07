public class ByteHash {

  // Used or storing bytes in a HashTable as Objects
  byte val;

  public ByteHash(byte v) {
    val = v;
  }

  public boolean equals(ByteHash bh) {
    return (val == bh.val);
  }

}
