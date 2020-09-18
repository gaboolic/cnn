package tk.gbl.cnn;

import tk.gbl.cnn.MnistDbFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.Util;

import java.io.*;

/**
 * Date: 2016/6/13
 * Time: 16:02
 *
 * @author Tian.Dong
 */
public class TrainTest {
  public static void main(String[] args) throws Exception {
    /*Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1))
        //C1 5*5卷积核 步长为1 数量为6
        .addLayer(new ConvolutionLayer("C1", 6, new KernelBuilder(5, 5, 1)))
            //S2 采样 输出12*12
        .addLayer(new SamplingLayer("S2", 6, new Sampling(2, 2)))
            //C3 5*5卷积核 输出8*8
        .addLayer(new ConvolutionLayer("C3", 12, new KernelBuilder(5, 5, 1)))
            //S4 采样 输出4*4
        .addLayer(new SamplingLayer("S4", 12, new Sampling(2, 2)))
            //5 输出
        .addOutputLayer(new ConvolutionLayer("Output", 26, new KernelBuilder(4, 4, 1)));//4 输出层
    cnn.init();*/
    Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1))
        //C1 5*5卷积核 步长为1 数量为6 28
        .addLayer(new ConvolutionLayer("C1", 25, new KernelBuilder(5, 5, 1)))
            //S2 采样 输出14
        .addLayer(new SamplingLayer("S2", 25, new Sampling(2, 2)))
            //C3 5*5卷积核 10
        .addLayer(new ConvolutionLayer("C3", 50, new KernelBuilder(5, 5, 1)))
            //S4 采样 输出5
        .addLayer(new SamplingLayer("S4", 50, new Sampling(2, 2)))
            //D5 全连接 输出1
        .addLayer(new DenseLayer("D5", 100, new KernelBuilder(4, 4, 1)))
            //Output 输出 1
        .addOutputLayer(new OutputLayer("Output", 500, new KernelBuilder(1, 1, 1)));
    cnn.init();

//    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\cnn.obj"));
//    Cnn cnn = (Cnn) in.readObject();
//    in.close();

    int trainCount = 12000;
    long loadStartTime = System.currentTimeMillis();
//    System.out.println("导入数据");
//    MnistDbFile trainFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "images-idx3-ubyte", "labels-idx1-ubyte", trainCount);
//    System.out.println("导入完毕," + (System.currentTimeMillis() - loadStartTime) + "ms");

    System.out.println("导入数据");
    MnistFile trainFile = new MnistFile("E:\\DT\\cnn", "train.format");
    System.out.println("导入完毕," + (System.currentTimeMillis() - loadStartTime) + "ms");

    for (int iter = 0; iter < 8; iter++) {
      int right = 0;
      for (int i = 0; i < trainCount; i++) {
        DataItem dataItem = trainFile.getDataItemList().get(Util.random(trainCount));
        cnn.train(dataItem);
        double[][][] outputs = cnn.getOutputLayer().getOutputs();
        int output = Util.getMaxIndex(outputs);
        int realResult = dataItem.getLabel();
        if (output == realResult) {
          right++;
        }
        if (i % 100 == 0) {
          System.out.print(".");
          //System.out.println(output+"-"+realResult);
        }
      }
      System.out.println();
      System.out.println("第" + iter + "次:" + right + "," + trainCount + ",正确率:" + (1.0 * right / trainCount));
    }
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\DT\\cnn_json\\chinese_cnn.obj"));
    out.writeObject(cnn);
    out.close();
  }
}
