package tk.gbl.cnn.core;

import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.MatrixOperate;
import tk.gbl.cnn.util.Util;

import java.io.IOException;
import java.util.List;

/**
 * Date: 2016/6/6
 * Time: 14:23
 *
 * @author Tian.Dong
 */
public class DenseLayer extends BaseLayer {

  KernelBuilder kernelBuilder;

  public DenseLayer(String name, int mapNum, KernelBuilder kernelBuilder) {
    this.name = name;
    this.mapNum = mapNum;
    this.kernelBuilder = kernelBuilder;
    this.bias = new double[mapNum];
  }

  @Override
  public double[][][] doLayer(double[][][] images) {
    double[][][] outputs = new double[mapNum][1][1];
    for (int j = 0; j < mapNum; j++) {
      double[][] sum = null;
      for (int i = 0; i < images.length; i++) {
        double[][] image = images[i];
        double[][] kernel = kernelList[j][i];
        if (sum == null) {
          sum = Util.convnValid(image, kernel);
        } else {
          sum = Util.add(sum, Util.convnValid(image, kernel));
        }
      }
      final double b = bias[j];
      sum = Util.matrixOp(sum, new MatrixOperate() {
        @Override
        public double process(double a) {
          return Util.sigmod(a + b);
        }
      });
      outputs[j] = sum;
    }
    this.outputs = outputs;
    return outputs;
  }

  @Override
  public double[][][] doError(double[][][] errors, Layer nextLayer) {
    double[][][] outputErrors = new double[mapNum][1][1];
    for (int i = 0; i < mapNum; i++) {
      double[][] output = outputs[i];
      double sum = 0;
      for (int j = 0; j < errors.length; j++) {
        double[][] error = errors[j];
        double[][] kernel = nextLayer.getKernelList()[j][i];
        sum += error[0][0] * kernel[0][0];
      }
      sum = sum * output[0][0] * (1 - output[0][0]);
      outputErrors[i][0][0] = sum;
    }
    this.errors = outputErrors;
    return outputErrors;
  }

  public void initKernel(Layer lastLayer) {
    int lastMapNum = lastLayer.getMapNum();
    this.kernelList = new double[this.mapNum][lastMapNum][this.kernelBuilder.getX()][this.kernelBuilder.getY()];
    for (int i = 0; i < lastMapNum; i++)
      for (int j = 0; j < mapNum; j++)
        kernelList[j][i] = Util.randomMatrix(kernelBuilder.getX(), kernelBuilder.getY());
  }

  public KernelBuilder getKernelBuilder() {
    return kernelBuilder;
  }

  public void setKernelBuilder(KernelBuilder kernelBuilder) {
    this.kernelBuilder = kernelBuilder;
  }
}
