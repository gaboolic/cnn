package tk.gbl.cnn.util;

import tk.gbl.cnn.util.image.ArrayToImage;

import java.io.File;
import java.io.IOException;

/**
 * Date: 2016/6/30
 * Time: 10:37
 *
 * @author Tian.Dong
 */
public class MatrixUtil {
  public static double[][] int2double(int[][] matrix) {
    double[][] m = new double[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        m[i][j] = matrix[i][j];
      }
    }
    return m;
  }

  public static int[][] double2int(double[][] matrix) {
    int[][] m = new int[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        m[i][j] = (int) matrix[i][j];
      }
    }
    return m;
  }

  public static double[][] shapeChange(double[][] matrix) {
    matrix = AffineUtil.shear(matrix, Math.random() * 0.2-0.1, Math.random() * 0.2-0.1);
    matrix = AffineUtil.scale(matrix, Math.random() * 0.2 + 0.9, Math.random() * 0.2 + 0.9);
    matrix = AffineUtil.translation(matrix, Math.random() * 6 - 3, Math.random() * 6 - 3, false);
    return AffineUtil.rotate(matrix, Math.random() * 10 - 5);
  }

  public static double[][] shapeChange2(double[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    double[][] deltaX = randomMatrix(m, n, 2, 1);
    double[][] deltaY = randomMatrix(m, n, 2, 1);
    double[][] k = randomMatrix(m, n, 2, 1);
    deltaX = Util.multiply(deltaX, k);
    deltaX = AffineUtil.rotate(deltaX, 10);
    deltaY = Util.multiply(deltaY, k);
    deltaY = AffineUtil.rotate(deltaY, 10);

    double[][] matrix2 = new double[m][n];

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        int dx = (int) Math.round(deltaX[i][j]);
        int dy = (int) Math.round(deltaY[i][j]);
        if (i + dx < 0 || j + dy < 0 || i + dx >= m || j + dy >= n) {
          continue;
        }
        matrix2[i + dx][j + dy] = matrix[i][j];
      }
    }
    return matrix2;
  }

  private static double[][] randomMatrix(int m, int n, double range, double subNumber) {
    double[][] matrix = new double[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = Math.random() * range - subNumber;
      }
    }
    return matrix;
  }

  public static double[][] standard32(double[][] image) {

    int h = 32;
    int w = 32;
    if (image.length < 32) {
      h = image.length;
    }
    if (image[0].length < 32) {
      w = image[0].length;
    }

    double[][] newImage = new double[32][32];
    int startH = (32 - h) / 2;
    int startW = (32 - w) / 2;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        newImage[i + startH][j + startW] = image[i][j];
      }
    }
    return newImage;
  }
}
