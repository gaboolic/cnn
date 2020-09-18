package tk.gbl.cnn.util.image;

/**
 * Date: 2014/9/26
 * Time: 10:05
 *
 * @author Tian.Dong
 */
public class NewCut {

  public static int[][] cut(int[][] temp, int count) {
    int width = 0;
    if(count == 0 || count ==3){
      width = (int) (1.0 * temp[0].length / 8 * 3);
    } else {
      width = (int) (1.0 * temp[0].length / 2);
    }
    int[][] t = new int[temp.length][width];
    for (int i = 0; i < temp.length; i++) {
      for (int j = Math.max(count * (temp[0].length / 4) - temp[0].length / 8,0);
           j < Math.min(count * (temp[0].length / 4) + temp[0].length / 8,temp[0].length);
           j++) {
        t[i][j - Math.max(count * (temp[0].length / 4) - temp[0].length / 8,0)] = temp[i][j];
      }
    }
    return t;
  }

  public static int[][] cut2(int[][] temp, int count) {
    int width = temp[0].length / 2;
    int[][] t = new int[temp.length][temp[0].length / 2];
    for (int i = 0; i < temp.length; i++) {
      for (int j = count * width; j < count * width + width; j++) {
        t[i][j - count * width] = temp[i][j];
      }
    }
    return t;
  }


}
