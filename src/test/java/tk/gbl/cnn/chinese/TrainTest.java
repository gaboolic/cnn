package tk.gbl.cnn.chinese;

import tk.gbl.cnn.HWDBGntFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.CoderUtil;
import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.Util;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 45217
 * 55289
 * <p>
 * Date: 2016/6/16
 * Time: 17:00
 *
 * @author Tian.Dong
 */
public class TrainTest {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1)) //64*64
        //C1 5*5卷积核 步长为1 数量为6 60
        .addLayer(new ConvolutionLayer("C1", 6, new KernelBuilder(5, 5, 1)))
            //S2 采样 输出30
        .addLayer(new SamplingLayer("S2", 6, new Sampling(2, 2)))
            //C3 5*5卷积核 输出26
        .addLayer(new ConvolutionLayer("C3", 16, new KernelBuilder(5, 5, 1)))
            //S4 采样 输出13
        .addLayer(new SamplingLayer("S4", 16, new Sampling(2, 2)))
            //5 输出 9
        .addLayer(new ConvolutionLayer("C5", 100, new KernelBuilder(5, 5, 1)))
            //6 输出4
        .addLayer(new SamplingLayer("S6", 100, new Sampling(2, 2)))
        .addOutputLayer(new ConvolutionLayer("Output", 3755, new KernelBuilder(4, 4, 1)));//4 输出层
    cnn.init();

//    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\chinese_cnn.obj"));
//    Cnn cnn = (Cnn) in.readObject();
//    in.close();

    for (int repeat = 1; repeat < 4; repeat++) {
      HWDBGntFile hwdbGntFile = new HWDBGntFile("E:\\DT\\cnn\\HWDB1.1tst_gnt", "124" + repeat + "-c.gnt");
//    System.out.println(hwdbGntFile.getDataItemList().get(0).getLabel());
//    System.out.println(hwdbGntFile.getDataItemList().get(hwdbGntFile.getDataItemList().size()-1).getLabel());
      Collections.sort(hwdbGntFile.getDataItemList(), new Comparator<DataItem>() {
        @Override
        public int compare(DataItem o1, DataItem o2) {
          return o1.getLabel() - o2.getLabel();
        }
      });

      System.out.println("size:" + hwdbGntFile.getDataItemList().size());

      int right = 0;
      int allCount = 0;
      for (int i = 0; i < hwdbGntFile.getDataItemList().size(); i++) {
        DataItem dataItem = hwdbGntFile.getDataItemList().get(i);
        dataItem.setImage(ImageUtil.standard(dataItem.getMatrix()));
        //ImageUtil.writeImage(dataItem.getImage(),CoderUtil.gb2312ToUtf8(dataItem.getLabel())+".png");
        dataItem.setLabel(CoderUtil.gb2312Map.get(CoderUtil.gb2312ToUtf8(dataItem.getLabel())));

        cnn.train(dataItem);
        double[][][] outputs = cnn.getOutputLayer().getOutputs();
        int output = Util.getMaxIndex(outputs);
        int realResult = dataItem.getLabel();
        if (output == realResult) {
          right++;
        }
        System.out.println(output + "-" + realResult + ",output:" + CoderUtil.gb2312Map2.get(output) + "," + "real:" + CoderUtil.gb2312Map2.get(realResult));
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
