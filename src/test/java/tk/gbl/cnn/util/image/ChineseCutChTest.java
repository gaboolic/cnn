package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Date: 2016/7/12
 * Time: 14:44
 *
 * @author Tian.Dong
 */
public class ChineseCutChTest {

  public static void main111(String[] args) throws Exception {
    File path = new File("F:\\验证码\\new12306\\binary_test");
    for (int i = 0; i < path.listFiles().length; i++) {
      File file = path.listFiles()[i];
//      file = new File("F:\\验证码\\new12306\\cut_ch_test\\光盘190.png");
      String fileName = file.getName();

      List<int[][]> images = ChineseCutUtil.cutCharacter2(file);
      for (int k = 0; k < images.size(); k++) {
        int[][] chImage = images.get(k);
        ArrayToImage.createImage(chImage, "F:\\验证码\\new12306\\cut_ch_test\\" + fileName.charAt(k) + (int) (Math.random() * 1000) + ".png");
      }
      if (i > 10)
        System.exit(0);
    }
  }

  public static void main(String[] args) throws IOException {
    File path = new File("F:\\验证码\\new12306\\binary_test");
    Map<Integer, Set<Integer>> map = new HashMap<>();

    for (int i = 0; i < path.listFiles().length; i++) {
      File file = path.listFiles()[i];
//      file = new File("F:\\验证码\\new12306\\cut_ch_test\\光盘190.png");
      String fileName = file.getName();

      int[][] image = Binary.deal(file);
      int wight = image[0].length;
      int chCount = fileName.split("[0-9]")[0].length();

      Set<Integer> set = map.get(chCount);
      if (set == null) {
        set = new TreeSet<>();
      } else {

      }
      set.add(wight);
      map.put(chCount, set);

      if(chCount == 2 && wight>40){
        System.out.println(fileName);
      }
    }

    System.out.println(map);
  }
}
