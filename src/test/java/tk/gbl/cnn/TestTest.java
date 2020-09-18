package tk.gbl.cnn;

import tk.gbl.cnn.MnistDbFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.Util;

import java.io.*;

/**
 * Date: 2016/6/13
 * Time: 16:05
 *
 * @author Tian.Dong
 */
public class TestTest {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\cnn.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();

    MnistDbFile testFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "t10k-images-idx3-ubyte", "t10k-labels-idx1-ubyte");
    int testCount = 10000;
    int right = 0;
    for (int i = 0; i < testCount; i++) {
      DataItem dataItem = testFile.getDataItem();
      int output = cnn.predict(dataItem);
      int realResult = dataItem.getLabel();
      if (output == realResult) {
        right++;
      }
      if (i % 100 == 0) {
        System.out.print(".");
      }
    }
    System.out.println();
    System.out.println(right + "," + testCount);
    System.out.println(1.0 * right / testCount);
  }
}
