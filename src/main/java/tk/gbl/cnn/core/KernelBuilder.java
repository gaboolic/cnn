package tk.gbl.cnn.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/6/3
 * Time: 11:55
 *
 * @author Tian.Dong
 */
public class KernelBuilder implements Serializable {
  private int x;
  private int y;
  private int step;
  private int mapCount;

  public KernelBuilder(int x, int y, int step) {
    this.x = x;
    this.y = y;
    this.step = step;
  }

  public KernelBuilder(int x, int y, int step, int mapCount) {
    this.x = x;
    this.y = y;
    this.step = step;
    this.mapCount = mapCount;
  }

  public List<Kernel> buildKernelList() {
    List<Kernel> kernelList = new ArrayList<>();
    for (int i = 0; i < mapCount; i++) {
      Kernel kernel = new Kernel(x, y, step);
      kernelList.add(kernel);
    }
    return kernelList;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public int getMapCount() {
    return mapCount;
  }

  public void setMapCount(int mapCount) {
    this.mapCount = mapCount;
  }
}
