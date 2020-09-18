package tk.gbl.cnn.core;


import tk.gbl.cnn.util.ImageUtil;

import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 2016/6/3
 * Time: 11:31
 *
 * @author Tian.Dong
 */
public class InputLayer extends BaseLayer {
  public InputLayer(int mapNum) {
    this.mapNum = mapNum;
  }

  @Override
  public double[][][] doLayer(double[][][] images) {
    this.outputs = images;
    return images;
  }

  @Override
  public double[][][] doError(double[][][] errors, Layer nextLayer) {
    return new double[0][][];
  }
}
