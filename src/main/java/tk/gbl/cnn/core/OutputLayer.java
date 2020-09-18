package tk.gbl.cnn.core;

import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.MatrixOperate;
import tk.gbl.cnn.util.Util;

import java.io.IOException;
import java.util.List;

/**
 * Date: 2016/6/3
 * Time: 11:33
 *
 * @author Tian.Dong
 */
public class OutputLayer extends BaseLayer {


  KernelBuilder kernelBuilder;

  public OutputLayer(String name, int mapNum, KernelBuilder kernelBuilder) {
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

    /*double sum = 0;
    for (int j = 0; j < mapNum; j++) {
      sum += Util.exp(outputs[j][0][0] + bias[j]);
    }
    for (int j = 0; j < mapNum; j++) {
      outputs[j][0][0] = (1 / sum) * Util.exp(outputs[j][0][0] + bias[j]);
    }*/

    this.outputs = outputs;
    return outputs;
  }

  @Override
  public double[][][] doError(double[][][] errors, Layer nextLayer) {
    double[][][] outputErrors = new double[mapNum][][];
    for (int count = 0; count < mapNum; count++) {
      double[][] map = outputs[count];

      double[][] error = errors[count];
      //value * (1-value)
      double[][] outputClone = Util.cloneMatrix(map);
      outputClone = Util.oneSubValue(outputClone);
      outputClone = Util.multiply(map, outputClone);//value*(1-value)
      double[][] kroneckerMatrix = Util.kronecker(error, nextLayer.getSampling());
      outputErrors[count] = Util.multiply(kroneckerMatrix, outputClone);
    }

    /*for (int count = 0; count < mapNum; count++) {
      double[][] map = outputs[count];
      double[][] nextError = errors[count];

      // 矩阵相乘，但对第二个矩阵的每个元素value进行1-value操作
      double[][] outMatrix = Util.matrixOp(map,
          Util.cloneMatrix(map), null, new MatrixOperate() {
            @Override
            public double process(double a) {
              return 1 - a;
            }
          },
          new MatrixTwoOperate() {
            @Override
            public double process(double a, double b) {
              return a * b;
            }
          });
      outMatrix = Util.matrixOp(outMatrix,
          Util.kronecker(nextError, nextLayer.getSampling()), null, null,
          new MatrixTwoOperate() {
            @Override
            public double process(double a, double b) {
              return a * b;
            }
          });
      outputErrors[count] = outMatrix;
    }*/
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
