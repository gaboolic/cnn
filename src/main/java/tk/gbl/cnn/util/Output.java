package tk.gbl.cnn.util;

/**
 * Date: 2016/6/13
 * Time: 10:38
 *
 * @author Tian.Dong
 */
public class Output {
  public static void output(String str, double[][][] images) {
    double max = images[0][0][0];
    double min = images[0][0][0];
    double sum = 0;
    for (double[][] image : images) {
      for (double[] item : image) {
        for (double num : item) {
          sum += num;
          if (max < num) {
            max = num;
          }
          if (min > num) {
            min = num;
          }
        }
      }
    }
    System.out.println(str + "max:" + max + ",min:" + min + ",sum:" + sum);
  }

  public static void output(String str, double[][] image) {
    double max = image[0][0];
    double min = image[0][0];
    double sum = 0;
    for (double[] item : image) {
      for (double num : item) {
        sum += num;
        if (max < num) {
          max = num;
        }
        if (min > num) {
          min = num;
        }
      }
    }
    System.out.println(str + "max:" + max + ",min:" + min + ",sum:" + sum);
  }

  public static void output(double[][] img) {
    for (int h = 0; h < img.length; h++) {
      for (int w = 0; w < img[h].length; w++) {
        System.out.print(img[h][w]);
      }
      System.out.println();
    }
  }
}
