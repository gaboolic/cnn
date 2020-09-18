package tk.gbl.cnn.chinese;

import tk.gbl.cnn.util.MD5Util;
import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;
import tk.gbl.cnn.util.image.Cut;
import tk.gbl.cnn.util.image.CutChineseUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Date: 2016/7/7
 * Time: 9:58
 *
 * @author Tian.Dong
 */
public class CutTest {
  public static void main(String[] args) throws IOException {
    File path = new File("F:\\验证码\\new12306\\code_new_type_cut");
    for (File file : path.listFiles()) {
      int[][] image = Binary.dealWhite(file);
      image = Cut.cut(image);

      List<int[][]> parts = CutChineseUtil.cutParts(image);
      for (int[][] part : parts) {
        ArrayToImage.createImage(part, new File("F:\\验证码\\new12306\\code_new_type_cut_single", System.currentTimeMillis() + ".png"));
      }
    }
  }

  public static void main3(String[] args) throws IOException {
    File file = new File("F:\\验证码\\new12306\\code_new_type_cut\\1_b109e7e32bea46a71747efc180982e4a.png");
    int[][] image = Binary.dealWhite(file);
    image = Cut.cut(image);

    List<int[][]> parts = CutChineseUtil.cutParts(image);
    for (int[][] part : parts) {
      ArrayToImage.createImage(part, new File("F:\\验证码\\new12306\\code_new_type_cut_single", System.currentTimeMillis() + ".png"));
    }
  }

  public static void main2(String[] args) throws IOException {
    File path = new File("F:\\验证码\\new12306\\code_new_type");
    for (File file : path.listFiles()) {
      int[][] image = Binary.dealWhite(file);
      image = Cut.cut(image);
      int width = image[0].length;
      int count = (int) Math.round(width * 1.0 / 15);
      if (count == 1 && width > 20) {
        count = 2;
      }
      ArrayToImage.createImage(image, "F:\\验证码\\new12306\\code_new_type_cut\\" + count + "_" + file.getName().split("_")[0] + ".png");
    }
  }
}
