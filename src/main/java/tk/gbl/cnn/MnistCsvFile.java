package tk.gbl.cnn;

import tk.gbl.cnn.core.DataItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/7/6
 * Time: 14:52
 *
 * @author Tian.Dong
 */
public class MnistCsvFile {
  List<DataItem> dataItemList = new ArrayList<>();


  public MnistCsvFile(String path, String fileName, boolean isTrain) throws Exception {
    File file = new File(path, fileName);
    BufferedReader in = new BufferedReader(new FileReader(file));
    String line = in.readLine();
    while ((line = in.readLine()) != null) {
      String[] datas = line.split(",");
      if (datas.length == 0)
        continue;
      double[] data = new double[datas.length];
      for (int i = 0; i < datas.length; i++)
        data[i] = Double.parseDouble(datas[i]);
      int label = -1;

      if (isTrain) {
        label = (int) data[0];
      }

      double[][] image = new double[28][28];
      for (int x = 0; x < 28; x++) {
        for (int y = 0; y < 28; y++) {
          if (isTrain) {
            image[x][y] = data[x * 28 + y + 1];
          } else {
            image[x][y] = data[x * 28 + y];
          }
          if (image[x][y] > 0) {
            image[x][y] = 1;
          }
        }
      }
      DataItem dataItem = new DataItem();
      dataItem.setLabel(label);
      dataItem.setImage(image);
      dataItemList.add(dataItem);
    }
    in.close();
  }

  public List<DataItem> getDataItemList() {
    return dataItemList;
  }

  public void setDataItemList(List<DataItem> dataItemList) {
    this.dataItemList = dataItemList;
  }
}
