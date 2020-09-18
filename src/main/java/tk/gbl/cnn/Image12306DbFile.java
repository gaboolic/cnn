package tk.gbl.cnn;

import tk.gbl.cnn.core.DataItem;
import tk.gbl.cnn.util.MatrixUtil;
import tk.gbl.cnn.util.Output;
import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2016/7/1
 * Time: 14:57
 *
 * @author Tian.Dong
 */
public class Image12306DbFile {
  List<DataItem> dataItemList = new ArrayList<>();
  String labels = "23456789ABCDEFGHKMNPQRSTUVWXYZ";

  public Image12306DbFile(String pathStr) throws IOException {

    File path = new File(pathStr);
    for (File templatePath : path.listFiles()) {
      if(!templatePath.isDirectory()){
        continue;
      }
      File[] files = templatePath.listFiles();
      String ch = templatePath.getName();
      for (int i = 0; i < files.length; i++) {
        try {
          File file = files[i];
          int[][] image = Binary.deal(file);
          DataItem dataItem = new DataItem();
          dataItem.setLabel(labels.indexOf(ch));
          double[][] changeImage = MatrixUtil.standard32(MatrixUtil.int2double(image));
          dataItem.setImage(changeImage);
          dataItem.setWidth(32);
          dataItem.setHeight(32);
          dataItemList.add(dataItem);
        } catch (EOFException e) {
          break;
        }
      }
    }
  }

  public List<DataItem> getDataItemList() {
    return dataItemList;
  }

  public void setDataItemList(List<DataItem> dataItemList) {
    this.dataItemList = dataItemList;
  }


}
