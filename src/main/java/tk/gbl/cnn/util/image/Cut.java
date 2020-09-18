package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: 2014/9/26
 * Time: 10:05
 *
 * @author Tian.Dong
 */
public class Cut {
  public static void main(String[] args) throws Exception {
    int[][] temp = new Binary().deal(new File("F:\\codeT\\lHR.png"));
    int[][] t = new Cut().cut(temp);
    Output.output(t);

    BufferedImage image = new BufferedImage(t[0].length, t.length,
        BufferedImage.TYPE_INT_RGB);
    for (int h = 0; h < t.length; h++) {
      for (int w = 0; w < t[h].length; w++) {
        if (t[h][w] == 1) {
          image.setRGB(w, h, 0);
        } else {
          image.setRGB(w, h, 0xffffff);
        }
      }
    }

    File file = new File("F:/codeTC/test.png");
    ImageIO.write(image, "PNG", file);

    int[][] ttt = new Binary().deal(new File("F:/codeTC/test.png"));
    Output.output(ttt);
  }

  public static int[][] cutLeftRight(int[][] temp) {
    TemplateCompare template = new TemplateCompare();
    Point temLeftFirst = template.getLeftFirst(temp);
    Point temUpFirst = new Point(0, 0);
    Point temRight = template.getRightFirst(temp);
    Point temDown = new Point(temp.length - 1, 0);

    Point leftUp = new Point(temUpFirst.getH(), temLeftFirst.getW());
    Point rightDown = new Point(temDown.getH(), temRight.getW());

    int[][] t = new int[rightDown.getH() - leftUp.getH() + 1][rightDown.getW() - leftUp.getW() + 1];
    for (int i = leftUp.getH(); i <= rightDown.getH(); i++) {
      for (int j = leftUp.getW(); j <= rightDown.getW(); j++) {
        t[i - leftUp.getH()][j - leftUp.getW()] = temp[i][j];
      }
    }

    return t;
  }

