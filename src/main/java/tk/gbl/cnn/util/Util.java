package tk.gbl.cnn.util;

import tk.gbl.cnn.core.Sampling;

import java.util.Random;

/**
 * Date: 2016/6/6
 * Time: 14:13
 *
 * @author Tian.Dong
 */
public class Util {

  static Random r = new Random(2);

  public static double[][] matrixOp(double[][] matrix, MatrixOperate matrixOperate) {
    final int m = matrix.length;
    int n = matrix[0].length;
    final double[][] outMatrix = new double[m][n];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        outMatrix[i][j] = matrixOperate.process(matrix[i][j]);
      }
    }
    return outMatrix;
  }

  public static double[][] matrixOp(final double[][] ma, final double[][] mb,
                                    final MatrixOperate operatorA, final MatrixOperate operatorB,
                                    MatrixTwoOperate operator) {
    final int m = ma.length;
    int n = ma[0].length;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        double a = ma[i][j];
        if (operatorA != null)
          a = operatorA.process(a);
        double b = mb[i][j];
        if (operatorB != null)
          b = operatorB.process(b);
        mb[i][j] = operator.process(a, b);
      }
    }
    return mb;
  }


  /**
   * 复制矩阵
   *
   * @param matrix
   * @return
   */
  public static double[][] cloneMatrix(final double[][] matrix) {

    final int m = matrix.length;
    int n = matrix[0].length;
    final double[][] outMatrix = new double[m][n];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        outMatrix[i][j] = matrix[i][j];
      }
    }
    return outMatrix;
  }

  /**
   * 激活函数
   * 返回0~1 值越大越接近1
   *
   * @param x
   * @return
   */
  public static double sigmod(double x) {
//    return 1 / (1 + Math.pow(Math.E, -x));
    return 1 / (1 + Math.exp(-x));
  }

  public static void main(String[] args) {
    System.out.println(1 / (1 + Math.pow(Math.E, 1)));
    System.out.println(1 / (1 + Math.pow(Math.E, 0.5)));
    System.out.println(1 / (1 + Math.pow(Math.E, 0.1)));
    System.out.println(1 / (1 + Math.pow(Math.E, 0)));
    System.out.println(1 / (1 + Math.pow(Math.E, -0.1)));
    System.out.println(1 / (1 + Math.pow(Math.E, -1)));
    System.out.println(1 / (1 + Math.pow(Math.E, -9)));
  }

  public static int getMaxIndex(double[][][] outputMaps) {
    int index = 0;
    double max = outputMaps[0][0][0];
    for (int i = 0; i < outputMaps.length; i++) {
      double output = outputMaps[i][0][0];
      if (max < output) {
        max = output;
        index = i;
      }
    }
    return index;
  }

  public static double sum(double[][] error) {
    double sum = 0;
    for (int i = 0; i < error.length; i++) {
      for (int j = 0; j < error[i].length; j++) {
        sum += error[i][j];
      }
    }
    return sum;
  }

  public static double sum(double[][][] errors) {
    double sum = 0;
    for (double[][] error : errors) {
      for (int i = 0; i < error.length; i++) {
        for (int j = 0; j < error[i].length; j++) {
          sum += error[i][j];
        }
      }
    }
    return sum;
  }

  /**
   * 计算full模式的卷积
   *
   * @param matrix
   * @param kernel
   * @return
   */
  public static double[][] convnFull(double[][] matrix,
                                     final double[][] kernel) {
    int m = matrix.length;
    int n = matrix[0].length;
    final int km = kernel.length;
    final int kn = kernel[0].length;
    // 扩展矩阵
    final double[][] extendMatrix = new double[m + 2 * (km - 1)][n + 2
        * (kn - 1)];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++)
        extendMatrix[i + km - 1][j + kn - 1] = matrix[i][j];
    }
    return convnValid(extendMatrix, kernel);
  }

  /**
   * 计算valid模式的卷积
   *
   * @param matrix
   * @param kernel
   * @return
   */
  public static double[][] convnValid(final double[][] matrix,
                                      double[][] kernel) {
    int m = matrix.length;
    int n = matrix[0].length;
    final int km = kernel.length;
    final int kn = kernel[0].length;
    // 需要做卷积的列数
    int kns = n - kn + 1;
    // 需要做卷积的行数
    final int kms = m - km + 1;
    if (kms < 0 || kns < 0) {
      System.out.println();
    }
    // 结果矩阵
    final double[][] outMatrix = new double[kms][kns];

    for (int i = 0; i < kms; i++) {
      for (int j = 0; j < kns; j++) {
        double sum = 0.0;
        for (int ki = 0; ki < km; ki++) {
          for (int kj = 0; kj < kn; kj++)
            sum += matrix[i + ki][j + kj] * kernel[ki][kj];
        }
        outMatrix[i][j] = sum;
      }
    }
    return outMatrix;

  }

  public static double[][] add(double[][] matrix, double[][] matrix2) {
    if (matrix == null) {
      return matrix2;
    }
    if (matrix2 == null) {
      return matrix;
    }
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (i < matrix2.length && j < matrix2[i].length) {
          matrix[i][j] += matrix2[i][j];
        }
      }
    }
    return matrix;
  }

  public static double[][] multiply(double[][] matrix, double[][] matrix2) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] *= matrix2[i][j];
      }
    }
    return matrix;
  }

  public static double[][] multiply(double[][] matrix, double d) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] *= d;
      }
    }
    return matrix;
  }



  public static double[][] oneSubValue(double[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] = 1 - matrix[i][j];
      }
    }
    return matrix;
  }

  /**
   * 克罗内克积,对矩阵进行扩展
   *
   * @param matrix
   * @param sampling
   * @return
   */
  public static double[][] kronecker(final double[][] matrix, Sampling sampling) {
    final int m = matrix.length;
    int n = matrix[0].length;
    final double[][] outMatrix = new double[m * sampling.getX()][n * sampling.getY()];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        for (int ki = i * sampling.getX(); ki < (i + 1) * sampling.getX(); ki++) {
          for (int kj = j * sampling.getY(); kj < (j + 1) * sampling.getY(); kj++) {
            outMatrix[ki][kj] = matrix[i][j];
          }
        }
      }
    }
    return outMatrix;
  }


  public static double[][] randomMatrix(int x, int y) {
    double[][] matrix = new double[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
//        matrix[i][j] = (Math.random()) / 500 - (1/500);
//        matrix[i][j] = 0;
        matrix[i][j] = (Math.random()) / 10000 - 0.000005;
//        matrix[i][j] = (r.nextDouble() - 0.05) / 10;
      }
    }
    return matrix;
  }

  public static int random(int trainCount) {
    return (int) (Math.random() * trainCount);
//    return r.nextInt(trainCount);
  }

  public static double exp(double x) {
    return Math.exp(x);
  }


}
