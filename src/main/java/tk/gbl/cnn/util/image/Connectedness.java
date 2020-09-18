package tk.gbl.cnn.util.image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基于连通性分割
 *
 * Date: 2014/9/22
 * Time: 14:44
 *
 * @author Tian.Dong
 */
public class Connectedness {


  List<Point> pointSet = new ArrayList<Point>();
  Set<Point> dealPoint = new HashSet<Point>();

  public List<Point> getPointSet() {
    return pointSet;
  }

  public void setPointSet(List<Point> pointSet) {
    this.pointSet = pointSet;
  }

  public Set<Point> getDealPoint() {
    return dealPoint;
  }

  public void setDealPoint(Set<Point> dealPoint) {
    this.dealPoint = dealPoint;
  }

  public void around(int[][] img,int h,int w){
    if(dealPoint.contains(new Point(h,w))) {
      return;
    }
    dealPoint.add(new Point(h,w));
    for(int i=h-1>0?h-1:0;i<=(h+1<img.length-1?h+1:img.length-1);i++){
      for(int j=w-1>0?w-1:0;j<=(w+1<img[h].length-1?w+1:img[h].length-1);j++) {
        if(i==h && j==w) {
          continue;
        }
        if(img[i][j] == 1) {
          Point point = new Point(i,j);
          if(!pointSet.contains(point)) {
            pointSet.add(new Point(i, j));
          }
        }
      }
    }
    for(int i=0;i<pointSet.size();i++) {
      Point p = pointSet.get(i);
      around(img,p.getH(),p.getW());
    }
  }
}