  public static int[][] cut(int[][] temp) {
    TemplateCompare template = new TemplateCompare();
    Point temLeftFirst = template.getLeftFirst(temp);
    //Point temUpFirst = template.getUpFirst(temp);
    Point temRight = template.getRightFirst(temp);
    //Point temDown = template.getDownFirst(temp);

    int[][] t = new int[temp.length][temRight.getW() - temLeftFirst.getW() + 1];
    for (int i = 0; i < temp.length; i++) {
      for (int j = temLeftFirst.getW(); j <= temRight.getW(); j++) {
        t[i][j - temLeftFirst.getW()] = temp[i][j];
      }
    }

    return t;
  }
  public static List<int[][]> cutParts(int[][] image, int count) {
    List<int[][]> parts = new ArrayList<>();
    int singleWidth = image[0].length/count;
    for(int i=0;i<count;i++) {
      int[][] part = new int[image.length][singleWidth];
      for(int x = 0;x<singleWidth;x++) {
        for(int y = 0;y<image.length;y++) {
          try {
            part[y][x] = image[y][x+i*singleWidth];
          }catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      parts.add(part);
    }
    return parts;
  }
  public static List<int[][]> cut4Parts(int[][] img) {
    int[][] left = Cut.cut2(img, 0);
    List<int[][]> lefts = Cut.cutIncline(left);

    int[][] right = Cut.cut2(img, 1);
    List<int[][]> rights = null;
    rights = Cut.cutIncline(right);

    List<int[][]> parts = new ArrayList<int[][]>();
    parts.addAll(lefts);
    parts.addAll(rights);

    return parts;
  }

  public static int[][] cutForLeftJudge(int[][] temp) {
    TemplateCompare template = new TemplateCompare();
    Point temLeftFirst = template.getLeftFirst(temp);
    Point temUpFirst = template.getUpFirstForLeftJudge(temp);
    Point temRight = template.getRightFirst(temp);
    Point temDown = template.getDownFirst(temp);

    Point leftUp = null;
    Point rightDown = null;
    try {
      leftUp = new Point(temUpFirst.getH(), temLeftFirst.getW());
      rightDown = new Point(temDown.getH(), temRight.getW());
    } catch (Exception e) {
      e.printStackTrace();
    }
    int[][] t = new int[rightDown.getH() - leftUp.getH() + 1][rightDown.getW() - leftUp.getW() + 1];
    for (int i = leftUp.getH(); i <= rightDown.getH(); i++) {
      for (int j = leftUp.getW(); j <= rightDown.getW(); j++) {
        t[i - leftUp.getH()][j - leftUp.getW()] = temp[i][j];
      }
    }

    return t;
  }

  public static int[][] cut(int[][] temp, int count) {
    int width = temp[0].length / 4;
    int[][] t = new int[temp.length][temp[0].length / 4];
    for (int i = 0; i < temp.length; i++) {
      for (int j = count * width; j < count * width + width; j++) {
        t[i][j - count * width] = temp[i][j];
      }
    }

    return t;
  }

  public static int[][] cutFirst(int[][] temp) {
    int[][] t = new int[temp.length][20];
    for (int i = 0; i < temp.length; i++) {
      for (int j = 0; j < 20; j++) {
        t[i][j] = temp[i][j];
      }
    }

    return t;
  }

  public static int[][] cut2(int[][] temp, int count) {
    int mid = temp[0].length / 2;
    List<Integer> list = new ArrayList<Integer>();
    for (int i = mid - 2; i < mid + 3; i++) {
      int sum = 0;
      for (int h = 0; h < temp.length; h++) {
        if (temp[h][i] == 1) {
          sum++;
        }
      }
      list.add(sum);
    }
    int index = mid;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < list.size(); i++) {
      if (min > list.get(i)) {
        min = list.get(i);
        index = mid - 2 + i;
      }
    }
    if (list.get(2).equals(0) || list.get(2).equals(min)) {
      index = mid;
    }
    int[][] t = null;
    if (count == 0) {
      t = new int[temp.length][index];
      for (int i = 0; i < temp.length; i++) {
        for (int j = 0; j < index; j++) {
          t[i][j] = temp[i][j];
        }
      }
    } else {
      t = new int[temp.length][temp[0].length - index];
      for (int i = 0; i < temp.length; i++) {
        for (int j = index; j < temp[0].length; j++) {
          t[i][j - index] = temp[i][j];
        }
      }
    }
    return t;
  }

  public static int[][] cutInclineLeft(int[][] temp) {
    int mid = temp[0].length / 2;
    int wl = mid / 2;
    int wr = mid + mid / 2;

    double x = 1.0 * mid / temp.length;
    int[][] t = new int[temp.length][wr];
    for (int h = 0; h < temp.length; h++) {
      double ww = x * h + wl;
      for (int w = 0; w < temp[h].length; w++) {
        if (w > ww) {
          continue;
        }
        t[h][w] = temp[h][w];
      }
    }
    return t;
  }

  public static int[][] cutInclineRight(int[][] temp) {
    int mid = temp[0].length / 2;
    int wl = (int) (temp[0].length * 0.25);
    int wr = (int) (temp[0].length * 0.75);

    double x = 1.0 * mid / temp.length;
    int[][] t = new int[temp.length][wr + 1];
    for (int h = 0; h < temp.length; h++) {
      double ww = x * h + wl;
      for (int w = 0; w < temp[0].length; w++) {
        if (w < ww) {
          continue;
        }
        t[h][w - wl] = temp[h][w];
      }
    }
    return t;
  }

  public static List<int[][]> cutConnectedness(int[][] temp) {
    Separate separate = new Separate();
    separate.separateConnectedness(temp);
    Map<Point, List<Point>> map = separate.getMap();


    List<int[][]> list = new ArrayList<int[][]>();
    List<Integer> counts = new ArrayList<Integer>();
    for (Map.Entry<Point, List<Point>> entry : map.entrySet()) {
//      int[][] arr = new int[26][78];
      int[][] arr = new int[temp.length][temp[0].length];
      int count = 0;
      for (Point p : entry.getValue()) {
        arr[p.getH()][p.getW()] = 1;
        count++;
      }
      counts.add(count);
      list.add(arr);
    }

    try {
      for (int i = 0; i < counts.size(); i++) {
        if (counts.get(i) < 16) {
          counts.remove(i);
          list.remove(i);
        }
      }
    } catch (Exception e) {

      Output.output(temp);
      e.printStackTrace();
      System.exit(0);
    }
    if (list.size() == 1) {
      return list;
    }


    int count1 = counts.get(0);
    int count2 = counts.get(1);
    if (count1 > count2) {
      int t = count1;
      count1 = count2;
      count2 = t;
    }
    if (count1 < 20) {
      return null;
    }
    double result = 1.0 * count1 / (count1 + count2);
    if (result < 0.9 && result > 0.1) {
      List<int[][]> arrs = new ArrayList<int[][]>();
      for (int[][] arr : list) {
        arrs.add(Cut.cut(arr));
      }
      return arrs;
    }
    List<int[][]> arrs = new ArrayList<int[][]>();
    arrs.add(temp);
    return arrs;
  }

  public static List<int[][]> cutIncline(int[][] image) {
    List<int[][]> list = cutConnectedness(image);
    int[][] temp = image;
    if (list != null && list.size() == 2) {
      return list;
    }
    if (list != null && list.size() == 1) {
      temp = Cut.cut(list.get(0));
    }

    int mid = temp[0].length / 2;
    int wl = mid / 2;
    int wr = mid + mid / 2;

    double x = 1.0 * mid / temp.length;
    double x2 = -1.0 * mid / temp.length;

    int countL1 = 0;
    int countL2 = 0;
//    for (int h = 0; h < temp.length; h++) {
//      double ww = x * h + wl;
//      double ww2 = x2 * h + wr;
//      countL1 += temp[h][(int) Math.floor(ww)];
//      countL2 += temp[h][(int) Math.floor(ww2)];
//    }
    double xf = 0;
    double yf = 0;
    if (temp.length == 0) {
      System.out.println();
    }
    if (temp.length < 5) {
      System.out.println();
    }
    if (temp[0].length < 5) {
      System.out.println();
    }
    if (LeftRightJudge.judgeIsLeft(temp)) {
      countL1 = 0;
      countL2 = 10;
    } else {
      countL1 = 10;
      countL2 = 0;
    }
    if (countL1 <= countL2) {
      xf = x;
      yf = wl;
    } else {
      xf = x2;
      yf = wr;
    }

    int[][] t1 = new int[temp.length][wr + 4];
    int[][] t2 = new int[temp.length][wr + 4];
    for (int h = 0; h < temp.length; h++) {
      double ww = xf * h + yf;
      for (int w = 0; w < temp[0].length; w++) {
        if (w < ww + 2) {
          t1[h][w] = temp[h][w];
        }
        if (w > ww) {
          t2[h][w - wl + 1] = temp[h][w];
        }

      }
    }
    list = new ArrayList<int[][]>();
    list.add(t1);
    list.add(t2);
    return list;
  }


  public static List<int[][]> cutHistogram(int[][] image) {
    List<Integer> split0Counts = new ArrayList<>();
    List<Integer> splitCounts = new ArrayList<>();
    for (int w = 0; w < image[0].length; w++) {
      int blackCount = 0;
      for (int h = 0; h < image.length; h++) {
        int argb = image[h][w];
        if (argb != 0) {
          blackCount++;
        }
      }
      if(blackCount == 0) {
        if (w < 12 || w > image[0].length - 10) {
          continue;
        }
        if (split0Counts.size() == 0) {
          split0Counts.add(w);
        } else {
          Integer prevCount = split0Counts.get(split0Counts.size() - 1);
          if (w - prevCount > 12) {
            split0Counts.add(w + 1);
          }
        }
      }else if (blackCount < 2) {
        if (w < 12 || w > image[0].length - 10) {
          continue;
        }
        if (splitCounts.size() == 0) {
          splitCounts.add(w);
        } else {
          Integer prevCount = splitCounts.get(splitCounts.size() - 1);
          if (w - prevCount > 12) {
            splitCounts.add(w + 1);
          }
        }
      }
    }
    if(split0Counts.size() != 0){
      splitCounts = split0Counts;
    }
    System.out.println(splitCounts);
    List<int[][]> imgs = new ArrayList<>();
    for (int count = 0; count < splitCounts.size(); count++) {
      int splitCount = splitCounts.get(count);
      int prevSplitCount = 0;
      if (count > 0) {
        prevSplitCount = splitCounts.get(count - 1);
      }
      int[][] t = new int[image.length][splitCount - prevSplitCount];
      for (int i = 0; i < image.length; i++) {
        for (int j = prevSplitCount; j < splitCount; j++) {
          t[i][j - prevSplitCount] = image[i][j];
        }
      }
      t = Cut.cut(t);
      imgs.add(t);
    }
    if (splitCounts.size() == 0) {
      imgs.add(image);
      return imgs;
    }
    int lastSplitCount = splitCounts.get(splitCounts.size() - 1);
    int[][] t = new int[image.length][image[0].length - lastSplitCount];
    for (int i = 0; i < image.length; i++) {
      for (int j = lastSplitCount; j < image[0].length; j++) {
        t[i][j - lastSplitCount] = image[i][j];
      }
    }
    t = Cut.cut(t);
    imgs.add(t);
    return imgs;
  }


}
