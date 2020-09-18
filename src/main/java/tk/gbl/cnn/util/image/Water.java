package tk.gbl.cnn.util.image;

/**
 * 滴水算法分割
 *
 *  @author Tian.Dong
 */
public class Water {

  public static void water(int[][] img){
    down(img);
  }



  public static void down(int[][] img){
    for(int h=0;h<img.length;h++){
      for(int w=0;w<img[h].length;w++){
        Point p = new Point(h,w);
        if(isFirst(img,p)){
          if(img[h][w+1] == 1){
            p.right();
          }
          down(img,p);
          return;
        }
      }
    }
  }

  private static boolean isFirst(int[][] img, Point p) {
    //左边必须是背景
    if(img[p.getH()-1<0?0:p.getH()][p.getW()] == 0){
      return false;
    }
    //右边必须存在前景
    for(int w=p.getW();w<img[p.getH()].length;w++){
      if(img[p.getH()][p.getW()] == 1) {
        return true;
      }
    }
    return false;
  }

  public static void down(int[][] img,Point p){
    for (int h = 0; h < img.length; h++) {
      for (int w = 0; w < img[h].length; w++) {
        System.out.print(img[h][w]);
      }
      System.out.println();
    }
    System.out.println("***");

    img[p.getH()][p.getW()] = 8;
    int h = p.getH();
    int w = p.getW();
    //边界
    if(h+1 == img.length) {
      return;
    }
    //下是白的
    else if(img[h+1][w] == 0){
      p.down();
      down(img,p);
    }
    //右边界
    else if(w+1 == img[h].length){
      return;
    }
    //右下是白的
    else if(img[h+1][w+1] == 0) {
      p.right();
      p.down();
      down(img,p);
    }
    //左边界
    else if(h == 0) {
      return;
    }
    //左下是白的
    else if(img[h+1][w+1] == 0){
      p.left();
      p.down();
      down(img,p);
    }
    //右是白的
    else if(img[h][w+1] == 0){
      p.right();
      down(img,p);
    }
    //左是白的
    else if(img[h][w-1] == 0){
      p.left();
      down(img,p);
    }
    //向下
    else {
      p.down();
      down(img,p);
    }
  }



}
