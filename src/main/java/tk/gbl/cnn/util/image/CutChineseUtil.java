package tk.gbl.cnn.util.image;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/7/7
 * Time: 10:14
 *
 * @author Tian.Dong
 */
public class CutChineseUtil {
  public static List<int[][]> cutParts(int[][] image) {
    int width = image[0].length;
    int count = (int) Math.round(width * 1.0 / 15);
    if (count == 1 && width > 18) {
      count = 2;
    }
    List<int[][]> parts = new ArrayList<>();
    if (count == 1) {
      parts.add(image);
      return parts;
    }

    int singleWidth = (int) Math.round(image[0].length * 1.0 / count);
    for (int i = 0; i < count; i++) {
      int[][] part = new int[image.length][singleWidth];
      for (int j = 0; j < singleWidth; j++) {
        for (int h = 0; h < image.length; h++) {
          if (singleWidth * i + j < image[0].length) {
            part[h][j] = image[h][singleWidth * i + j];
          }
        }
      }
      parts.add(part);
    }
    return parts;
  }
}
