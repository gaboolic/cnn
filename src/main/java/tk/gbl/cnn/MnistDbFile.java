package tk.gbl.cnn;

import tk.gbl.cnn.core.DataItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/6/3
 * Time: 10:26
 *
 * @author Tian.Dong
 */
public class MnistDbFile {
  RandomAccessFile imageFile;
  RandomAccessFile labelFile;
  int imageCount;
  int row;
  int col;
  int labelCount;

  int currentIndex;

  List<DataItem> dataItemList = new ArrayList<>();

  public MnistDbFile(String path, String imageFileStr, String labelFileStr) throws IOException {
    this.imageFile = new RandomAccessFile(new File(path, imageFileStr), "r");
    this.labelFile = new RandomAccessFile(new File(path, labelFileStr), "r");
    imageFile.readInt();
    imageCount = imageFile.readInt();
    row = imageFile.readInt();
    col = imageFile.readInt();

    labelFile.readInt();
    labelCount = labelFile.readInt();
  }

  public MnistDbFile(String path, String imageFileStr, String labelFileStr, int loadCount) throws IOException {
    this.imageFile = new RandomAccessFile(new File(path, imageFileStr), "r");
    this.labelFile = new RandomAccessFile(new File(path, labelFileStr), "r");
    imageFile.readInt();
    imageCount = imageFile.readInt();
    row = imageFile.readInt();
    col = imageFile.readInt();

    labelFile.readInt();
    labelCount = labelFile.readInt();
    initDataItemList(loadCount);
  }

  private void initDataItemList(int loadCount) throws IOException {
    for (int i = 0; i < loadCount; i++) {
      dataItemList.add(getDataItem());
    }
  }

  public void jumpToIndex(int index) throws IOException {
    for (int i = 0; i < index; i++) {
      readLabel();
      readImage();
      currentIndex++;
    }
  }

  public DataItem getDataItem() throws IOException {
    int label = readLabel();
    double[][] image = readImage01();
    DataItem dataItem = new DataItem();
    dataItem.setImage(image);
    dataItem.setLabel(label);
    currentIndex++;
    return dataItem;
  }


  public void readImageAndWriteImage() throws IOException {
    int label = readLabel();
    BufferedImage bufferedImage = arrayToImage(readImage());
    File path = new File("E:\\DT\\MNIST\\" + label);
    writeImage(bufferedImage, path);
    currentIndex++;
  }

  public void writeImage(BufferedImage bufferedImage, File path) throws IOException {
    if (!path.exists()) {
      path.mkdir();
    }
    ImageIO.write(bufferedImage, "PNG", new File(path, System.currentTimeMillis() + ".png"));
  }

  public int readLabel() throws IOException {
    int label = labelFile.read();
    return label;
  }

  private double[][] readImage01() throws IOException {
    double[][] dat = new double[row][col];

    for (int h = 0; h < row; h++) {
      for (int w = 0; w < col; w++) {
        int value = imageFile.read();
        dat[h][w] = value == 0 ? 0 : 1;
      }
    }
    return dat;
  }

  public double[][] readImage() throws IOException {
    double[][] dat = new double[row][col];

    for (int h = 0; h < row; h++) {
      for (int w = 0; w < col; w++) {
        dat[h][w] = imageFile.read();
      }
    }
    return dat;
  }

  public BufferedImage arrayToImage(double[][] dat) {
    BufferedImage bufferedImage = new BufferedImage(dat[0].length, dat.length, BufferedImage.TYPE_USHORT_GRAY);
    for (int h = 0; h < dat.length; h++) {
      for (int w = 0; w < dat[0].length; w++) {
        bufferedImage.setRGB(w, h, (int) (dat[h][w] * 256 * 256 + dat[h][w] * 256) + (int) dat[h][w]);
      }
    }
    return bufferedImage;
  }

  public List<DataItem> getDataItemList() {
    return dataItemList;
  }

  public int getImageCount() {
    return imageCount;
  }

}
