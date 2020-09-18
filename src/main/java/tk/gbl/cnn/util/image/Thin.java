package tk.gbl.cnn.util.image;

/**
 * 骨架细化
 * <p/>
 * Date: 14-9-24
 * Time: 上午11:20
 *
 * @author Tian.Dong
 */

public class Thin {
  static int[] eraseTable =
      {
          0, 0, 1, 1, 0, 0, 1, 1,
          1, 1, 0, 1, 1, 1, 0, 1,
          1, 1, 0, 0, 1, 1, 1, 1,
          0, 0, 0, 0, 0, 0, 0, 1,
          0, 0, 1, 1, 0, 0, 1, 1,
          1, 1, 0, 1, 1, 1, 0, 1,
          1, 1, 0, 0, 1, 1, 1, 1,
          0, 0, 0, 0, 0, 0, 0, 1,
          1, 1, 0, 0, 1, 1, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
          1, 1, 0, 0, 1, 1, 0, 0,
          1, 1, 0, 1, 1, 1, 0, 1,
          0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 1, 1, 0, 0, 1, 1,
          1, 1, 0, 1, 1, 1, 0, 1,
          1, 1, 0, 0, 1, 1, 1, 1,
          0, 0, 0, 0, 0, 0, 0, 1,
          0, 0, 1, 1, 0, 0, 1, 1,
          1, 1, 0, 1, 1, 1, 0, 1,
          1, 1, 0, 0, 1, 1, 1, 1,
          0, 0, 0, 0, 0, 0, 0, 0,
          1, 1, 0, 0, 1, 1, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
          1, 1, 0, 0, 1, 1, 1, 1,
          0, 0, 0, 0, 0, 0, 0, 0,
          1, 1, 0, 0, 1, 1, 0, 0,
          1, 1, 0, 1, 1, 1, 0, 0,
          1, 1, 0, 0, 1, 1, 1, 0,
          1, 1, 0, 0, 1, 0, 0, 0
      };

  public void thin(int[][] img) {
    for (int h = 0; h < img.length; h++) {
      for (int w = 0; w < img[h].length; w++) {
        if (img[h][w] == 0) {
          continue;
        }
        //1  左右邻居
        if (w != 0 && w + 1 < img[h].length && img[h][w - 1] == 1 && img[h][w + 1] == 1) {
          continue;
        }
        //2 查表
        int count = near8count(img, h, w);
        if (eraseTable[count] == 1) {
          img[h][w] = 0;
          w++;
        }
      }
    }


//    for (int w = 0; w < img[0].length; w++) {
//      for (int h = 0; h < img.length; h++) {
//        if (img[h][w] == 0) {
//          continue;
//        }
//        //1  上下邻居
//        if (h != 0 && h + 1 < img.length && img[h - 1][w] == 1 && img[h + 1][w] == 1) {
//          continue;
//        }
//        //2 查表
//        int count = near8count(img, h, w);
//        if (eraseTable[count] == 1) {
//          img[h][w] = 0;
//          h++;
//        }
//      }
//    }
  }

  private int near8count(int[][] img, int h, int w) {
    int p1 = 0;
    int p2 = 0;
    int p3 = 0;
    int p4 = 0;
    int p6 = 0;
    int p7 = 0;
    int p8 = 0;
    int p9 = 0;
    if (h - 1 > 0 && w - 1 > 0) {
      p1 = img[h - 1][w - 1];
    }
    if (h - 1 > 0) {
      p2 = img[h - 1][w];
    }
    if (h - 1 > 0 && w + 1 < img[h].length) {
      p3 = img[h - 1][w + 1];
    }
    if (w - 1 > 0) {
      p4 = img[h][w - 1];
    }
    if (w + 1 < img[h].length) {
      p6 = img[h][w + 1];
    }
    if (h + 1 < img.length && w - 1 > 0) {
      p7 = img[h + 1][w - 1];
    }
    if (h + 1 < img.length) {
      p8 = img[h + 1][w];
    }
    if (h + 1 < img.length && w + 1 < img[h].length) {
      p9 = img[h + 1][w + 1];
    }
    int count = p1 + 2 * p2 + 4 * p3 + 8 * p4 + 16 * p6
        + 32 * p7 + 64 * p8 + 128 * p9;
    if (count == 258) {
      System.out.println();
    }
    return count;
  }
}
