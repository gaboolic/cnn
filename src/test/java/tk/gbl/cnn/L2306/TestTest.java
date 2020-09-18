package tk.gbl.cnn.L2306;

import tk.gbl.cnn.core.Cnn;
import tk.gbl.cnn.core.DataItem;
import tk.gbl.cnn.util.MatrixUtil;
import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;
import tk.gbl.cnn.util.image.Cut;
import tk.gbl.cnn.util.image.ImageFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Date: 2016/7/1
 * Time: 15:12
 *
 * @author Tian.Dong
 */
public class TestTest {
  static String labels = "23456789ABCDEFGHKMNPQRSTUVWXYZ";

  public static void main(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\12306_cnn.obj"));
////    ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/work/cnn/12306_cnn.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();

    int singleRight = 0;
    int right = 0;
    int allCount = 0;
    int count = 0;

    long allTime = 0;

    File testPath = new File("F:\\验证码\\code_all2");
    for (File file : testPath.listFiles()) {
      long startTime = System.currentTimeMillis();

      String realResult = file.getName().split("\\.")[0];
      int[][] image = Binary.deal(file);
      image = Cut.cut(image);
      int zero = 0;
      for (int[] img : image) {
        for (int a : img) {
          zero += a;
        }
      }
      if (zero == 0) {
        System.out.println();
      }
      List<int[][]> parts = null;
      try {
        parts = Cut.cut4Parts(image);
      } catch (Exception e) {
        e.printStackTrace();
      }
      String result = "";
      for (int c = 0; c < parts.size(); c++) {
        int[][] part = parts.get(c);
        int label = labels.indexOf(realResult.charAt(c));

        DataItem dataItem = new DataItem();
        dataItem.setLabel(label);
        double[][] changeImage = MatrixUtil.standard32(MatrixUtil.int2double(part));
        dataItem.setImage(changeImage);
        dataItem.setWidth(32);
        dataItem.setHeight(32);

        int predictResult = cnn.predict(dataItem);
        if (predictResult == label) {
          singleRight++;
        }
        result += labels.charAt(predictResult);
        count++;
      }
      if (realResult.equals(result)) {
        right++;
      } else {
        //ArrayToImage.createImage(image, new File("F:\\验证码\\code_all_wrong", result + ".png"));
      }
      allCount++;
      allTime = allTime + (System.currentTimeMillis() - startTime);

      if (allCount % 500 == 0) {
        System.out.println("单个识别率:" + singleRight + "," + count + "," + (singleRight * 1.0 / count));
        System.out.println("总识别率:" + right + "," + allCount + "," + (right * 1.0 / allCount));
        System.out.println("总时间:" + allTime);
        System.out.println("平均时间:" + (allTime * 1.0 / allCount));
      }
    }

    System.out.println("单个识别率:" + singleRight + "," + count + "," + (singleRight * 1.0 / count));
    System.out.println("总识别率:" + right + "," + allCount + "," + (right * 1.0 / allCount));
    System.out.println("总时间:" + allTime);
    System.out.println("平均时间:" + (allTime * 1.0 / allCount));
  }
}
