package tk.gbl.cnn.util.image;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 分割
 *
 * Date: 2014/9/22
 * Time: 14:07
 *
 * @author Tian.Dong
 */
public class Separate {

  Map<Point, List<Point>> map = new LinkedHashMap<Point, List<Point>>();

  public Map<Point, List<Point>> getMap() {
    return map;
  }

  public void setMap(Map<Point, List<Point>> map) {
    this.map = map;
  }

  public void separate(int[][] img){
    //new Water().water(img);
    new Thin().thin(img);

    for (int h = 0; h < img.length; h++) {
      for (int w = 0; w < img[h].length; w++) {
        System.out.print(img[h][w]);
      }
      System.out.println();
    }
  }

  public void separateConnectedness(int[][] img) {

      for (int w = 0; w < img[0].length; w++) {
        for (int h = 0; h < img.length; h++) {
        if (img[h][w] == 0) {
          continue;
        }
        if (isHave(new Point(h, w))) {
          continue;
        }
        Connectedness connectedness = new Connectedness();
        connectedness.around(img, h, w);
          List<Point> set = connectedness.getPointSet();
        map.put(new Point(h, w), set);
      }
    }

  }

  private boolean isHave(Point point) {
    for (Map.Entry<Point, List<Point>> entry : map.entrySet()) {
      if (entry.getValue().contains(point)) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    String s = "000000000000000000001";
    System.out.println(s.length());
  }


//  {
//    System.out.println(img[2][20]);
//    Connectedness connectedness = new Connectedness();
//    connectedness.around(img, 2, 20);
//    List<Point> set = connectedness.getPointSet();
//    int[][] arr = new int[26][78];
//    for(Point p:set) {
//      System.out.println(p.getH()+" "+p.getW());
//      arr[p.getH()][p.getW()] = 1;
//    }
//
//    System.out.println("****");
//    for (int h = 0; h < arr.length; h++) {
//      for (int w = 0; w < arr[h].length; w++) {
//        System.out.print(arr[h][w]);
//      }
//      System.out.println();
//    }
//  }
}
