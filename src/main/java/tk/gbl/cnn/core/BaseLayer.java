package tk.gbl.cnn.core;

import tk.gbl.cnn.util.Output;
import tk.gbl.cnn.util.Util;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2016/6/6
 * Time: 15:17
 *
 * @author Tian.Dong
 */
public abstract class BaseLayer implements Layer, Serializable {

  protected double[][][][] kernelList;
  protected Sampling sampling;
  protected String name;
  protected int mapNum;
  protected double[][][] outputs;

  protected static double ALPHA = 0.85;
  protected static double LAMBDA = 0;
  protected double[] bias;// 每个map对应一个偏置，只有卷积层和输出层有
  protected double[][][] errors;

  public void excite(double[][][] images) {
    for (int i = 0; i < images.length; i++) {
      double[][] image = images[i];
      for (int x = 0; x < image.length; x++) {
        for (int y = 0; y < image[x].length; y++) {
          image[x][y] = Util.sigmod(image[x][y] + bias[i]);
        }
      }
    }
  }

  @Override
  public int getMapNum() {
    return mapNum;
  }

  @Override
  public double[][][] getErrors() {
    return errors;
  }

  @Override
  public void setErrors(double[][][] errors) {
    this.errors = errors;
  }

  protected double[][] rot180(double[][] kernel) {
    double[][] matrix = new double[kernel.length][kernel[0].length];
    int m = matrix.length;
    int n = matrix[0].length;
    // 按列对称进行交换
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n / 2; j++) {
        double tmp = kernel[i][j];
        matrix[i][j] = kernel[i][n - 1 - j];
        matrix[i][n - 1 - j] = tmp;
      }
    }
    // 按行对称进行交换
    for (int j = 0; j < n; j++) {
      for (int i = 0; i < m / 2; i++) {
        double tmp = kernel[i][j];
        matrix[i][j] = kernel[m - 1 - i][j];
        matrix[m - 1 - i][j] = tmp;
      }
    }
    return matrix;
  }

  protected double[][] extend(double[][] error, int km, int kn) {
    int m = error.length;
    int n = error[0].length;
    // 扩展矩阵
    final double[][] extendMatrix = new double[m + 2 * (km - 1)][n + 2
        * (kn - 1)];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++)
        extendMatrix[i + km - 1][j + kn - 1] = error[i][j];
    }
    return extendMatrix;
  }

  @Override
  public void updateParam(double[][][] lastOutputs) {
    if (kernelList == null) {
      return;
    }
    updateKernels(lastOutputs);
    updateBias();
  }

  private void updateKernels(double[][][] lastOutputs) {
    if (kernelList == null) {
      return;
    }
    for (int j = 0; j < mapNum; j++) {
      for (int i = 0; i < lastOutputs.length; i++) {
        // 对batch的每个记录delta求和
        double[][] deltaKernel = null;
        double[][] error = errors[j];
        deltaKernel = Util.convnValid(
            lastOutputs[i], error);

        // 更新卷积核
        double[][] kernel = kernelList[j][i];
        double[][] oldKernel = Util.multiply(kernel, (1 - LAMBDA * ALPHA));
        deltaKernel = Util.multiply(deltaKernel, ALPHA);
        double[][] newKernel = Util.add(oldKernel, deltaKernel);
        kernelList[j][i] = newKernel;
      }
    }
  }

  private void updateBias() {
    if (kernelList == null) {
      return;
    }
    for (int i = 0; i < errors.length; i++) {
      double[][] error = errors[i];
      double deltaBias = Util.sum(error);
      this.bias[i] = this.bias[i] + ALPHA * deltaBias;
    }

  }

  public double[][][] getOutputs() {
    return outputs;
  }

  public void setOutputs(double[][][] outputs) {
    this.outputs = outputs;
  }

  @Override
  public Sampling getSampling() {
    return sampling;
  }

  public void setSampling(Sampling sampling) {
    this.sampling = sampling;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMapNum(int mapNum) {
    this.mapNum = mapNum;
  }

  public double[][][][] getKernelList() {
    return kernelList;
  }

  public void setKernelList(double[][][][] kernelList) {
    this.kernelList = kernelList;
  }

  public static double getALPHA() {
    return ALPHA;
  }

  public static void setALPHA(double ALPHA) {
    BaseLayer.ALPHA = ALPHA;
  }

  public static double getLAMBDA() {
    return LAMBDA;
  }

  public static void setLAMBDA(double LAMBDA) {
    BaseLayer.LAMBDA = LAMBDA;
  }

  public double[] getBias() {
    return bias;
  }

  public void setBias(double[] bias) {
    this.bias = bias;
  }
}
