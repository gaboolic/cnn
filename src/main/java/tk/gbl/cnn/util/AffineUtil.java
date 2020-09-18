package tk.gbl.cnn.util;

/**
 * Date: 2016/6/30
 * Time: 14:39
 *
 * @author Tian.Dong
 */
public class AffineUtil {
  public static double[][] rotateZ(double[][] matrix, double angle) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] matrix2 = new double[matrix.length][matrix[0].length];
    double cos = Math.cos(angle / 180 * Math.PI);
    double sin = Math.sin(angle / 180 * Math.PI);
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        int dx = (int) Math.round(cos * (i - m / 2) + sin * (j - n / 2));
        int dy = (int) Math.round(-sin * (i - m / 2) + cos * (j - n / 2));
        if (i + dx < 0 || j + dy < 0 || i + dx >= m || j + dy >= n) {
          continue;
        }
        matrix2[i + dx][j + dy] = matrix[i][j];
      }
    }
    return matrix2;
  }

  public static double[][] translation(double[][] matrix, double dx, double dy) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] matrix2 = new double[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (i + dx < 0 || j + dy < 0 || i + dx >= m || j + dy >= n) {
          continue;
        }
        matrix2[((int) (i + dx))][((int) (j + dy))] = matrix[i][j];
      }
    }
    return matrix2;
  }

  public static double[][] translation(double[][] matrix, double dx, double dy, boolean flag) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] matrix2 = new double[matrix.length][matrix[0].length];
    if (flag) {
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
          matrix2[i][j] = 1;
        }
      }
    }
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (i + dx < 0 || j + dy < 0 || i + dx >= m || j + dy >= n) {
          continue;
        }
        matrix2[((int) (i + dx))][((int) (j + dy))] = matrix[i][j];
      }
    }
    return matrix2;
  }

  public static double[][] rotate(double[][] matrix, double angle) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] matrix2 = new double[matrix.length][matrix[0].length];
    double cos = Math.cos(angle / 180 * Math.PI);
    double sin = Math.sin(angle / 180 * Math.PI);
    for (int i = 0; i < matrix2.length; i++) {
      for (int j = 0; j < matrix2[i].length; j++) {
        int dx = (int) Math.round(cos * (i - m / 2) - sin * (j - n / 2) + m / 2);
        int dy = (int) Math.round(sin * (i - m / 2) + cos * (j - n / 2) + n / 2);
        if (dx < 0 || dy < 0 || dx >= m || dy >= n) {
          continue;
        }
        matrix2[i][j] = matrix[dx][dy];
      }
    }
    return matrix2;
  }

  public static double[][] scale(double[][] matrix, double sx, double sy) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] matrix2 = new double[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix2.length; i++) {
      for (int j = 0; j < matrix2[i].length; j++) {
        int dx = (int) Math.round(i / sx);
        int dy = (int) Math.round(j / sy);
        if (dx < 0 || dy < 0 || dx >= m || dy >= n) {
          continue;
        }
        matrix2[i][j] = matrix[dx][dy];
      }
    }
    return matrix2;
  }

  public static double[][] shear(double[][] matrix, double shx, double shy) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] matrix2 = new double[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix2.length; i++) {
      for (int j = 0; j < matrix2[i].length; j++) {
        int dx = (int) Math.round(i + shx * j);
        int dy = (int) Math.round(i * shy + j);
        if (dx < 0 || dy < 0 || dx >= m || dy >= n) {
          continue;
        }
        matrix2[i][j] = matrix[dx][dy];
      }
    }
    return matrix2;
  }

  public static double[][] swap(double[][] matrix) {
    double r = Math.random() * 32 + 2;
    double angle = Math.random() * Math.PI / 2;
    int m = matrix.length;//高度
    int n = matrix[0].length;//宽度
    double[][] matrix2 = new double[m][n];
    for (int i = 0; i < n; i++) {
      int dx = 0;
      int dy = (int) Math.round(Math.sin((double) i / r + angle) * r);
      for (int j = 0; j < m; j++) {
        if (i + dx < 0 || j + dy < 0 || i + dx >= n || j + dy >= m) {
          continue;
        }
        matrix2[j + dy][i + dx] = matrix[j][i];
      }
    }
    return matrix2;
  }

  public static double[][] swap2(double[][] matrix) {
    int m = matrix.length;//高度
    int n = matrix[0].length;//宽度
    double[][] matrix2 = new double[m][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        int dx = 0;
        int dy = -(int) Math.round(Math.cos((double) i / 8) * 2);
        if (i + dx < 0 || j - dy < 0 || i + dx >= n || j - dy >= m) {
          continue;
        }
        matrix2[j - dy][i + dx] = matrix[j][i];
      }
    }
    return matrix2;
  }
}
