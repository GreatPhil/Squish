public class byteConverter {

  public byteConverter() {
  }

  public String toBits(byte byt, byte L) {
    String str = "";
    byte multiplier = 1;
    for (byte i = 0; i < L; i++) {
      str = str + ((byt & multiplier) / multiplier);
      multiplier *= 2;
    }
    return str;
  }

  // Str must be 8 digits long
  public byte toBytes(String str) {
    int multiplier = 1;
    int answer = 0;
    for (int i = 0; i < 8; i++) {
      if (str.charAt(i) == '1')
      answer += multiplier;
      multiplier *= 2;
    }
    return (new Integer(answer)).byteValue();
  }

}
