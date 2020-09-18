package tk.gbl.cnn.core;

import tk.gbl.cnn.util.CoderUtil;
import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.Util;

import java.io.IOException;

/**
 * Date: 2016/6/8
 * Time: 17:03
 *
 * @author Tian.Dong
 */
public class SamplingLayer extends BaseLayer {

  public SamplingLayer(String name, int mapNum, Sampling sampling) {
    this.name = name;
    this.mapNum = mapNum;
    this.sampling = sampling;
  }

  @Override
  public double[][][] doLayer(double[][][] images) {
    double[][][] outputs = new double[images.length][images[0].length / sampling.getX()][images[0].length / sampling.getY()];
    for (int count = 0; count < images.length; count++) {
      double[][] image = images[count];
      double[][] output = outputs[count];
      for (int x = 0; x < output.length; x++) {
        for (int y = 0; y < output[x].length; y++) {
          double sum = 0;
          for (int i = 0; i < sampling.getX(); i++) {
            for (int j = 0; j < sampling.getY(); j++) {
              sum += image[x * sampling.getX() + i][y * sampling.getY() + j];
            }
          }
          output[x][y] = sum / (sampling.getX() * sampling.getY());
        }
      }
    }
//    for (double[][] output : outputs) {
//      try {
//        ImageUtil.writeImage(output, name + " " + System.currentTimeMillis() + ".png");
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
    this.outputs = outputs;
    return outputs;
  }

  /**
   * for (int i = start; i < end; i++) {
   * double[][] sum = null;// 对每一个卷积进行求和
   * for (int j = 0; j < nextMapNum; j++) {
   * double[][] nextError = nextLayer.getError(j);
   * double[][] kernel = nextLayer.getKernel(i, j);
   * // 对卷积核进行180度旋转，然后进行full模式下得卷积
   * if (sum == null)
   * sum = Util
   * .convnFull(nextError, Util.rot180(kernel));
   * else
   * sum = Util.matrixOp(
   * Util.convnFull(nextError,
   * Util.rot180(kernel)), sum, null,
   * null, Util.plus);
   * }
   * layer.setError(i, sum);
   * }
   */
  @Override
  public double[][][] doError(double[][][] errors, Layer nextLayer) {
    if (nextLayer instanceof ConvolutionLayer) {
      int errorX = this.outputs[0].length;
      int errorY = this.outputs[0].length;
      double[][][] outputErrors = new double[mapNum][errorX][errorY];
      for (int i = 0; i < mapNum; i++) {
      double[][] sum = null;
      for (int j = i*2; j <i*2+1; j++) {

        double[][] nextError = nextLayer.getErrors()[j];
        double[][] kernel = nextLayer.getKernelList()[j][i];

        if (sum == null) {
          sum = Util.convnFull(nextError, rot180(kernel));
        } else {
          sum = Util.add(sum, Util.convnFull(nextError, rot180(kernel)));
        }
        outputErrors[i] = sum;
      }
      }
      this.errors = outputErrors;
      return outputErrors;
    } else {
      int errorX = this.outputs[0].length;
      int errorY = this.outputs[0].length;
      double[][][] outputErrors = new double[mapNum][errorX][errorY];
      for (int i = 0; i < mapNum; i++) {
        double[][] sum = null;
        for (int j = 0; j < nextLayer.getMapNum(); j++) {
          double[][] nextError = nextLayer.getErrors()[j];
          double[][] kernel = nextLayer.getKernelList()[j][i];

          if (sum == null) {
            sum = Util.convnFull(nextError, rot180(kernel));
          } else {
            sum = Util.add(sum, Util.convnFull(nextError, rot180(kernel)));
          }
          outputErrors[i] = sum;
        }
      }
      this.errors = outputErrors;
      return outputErrors;
    }
  }
}
