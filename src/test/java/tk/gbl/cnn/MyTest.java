package tk.gbl.cnn;

import tk.gbl.cnn.MnistDbFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.Util;

import java.io.*;
import java.util.Arrays;

/**
 * Date: 2016/6/2
 * Time: 17:38
 *
 * @author Tian.Dong
 */
public class MyTest {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1))
        //C1 5*5卷积核 步长为1 数量为6
        .addLayer(new ConvolutionLayer("C1", 6, new KernelBuilder(5, 5, 1)))
            //S2 采样 输出12*12
        .addLayer(new SamplingLayer("S2",6, new Sampling(2, 2)))
            //C3 5*5卷积核 输出8*8
        .addLayer(new ConvolutionLayer("C3", 12, new KernelBuilder(5, 5, 1)))
            //S4 采样 输出4*4
        .addLayer(new SamplingLayer("S4",12, new Sampling(2, 2)))
            //5 输出
        .addOutputLayer(new ConvolutionLayer("Output", 10, new KernelBuilder(4, 4, 1)));//4 输出层
      cnn.init();

//    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\cnn.obj"));
//    Cnn cnn = (Cnn) in.readObject();
//    in.close();

    MnistDbFile trainFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "images-idx3-ubyte", "labels-idx1-ubyte");
    int right = 0;
    for (int i = 0; i < 100; i++) {
      DataItem dataItem = trainFile.getDataItem();
      cnn.train(dataItem);
      double[][][] outputs = cnn.getOutputLayer().getOutputs();
      int output = Util.getMaxIndex(outputs);
      int realResult = dataItem.getLabel();
      if (output == realResult) {
        right++;
      }
      System.out.println(realResult + "," + output);
    }
    System.out.println(right + "," + 100);

    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\DT\\cnn_json\\cnn.obj"));
    out.writeObject(cnn);
    out.close();



    /*MnistDbFile testFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "t10k-images-idx3-ubyte", "t10k-labels-idx1-ubyte");
    int right = 0;
    for (int i = 0; i < 100; i++) {
      DataItem dataItem = testFile.getDataItem();
      int output = cnn.predict(dataItem);
      int realResult = dataItem.getLabel();
      if (output == realResult) {
        right++;
      }
      System.out.println(realResult + "," + output);
    }
    System.out.println(right + "," + 100);*/
  }

  public static void main2(String[] args) throws IOException {
    File path = new File("C:\\Users\\tian\\MNIST");
    File trainData = new File(path, "images-idx3-ubyte");
    RandomAccessFile trainFile = new RandomAccessFile(trainData, "r");

    File trainLabel = new File(path, "labels-idx1-ubyte");
    File testData = new File(path, "t10k-images-idx3-ubyte");
    File testLabel = new File(path, "images-idx3-ubyte");

    MnistDbFile train = new MnistDbFile("C:\\Users\\tian\\MNIST", "images-idx3-ubyte", "labels-idx1-ubyte");
    double[][] data = train.readImage();
    double[][] sub = new double[14][14];
    for (int h = 0; h < data.length - 1; h += 2) {
      for (int w = 0; w < data[h].length - 1; w += 2) {
        double upLeft = data[h][w];
        double upRight = data[h][w + 1];
        double downLeft = data[h + 1][w];
        double downRight = data[h + 1][w + 1];

        double max = max(upLeft, upRight, downLeft, downRight);
        sub[h / 2][w / 2] = max;
      }
    }

    train.writeImage(train.arrayToImage(sub), new File("E:\\DT\\MNIST"));
    train.writeImage(train.arrayToImage(data), new File("E:\\DT\\MNIST"));

  }

  private static double max(double upLeft, double upRight, double downLeft, double downRight) {
    double[] array = {upLeft, upRight, downLeft, downRight};
    Arrays.sort(array);
    if (Math.abs(array[0]) > Math.abs(array[3])) {
      return array[0];
    }
    return array[3];
  }
}
