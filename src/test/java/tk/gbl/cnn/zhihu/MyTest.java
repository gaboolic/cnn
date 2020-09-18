package tk.gbl.cnn.zhihu;

import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;
import tk.gbl.cnn.util.image.Cut;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Date: 2016/7/5
 * Time: 16:45
 *
 * @author Tian.Dong
 */
public class MyTest {
  public static void main(String[] args) throws IOException {
    int[][] image = Binary.dealWhite(new File("E:\\DT\\cnn\\zhihu\\captcha.gif"));
    ArrayToImage.createImage(image,new File("E:\\DT\\cnn\\zhihu\\binary.png"));

    image = Cut.cut(image);
    List<int[][]> parts = Cut.cut4Parts(image);
    int i = 0;
    for(int[][] part:parts){
      ArrayToImage.createImage(part,new File("E:\\DT\\cnn\\zhihu\\"+i+++".png"));

    }
  }
}
