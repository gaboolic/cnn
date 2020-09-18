package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Date: 2016/7/12
 * Time: 16:14
 *
 * @author Tian.Dong
 */
public class ChineseBinaryTest {
  public static void main111(String[] args) throws Exception {
    File path = new File("F:\\验证码\\new12306\\cut_test");
    for (int i = 0; i < path.listFiles().length; i++) {
      File file = path.listFiles()[i];
      int[][] image = BinaryOstu.deal(file);
//      int[][] image = ChineseBinaryUtil.dealBlack(file);

      ArrayToImage.createImage(image, new File("F:\\验证码\\new12306\\binary_test\\" + file.getName()));
    }
  }

  public static void main(String[] args) throws IOException {
    File path = new File("F:\\验证码\\new12306\\binary_test");
    for (int i = 0; i < path.listFiles().length; i++) {
      File file = path.listFiles()[i];
      int[][] image = BinaryOstu.deal(file);
      image = Cut.cut(image);
      ArrayToImage.createImage(image, new File("F:\\验证码\\new12306\\binary_test\\" + file.getName()));
    }
  }

  public static void main2(String[] args) throws IOException {

    File file = new File("F:\\验证码\\new12306\\binary_test\\冰淇淋291.png");
    BufferedImage image = ImageIO.read(file);
    BufferedImage image2 = ConBriFilter.greyFilter(image);
    ImageIO.write(image2, "PNG", new File("F:\\验证码\\new12306\\binary_test\\冰淇淋.png"));
  }
}
