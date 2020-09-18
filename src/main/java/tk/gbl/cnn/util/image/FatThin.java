package tk.gbl.cnn.util.image;

/**
 * Date: 2014/11/10
 * Time: 17:55
 *
 * @author Tian.Dong
 */
public class FatThin {
  public static void fat(int[][] img) {
    for (int h = 0; h < img.length; h++) {
      for (int w = 0; w < img[0].length; w++) {
        if (img[h][w] == 1) {
          int start = w;
          while (w < img[0].length && img[h][w] == 1) {
            w++;
          }
          int end = w;
          if (start - 1 >= 0)
            img[h][start - 1] = 1;
          if (end < img[0].length)
            img[h][end] = 1;
          w = end + 1;
        }
      }
    }
  }

  public static void thin(int[][] img) {
    for (int h = 0; h < img.length; h++) {
      for (int w = 0; w < img[0].length; w++) {
        if (img[h][w] == 1) {
          int start = w;
          while (w < img[0].length && img[h][w] == 1) {
            w++;
          }
          int end = w;
          if (end - start > 2) {
            img[h][start] = 0;
            img[h][end - 1] = 0;
          }
          w = end;
        }
      }
    }
  }
}
