package tk.gbl.cnn.mnist;

import tk.gbl.cnn.MnistCsvFile;
import tk.gbl.cnn.MnistFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.MatrixUtil;
import tk.gbl.cnn.util.Util;
import tk.gbl.cnn.util.image.ArrayToImage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Date: 2016/6/13
 * Time: 16:02
 *
 * @author Tian.Dong
 */
public class TrainTest {
  public static void main(String[] args) throws Exception {
//    Cnn cnn = new Cnn();
//    cnn.addLayer(new InputLayer(1))
//        //C1 5*5卷积核 步长为1 数量为6 28
//        .addLayer(new ConvolutionLayer("C1", 25, new KernelBuilder(5, 5, 1)))
//            //S2 采样 输出14
//        .addLayer(new SamplingLayer("S2", 25, new Sampling(2, 2)))
//            //C3 5*5卷积核 10
//        .addLayer(new ConvolutionLayer("C3", 50, new KernelBuilder(5, 5, 1)))
//            //S4 采样 输出5
//        .addLayer(new SamplingLayer("S4", 50, new Sampling(2, 2)))
//            //D5 全连接 输出1
//        .addLayer(new DenseLayer("D5", 100, new KernelBuilder(4, 4, 1)))
//            //Output 输出 1
//        .addOutputLayer(new OutputLayer("Output", 10, new KernelBuilder(1, 1, 1)));
//    cnn.init();

    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\mnist.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();

    long loadStartTime = System.currentTimeMillis();
//    System.out.println("导入数据");
//    MnistDbFile trainFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "images-idx3-ubyte", "labels-idx1-ubyte", trainCount);
//    System.out.println("导入完毕," + (System.currentTimeMillis() - loadStartTime) + "ms");

    System.out.println("导入数据");
    MnistCsvFile trainFile = new MnistCsvFile("C:\\Users\\tian\\Downloads", "train.csv", true);
    System.out.println("导入完毕," + (System.currentTimeMillis() - loadStartTime) + "ms");
//    int trainCount = trainFile.getDataItemList().size();
    int trainCount = 12000;
    BaseLayer.setALPHA(0.01);
    for (int iter = 0; iter < 3; iter++) {
      int right = 0;
      for (int i = 0; i < trainCount; i++) {
        DataItem dataItem = trainFile.getDataItemList().get(Util.random(trainCount));
        DataItem changeDateItem = new DataItem();
        changeDateItem.setLabel(dataItem.getLabel());
        changeDateItem.setImage(MatrixUtil.shapeChange(dataItem.getImage()));

        ArrayToImage.createImage(MatrixUtil.double2int(dataItem.getImage()),"C:\\Users\\tian\\Downloads\\dateItem.png");
        ArrayToImage.createImage(MatrixUtil.double2int(changeDateItem.getImage()),"C:\\Users\\tian\\Downloads\\changeDateItem.png");

        cnn.train(changeDateItem);
        double[][][] outputs = cnn.getOutputLayer().getOutputs();
        int output = Util.getMaxIndex(outputs);
        int realResult = dataItem.getLabel();
        if (output == realResult) {
          right++;
        }
        if (i % 100 == 0) {
          System.out.print(".");
//          System.out.println(output+"-"+realResult);
        }
      }
      System.out.println();
      System.out.println("第" + iter + "次:" + right + "," + trainCount + ",正确率:" + (1.0 * right / trainCount));
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\DT\\cnn_json\\mnist.obj"));
      out.writeObject(cnn);
      out.close();
    }
  }
}
