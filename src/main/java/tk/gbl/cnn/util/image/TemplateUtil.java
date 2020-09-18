package tk.gbl.cnn.util.image;


/**
 * Date: 2014/9/25
 * Time: 15:28
 *
 * @author Tian.Dong
 */
public class TemplateUtil {

  public static int compareHam(int[][] img, int[][] temp) {
    Distance distance = new Distance();
    Point imgFirst = getLeftFirst(img);
    Point temFirst = getLeftFirst(temp);
    Point startPoint = new Point(imgFirst.getH() - temFirst.getH(), imgFirst.getW());
    int hamCount = distance.hamDistance(img, startPoint, temp);
    return hamCount;
  }

  public static Point getLeftFirst(int[][] temp) {
    Point tp;
    for (int w = 0; w < temp[0].length; w++) {
      for (int h = 0; h < temp.length; h++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return null;
  }

  public static Point getUpFirst(int[][] temp) {
    Point tp;
    for (int h = 0; h < temp.length; h++) {
      for (int w = 0; w < temp[0].length; w++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return null;
  }

  public static Point getDownFirst(int[][] temp) {
    Point tp;
    for (int h = temp.length-1; h >0; h--) {
      for (int w = temp[0].length - 1; w > 0; w--) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return null;
  }

  public static Point getRightFirst(int[][] temp) {
    Point tp;
    for (int w = temp[0].length - 1; w > 0; w--) {
      for (int h = 0; h < temp.length; h++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return null;
  }
}
