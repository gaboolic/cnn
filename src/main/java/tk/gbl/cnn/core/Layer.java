package tk.gbl.cnn.core;

import java.util.List;

/**
 * Date: 2016/6/3
 * Time: 11:08
 *
 * @author Tian.Dong
 */
public interface Layer {
  public double[][][] doLayer(double[][][] images);
  public double[][][] doError(double[][][] errors,Layer nextLayer);

  public int getMapNum();
  public double[][][] getErrors();
  public void setErrors(double[][][] errors);

  public void updateParam(double[][][] lastOutputs);

  public double[][][] getOutputs();
  public Sampling getSampling();
  public double[][][][] getKernelList();
}
