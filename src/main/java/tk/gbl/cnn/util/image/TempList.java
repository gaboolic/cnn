package tk.gbl.cnn.util.image;

import java.util.ArrayList;
import java.util.List;

public class TempList {
  private static List<String> list = new ArrayList<String>();

  public static List<String> getList() {
    return list;
  }

  public static void add(String td) {
    list.add(td);
  }

  public static void removeAll(){
    list = new ArrayList<String>();
  }
}