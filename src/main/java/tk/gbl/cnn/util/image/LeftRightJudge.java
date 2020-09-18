package tk.gbl.cnn.util.image;

/**
 * Date: 2014/10/21
 * Time: 16:46
 *
 * @author Tian.Dong
 */
public class LeftRightJudge {
  public static boolean judgeIsLeft(int[][] image) {
    int[][] temp = Cut.cutForLeftJudge(image);
    //Output.output(temp);
    if(temp.length ==0){
      System.out.println();
      Cut.cutForLeftJudge(image);
    }
    int ww = 0;
    for (int h = 0; h < temp.length; h++) {
      for (int w = 0; w < temp[0].length; w++) {
        if (temp[h][w] == 1) {
          ww = w;
          break;
        }
      }
    }
    int mid = 0;
    mid = temp[0].length / 2;

    return ww < mid;
  }
}
