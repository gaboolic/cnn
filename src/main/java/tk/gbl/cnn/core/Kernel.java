package tk.gbl.cnn.core;

import java.util.Random;

/**
 * 卷积核
 * <p>
 * Date: 2016/6/3
 * Time: 11:21
 *
 * @author Tian.Dong
 */
public class Kernel extends Size {

  private int step;
  private double[][] kernel;

  public Kernel(int x, int y, int step) {
    super(x, y);
    this.step = step;
    this.initKernel();
  }

  private void initKernel() {
    kernel = new double[this.x][this.y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        kernel[i][j] = randomK();
      }
    }
  }

  public double randomK() {
    return (Math.random() - 0.05) / 10;
//    return Math.random() / 10 - 0.05;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public double[][] getKernel() {
    return kernel;
  }

  public void setKernel(double[][] kernel) {
    this.kernel = kernel;
  }
}
