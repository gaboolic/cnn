package tk.gbl.cnn.core;

import java.io.Serializable;

/**
 * Date: 2016/6/3
 * Time: 11:46
 *
 * @author Tian.Dong
 */
public class Size implements Serializable {
  protected int x;
  protected int y;

  public Size(int x, int y) {
    this.x = x;
    this.y = y;
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


}
