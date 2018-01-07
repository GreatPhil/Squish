import java.util.*;
import java.io.*;

public class TreeMaker {

  Vector ByteVector = new Vector();
  FreqElement [] FreqArray = new FreqElement[256]; // added FOR SPEED

  public TreeMaker(){
  }

  public HuffTree makeTree(FileInputStream fis) {
    ByteVector = new Vector();
    FreqArray = new FreqElement[257]; // added FOR SPEED
    try {
      countFrequency(fis);
    } catch (IOException e)
    {System.out.println("ERROR: File could not be read in TreeMaker");}
    sortFrequency();
    return VectortoTree();
  }

  private void countFrequency(FileInputStream fis) throws IOException{

    int length = fis.available();
    for (int i = 0; i < length; i ++) {

      Integer integ = new Integer(fis.read());
      byte current = integ.byteValue();

      // the OLD method using a Vector... was slower:
      //boolean added = false;
      //for (int j = 0; j < ByteVector.size(); j++) {
      //  if (current == ((FreqElement)ByteVector.elementAt(j)).b) {
      //    ((FreqElement)ByteVector.elementAt(j)).addFreq();
      //    added = true;
      //    break;
      //  }
      //}
      //if (added == false)
      //ByteVector.add(new FreqElement(current));


      // FOR SPEED:
      if (FreqArray[integ.intValue()] == null) {
        FreqElement fe = new FreqElement(current);
        FreqArray[integ.intValue()] = fe;
      } else {
        FreqElement fe = new FreqElement(current);
        fe.addFreq();
      }
    }
  }

  private void sortFrequency() {
    Vector answer = new Vector();

    // Again... the OLD method is shown here:
    // for (int i = 0; i < ByteVector.size(); i++) {
    //   FreqElement current = (FreqElement)ByteVector.elementAt(i);

    for (int i = 0; i < 257; i++) {
      if (FreqArray[i] == null)
      { }
      else {
        FreqElement current = (FreqElement)FreqArray[i];

        int oldsize = answer.size();
        for (int j = 0; j < oldsize; j++) {
          if (current.frequency <
          ((FreqElement)answer.elementAt(j)).frequency) {
            answer.insertElementAt(current, j);
            break;
          }
        }
        if (answer.size() == oldsize)
        answer.add(current);
      }
    }
    ByteVector = answer;
  }

  private HuffTree VectortoTree() {
    Vector NodeVector = new Vector();

    for (int i = 0; i < ByteVector.size(); i++) {
      FreqElement current = (FreqElement)ByteVector.elementAt(i);
      NodeVector.add(new ByteNode(current.b, current.frequency));
    }

    int leaves = NodeVector.size();

    while (NodeVector.size() > 2) {

      ByteNode min1 = (ByteNode)NodeVector.elementAt(0);
      ByteNode min2 = (ByteNode)NodeVector.elementAt(1);
      NodeVector.remove(0);
      NodeVector.remove(0);
      ByteNode merge = new ByteNode(min2, min1);
      int oldsize = NodeVector.size();
      for (int j = 0; j < oldsize; j++) {
        if (merge.freq < ((ByteNode)NodeVector.elementAt(j)).freq) {
          NodeVector.insertElementAt(merge, j);
          break;
        }
      }
      if (NodeVector.size() == oldsize)
        NodeVector.add(merge);
    }

    HuffTree answer = new HuffTree(leaves);

    if (leaves == 1) {
      answer.root = new ByteNode ("root",
      (ByteNode)NodeVector.elementAt(0),
      new ByteNode());
    } else {
      answer.root = new ByteNode("root",
      (ByteNode)NodeVector.elementAt(1),
      (ByteNode)NodeVector.elementAt(0));
      answer.root.insertEoF();
    }

    return answer;

  }

}
