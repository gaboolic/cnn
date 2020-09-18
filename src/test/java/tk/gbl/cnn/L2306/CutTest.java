package tk.gbl.cnn.L2306;

import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;
import tk.gbl.cnn.util.image.Cut;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Date: 2016/7/4
 * Time: 15:01
 *
 * @author Tian.Dong
 */
public class CutTest {
  public static void main(String[] args) throws IOException {
    File file = new File("F:\\验证码\\numbertest\\myyzm.jpg");
    int[][] image = Binary.deal(file);
    image = Cut.cut(image);
    ArrayToImage.createImage(image,new File("F:\\验证码\\numbertest\\binary.jpg"));

    List<int[][]> imgs = Cut.cutParts(image,5);
    for(int i=0;i<imgs.size();i++) {
      int[][] img = imgs.get(i);
      ArrayToImage.createImage(img,new File("F:\\验证码\\numbertest\\cut"+i+".jpg"));
    }
  }
}
