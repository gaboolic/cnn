package tk.gbl.cnn.L2306;

import tk.gbl.cnn.core.Cnn;
import tk.gbl.cnn.core.DataItem;
import tk.gbl.cnn.util.MatrixUtil;
import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;
import tk.gbl.cnn.util.image.Cut;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Date: 2016/7/4
 * Time: 15:22
 *
 * @author Tian.Dong
 */
public class SingleTest {

  static String labels = "23456789ABCDEFGHKMNPQRSTUVWXYZ";

  public static void main(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\12306_cnn.obj"));
////    ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/work/cnn/12306_cnn.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();

    File file = new File("F:\\验证码\\test", "bbb.png");
    int[][] image = Binary.deal(file);
    ArrayToImage.createImage(image, new File("F:\\验证码\\test", "binary.png"));
    int[][] cutImage = Cut.cut(image);
    ArrayToImage.createImage(cutImage, new File("F:\\验证码\\test", "cutImage.png"));

    List<int[][]> parts = Cut.cut4Parts(cutImage);

    int i = 0;
    String result = "";
    for (int[][] part : parts) {
      ArrayToImage.createImage(part, new File("F:\\验证码\\test", i++ + "part.png"));

      DataItem dataItem = new DataItem();
      double[][] changeImage = MatrixUtil.standard32(MatrixUtil.int2double(part));
      dataItem.setImage(changeImage);
      dataItem.setWidth(32);
      dataItem.setHeight(32);

      int predictResult = cnn.predict(dataItem);
      result += labels.charAt(predictResult);

    }
    System.out.println(result);
  }
}
