package tk.gbl.cnn.sougou;

import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;
import tk.gbl.cnn.util.image.Cut;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Date: 2016/7/5
 * Time: 17:21
 *
 * @author Tian.Dong
 */
public class CutTest {
  public static void main(String[] args) throws IOException {
    File originPath = new File("E:\\DT\\cnn\\sougou\\origin");
    for(File file:originPath.listFiles()) {
      //File file = new File("E:\\DT\\cnn\\sougou\\test.png");
      int[][] image = Binary.deal(file);
      ArrayToImage.createImage(image, new File("E:\\DT\\cnn\\sougou\\binary\\"+file.getName()));
    }
  }
}
