package tk.gbl.cnn.util.image;

/**
 * Date: 2014/9/25
 * Time: 15:28
 *
 * @author Tian.Dong
 */
public class TemplateCompare {

  public int compare(int[][] img, int[][] temp) {
    Distance distance = new Distance();
//    Point imgLeftFirst = getLeftFirst(img);
//    Point temLeftFirst = getLeftFirst(temp);
//    Point temUpFirst = getUpFirst(temp);
//    Point temRight = getRightFirst(temp);
//    Point temDown = getDownFirst(temp);
//
//    //Point imgS = new Point(imgLeftFirst.getH() - (temLeftFirst.getH() - temUpFirst.getH()), imgLeftFirst.getW());
//    //Point imgE = new Point();
//    Point imgS = new Point(5,10);
//    Point imgE = new Point(19,26);
//    Point ts = new Point(temUpFirst.getH(),temLeftFirst.getW());
//    Point te = new Point(temDown.getH(),temRight.getW());
    int editCount = distance.editDistance(img, temp);
    return editCount;
  }

  public int compare(int[][] imgl, int[] tmg) {
    Distance distance = new Distance();
    int editCount = distance.editDistance(imgl, tmg);
    return editCount;
  }


  public int compareHam(int[][] img, int[][] temp) {
    Distance distance = new Distance();
    Point imgFirst = getLeftFirst(img);
    Point temFirst = getLeftFirst(temp);
    //Point startPoint = new Point(imgFirst.getH() - temFirst.getH(), imgFirst.getW());
    int hamCount = distance.hamDistance(img, temp);
    return hamCount;
  }

  public Point getLeftFirst(int[][] temp) {
    Point tp;
    for (int w = 0; w < temp[0].length; w++) {
      for (int h = 0; h < temp.length; h++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return null;
  }

  public Point getUpFirst(int[][] temp) {
    Point tp;
    for (int h = 0; h < temp.length; h++) {
      for (int w = 0; w < temp[0].length; w++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return null;
  }

  public Point getUpFirstForLeftJudge(int[][] temp) {
    Point tp = null;
    for (int h = 0; h < temp.length; h++) {
      for (int w = 0; w < temp[0].length/8; w++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    for (int h = 0; h < temp.length; h++) {
      for (int w = 0; w < temp[0].length/6; w++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    for (int h = 0; h < temp.length; h++) {
      for (int w = 0; w < temp[0].length/4; w++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    if(tp == null) {
      return getUpFirst(temp);
    }
    return null;
  }



  public Point getDownFirst(int[][] temp) {
    Point tp;
    for (int h = temp.length-1; h >0; h--) {
      for (int w = temp[0].length - 1; w > 0; w--) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return new Point(temp.length-1,0);
  }

  public Point getRightFirst(int[][] temp) {
    Point tp;
    for (int w = temp[0].length - 1; w > 0; w--) {
      for (int h = 0; h < temp.length; h++) {
        if (temp[h][w] == 1) {
          tp = new Point(h, w);
          return tp;
        }
      }
    }
    return new Point(0,temp[0].length - 1);
  }



}
