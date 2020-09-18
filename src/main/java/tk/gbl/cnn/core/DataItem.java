package tk.gbl.cnn.core;

/**
 * Date: 2016/6/3
 * Time: 16:24
 *
 * @author Tian.Dong
 */
public class DataItem {
  double[][] image;
  byte[] data;
  int width;
  int height;
  int label;

  public double[][] getImage() {
    return image;
  }

  public void setImage(double[][] image) {
    this.image = image;
  }

  public int getLabel() {
    return label;
  }

  public void setLabel(int label) {
    this.label = label;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public double[][] getMatrix() {
    double[][] matrix = new double[height][width];
    for (int i = 0; i < data.length; i++) {
      double r = data[i] == -1 ? 0 : 1;
      try {
        matrix[i / width][i % width] = r;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return matrix;
  }

  public void setImage(int[][] image) {
    double[][] img = new double[image[0].length][image.length];
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
//        img[i][j] = image[i][j] == 1 ? 0 : 1;
        img[i][j] = image[i][j];
      }
    }
    this.image = img;
  }

  public void reverseImage() {
    double[][] img = this.image;
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        img[i][j] = image[i][j] == 1 ? 0 : 1;
      }
    }
    this.image = img;
  }
}
