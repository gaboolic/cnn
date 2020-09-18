package tk.gbl.cnn.mnist;

import tk.gbl.cnn.MnistCsvFile;
import tk.gbl.cnn.MnistDbFile;
import tk.gbl.cnn.core.BaseLayer;
import tk.gbl.cnn.core.Cnn;
import tk.gbl.cnn.core.DataItem;
import tk.gbl.cnn.util.Util;

import java.io.*;

/**
 * Date: 2016/6/13
 * Time: 16:05
 *
 * @author Tian.Dong
 */
public class TestTest {
  public static void main(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\mnist.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();

    long loadStartTime = System.currentTimeMillis();
//    System.out.println("导入数据");
//    MnistDbFile trainFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "images-idx3-ubyte", "labels-idx1-ubyte", trainCount);
//    System.out.println("导入完毕," + (System.currentTimeMillis() - loadStartTime) + "ms");

    System.out.println("导入数据");
    MnistCsvFile trainFile = new MnistCsvFile("C:\\Users\\tian\\Downloads", "test.csv", false);
    System.out.println("导入完毕," + (System.currentTimeMillis() - loadStartTime) + "ms");

    File outputFile = new File("C:\\Users\\tian\\Downloads\\output.csv");
    PrintWriter printWriter = new PrintWriter(new FileWriter(outputFile));
    printWriter.println("ImageId,Label");
    for (int i = 0; i < trainFile.getDataItemList().size(); i++) {
      DataItem dataItem = trainFile.getDataItemList().get(i);
      int output = cnn.predict(dataItem);
      printWriter.println((i+1)+","+output);
    }
    printWriter.close();
  }
}
