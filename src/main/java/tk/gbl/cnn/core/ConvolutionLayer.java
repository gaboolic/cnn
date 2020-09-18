package tk.gbl.cnn.core;

import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.MatrixOperate;
import tk.gbl.cnn.util.MatrixTwoOperate;
import tk.gbl.cnn.util.Util;

import java.io.IOException;

/**
 * 卷积层
 * <p/>
 * Date: 2016/6/8
 * Time: 17:03
 *
 * @author Tian.Dong
 */
public class ConvolutionLayer extends BaseLayer {

  KernelBuilder kernelBuilder;

  public ConvolutionLayer(String name, int mapNum, KernelBuilder kernelBuilder) {
    this.name = name;
    this.mapNum = mapNum;
    this.kernelBuilder = kernelBuilder;
    this.bias = new double[mapNum];
  }

  @Override
  public double[][][] doLayer(double[][][] images) {
    int ox = (images[0].length - 4) > 0 ? (images[0].length - 4) : 1;
    int oy = (images[0].length - 4) > 0 ? (images[0].length - 4) : 1;

    double[][][] outputs = new double[mapNum][ox][oy];
    for (int j = 0; j < mapNum; j++) {
      double[][] sum = null;
//      for (int i = 0; i < images.length; i++) {
      int i = 0;
      if (images.length > 1) {
        i = j / 2;
      }
      double[][] image = images[i];
      double[][] kernel = kernelList[j][i];
      if (sum == null) {
        sum = Util.convnValid(image, kernel);
      } else {
        sum = Util.add(sum, Util.convnValid(image, kernel));
      }
//      }
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
    double[][][] outputErrors = new double[mapNum][][];
    for (int i = 0; i < mapNum; i++) {
      double[][] output = outputs[i];
      double[][] error = errors[i];
      double[][] outputClone = Util.cloneMatrix(output);
      outputClone = Util.oneSubValue(outputClone);
      outputClone = Util.multiply(output, outputClone);//value*(1-value)
      double[][] kroneckerMatrix = Util.kronecker(error, nextLayer.getSampling());
      outputErrors[i] = Util.multiply(kroneckerMatrix, outputClone);
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
