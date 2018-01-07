public class FreqElement {

  // used for counting frequencies
  byte b;
  int  frequency = 0;

  public FreqElement (byte b) {
    this.b = b;
    this.frequency = 1;
  }

  public FreqElement (byte b, int f) {
    this.b = b;
    this.frequency = f;
  }

  public void addFreq () {
    this.frequency += 1;
  }

  public String toString() {
    String answer = "(" + b + "," + frequency + ")";
    return answer;
  }

} 
