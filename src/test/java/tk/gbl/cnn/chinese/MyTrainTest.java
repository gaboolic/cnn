package tk.gbl.cnn.chinese;

import tk.gbl.cnn.HWDBGntFile;
import tk.gbl.cnn.ImageDbFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.CoderUtil;
import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.Output;
import tk.gbl.cnn.util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;

/**
 * 45217
 * 55289
 * <p/>
 * Date: 2016/6/16
 * Time: 17:00
 *
 * @author Tian.Dong
 */
public class MyTrainTest {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1)) //32*32
        //C1 5*5卷积核 步长为1 数量为6 28
        .addLayer(new ConvolutionLayer("C1", 100, new KernelBuilder(5, 5, 1)))
            //S2 采样 输出14
        .addLayer(new SamplingLayer("S2", 100, new Sampling(2, 2)))
            //C3 5*5卷积核 10
        .addLayer(new ConvolutionLayer("C3", 200, new KernelBuilder(5, 5, 1)))
            //S4 采样 输出5
        .addLayer(new SamplingLayer("S4", 200, new Sampling(2, 2)))
            //5 输出 1
        .addOutputLayer(new OutputLayer("Output", 500, new KernelBuilder(5, 5, 1)));
    cnn.init();

//    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\chinese_cnn.obj"));
//    Cnn cnn = (Cnn) in.readObject();
//    in.close();
    ImageDbFile imageDbFile = new ImageDbFile("E:\\DT\\cnn\\chinese");
    System.out.println("size:" + imageDbFile.getDataItemList().size());

    for (int repeat = 1; repeat <= 20; repeat++) {
      int right = 0;
      int allCount = 0;
      for (int i = 0; i < imageDbFile.getDataItemList().size(); i++) {
        DataItem dataItem = imageDbFile.getDataItemList().get(i);
        //ImageUtil.writeImage(dataItem.getImage(),CoderUtil.gb2312ToUtf8(dataItem.getLabel())+".png");
        cnn.train(dataItem);
        double[][][] outputs = cnn.getOutputLayer().getOutputs();
        int output = Util.getMaxIndex(outputs);
        int realResult = dataItem.getLabel();
        if (output == realResult) {
          right++;
        }
        System.out.println(output + "-" + realResult + ",output:" +imageDbFile.getIzMap().get(output) + "," + "real:" + imageDbFile.getIzMap().get((realResult)));
        if (i % 100 == 0) {
          System.out.print(".");
        }
        allCount++;
      }
      System.out.println();
      System.out.println(right + "," + allCount + ",正确率:" + (1.0 * right / allCount));


      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\DT\\cnn_json\\chinese_cnn.obj"));
      out.writeObject(cnn);
      out.close();
    }
  }
}
