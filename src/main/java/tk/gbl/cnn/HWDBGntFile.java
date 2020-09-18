package tk.gbl.cnn;

import tk.gbl.cnn.core.DataItem;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/6/16
 * Time: 13:51
 *
 * @author Tian.Dong
 */
public class HWDBGntFile {
  RandomAccessFile imageFile;
  List<DataItem> dataItemList = new ArrayList<>();

  public HWDBGntFile(String path, String imageFileStr) throws IOException {
    this.imageFile = new RandomAccessFile(new File(path, imageFileStr), "r");

    while (true) {
      try {
        long size = readUnsignedInt();
        int tag = readChar();
        int width = readUnsignedShort();
        int height = readUnsignedShort();
        byte[] bitmap = new byte[width * height];
        imageFile.read(bitmap);

        DataItem dataItem = new DataItem();
        dataItem.setLabel(tag);
        dataItem.setData(bitmap);
        dataItem.setWidth(width);
        dataItem.setHeight(height);
        dataItemList.add(dataItem);
      } catch (EOFException e) {
        break;
      }
    }
  }


  private int readUnsignedShort() throws IOException {
    int b1 = imageFile.read();
    int b2 = imageFile.read();
    return ((b2 << 8) + (b1 << 0));
  }

  private char readChar(byte[] data) {
    int b1 = data[0];
    int b2 = data[1];
    return (char) ((b2 << 8) + (b1 << 0));
  }

  private int readChar() throws IOException {
    int b1 = imageFile.read();
    int b2 = imageFile.read();
    return  ((b1 << 8) + (b2 << 0));
//    return URLDecoder.decode("", "utf-8");
  }

  private long readUnsignedInt() throws IOException {
    int b1 = imageFile.read();
    if (b1 == -1) {
      throw new EOFException();
    }
    int b2 = imageFile.read();
    int b3 = imageFile.read();
    int b4 = imageFile.read();

    return (long) ((b4 << 24) + (b3 << 16)) + (b2 << 8) + (b1 << 0);
  }

  public List<DataItem> getDataItemList() {
    return dataItemList;
  }

  public void setDataItemList(List<DataItem> dataItemList) {
    this.dataItemList = dataItemList;
  }
}
