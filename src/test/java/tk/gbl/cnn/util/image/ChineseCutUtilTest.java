package tk.gbl.cnn.util.image;

import tk.gbl.cnn.util.FatArrayList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 2016/7/12
 * Time: 14:44
 *
 * @author Tian.Dong
 */
public class ChineseCutUtilTest {
  public static void main2(String[] args) throws IOException {
    File file = new File("F:\\验证码\\new12306\\马-蜗牛_94.png");
    String fileName = file.getName();
    String f1 = fileName.split("_")[0].split("-")[0];
    String f2 = fileName.split("_")[0].split("-")[1];
    BufferedImage image = ImageIO.read(file);
    List<BufferedImage> images = ChineseCutUtil.cutWord(image);
    ImageIO.write(images.get(0), "PNG", new File("F:\\验证码\\new12306\\cut_test\\" + f1 + (int) (Math.random() * 1000) + ".png"));
    ImageIO.write(images.get(1), "PNG", new File("F:\\验证码\\new12306\\cut_test\\" + f2 + (int) (Math.random() * 1000) + ".png"));
  }

  public static void main(String[] args) throws Exception {
    File path = new File("F:\\验证码\\new12306\\type");
    int sum=0;
    for (int i = 0; i < path.listFiles().length; i++) {
      File file = path.listFiles()[i];
      String fileName = file.getName();
      if (!fileName.contains("-")) {
        continue;
      }
      sum++;
      String f1 = fileName.split("_")[0].split("-")[0];
      String f2 = fileName.split("_")[0].split("-")[1];
      BufferedImage image = ImageIO.read(file);
      List<BufferedImage> images = ChineseCutUtil.cutWord(image);
      ImageIO.write(images.get(0), "PNG", new File("F:\\验证码\\new12306\\cut_test\\" + f1 + (int) (Math.random() * 1000) + ".png"));
      ImageIO.write(images.get(1), "PNG", new File("F:\\验证码\\new12306\\cut_test\\" + f2 + (int) (Math.random() * 1000) + ".png"));
    }
    System.out.println(sum);
  }
}
