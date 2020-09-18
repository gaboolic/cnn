package tk.gbl.cnn.L2306;

import org.junit.Test;
import tk.gbl.cnn.Image12306DbFile;
import tk.gbl.cnn.ImageDbFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.MatrixUtil;
import tk.gbl.cnn.util.Util;
import tk.gbl.cnn.util.image.ArrayToImage;

import java.io.*;

/**
 * 45217
 * 55289
 * <p/>
 * Date: 2016/6/16
 * Time: 17:00
 *
 * @author Tian.Dong
 */
public class L2306TrainTest {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
//    Cnn cnn = new Cnn();
//    cnn.addLayer(new InputLayer(1))
//        //C1 5*5卷积核 步长为1 数量为6 32
//        .addLayer(new ConvolutionLayer("C1", 25, new KernelBuilder(5, 5, 1)))
//            //S2 采样 输出14
//        .addLayer(new SamplingLayer("S2", 25, new Sampling(2, 2)))
//            //C3 5*5卷积核 10
//        .addLayer(new ConvolutionLayer("C3", 50, new KernelBuilder(5, 5, 1)))
//            //S4 采样 输出5
//        .addLayer(new SamplingLayer("S4", 50, new Sampling(2, 2)))
//            //D5 全连接 输出1
//        .addLayer(new DenseLayer("D5", 100, new KernelBuilder(5, 5, 1)))
//            //Output 输出 1
//        .addOutputLayer(new OutputLayer("Output", 30, new KernelBuilder(1, 1, 1)));//26
//    cnn.init();

    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\12306_cnn.obj"));
////    ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/work/cnn/12306_cnn.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();
    Image12306DbFile imageDbFile = new Image12306DbFile("F:\\验证码\\template");
//    ImageDbFile imageDbFile = new ImageDbFile("/home/work/cnn/chinese");
    System.out.println("size:" + imageDbFile.getDataItemList().size());

    for (int repeat = 0; repeat <= 30; repeat++) {
      int right = 0;
      int allCount = 0;
      for (int i = 0; i < 10000; i++) {
        int index = (int) (Math.random()*imageDbFile.getDataItemList().size());
        DataItem dataItem = imageDbFile.getDataItemList().get(index);
        cnn.train(dataItem);
        double[][][] outputs = cnn.getOutputLayer().getOutputs();
        int output = Util.getMaxIndex(outputs);
        int realResult = dataItem.getLabel();
        if (output == realResult) {
          right++;
        }
        allCount++;
        if (i % 300 == 0) {
          System.out.print(".");
          System.out.println("第" + repeat + "次迭代," + right + "," + allCount + ",正确率:" + (1.0 * right / allCount));
        }
      }
      System.out.println();
      System.out.println("第" + repeat + "次迭代," + right + "," + allCount + ",正确率:" + (1.0 * right / allCount));
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\DT\\cnn_json\\12306_cnn.obj"));
//      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/home/work/cnn/12306_cnn.obj"));
      out.writeObject(cnn);
      out.close();
    }
  }

}
