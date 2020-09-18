package tk.gbl.cnn.util.image;

/**
 * Date: 2014/9/25
 * Time: 10:48
 *
 * @author Tian.Dong
 */
public class Distance {

  public int distance(int[][] a, Point s, int[][] tem) {
    int edit = editDistance(a, s, tem);
    return 0;
  }

  public int hamDistance(int[][] a, Point s, int[][] b) {
    int h = a.length < b.length ? a.length : b.length;
    int w = a[0].length < b[0].length ? a[0].length : b[0].length;
    int count = 0;
    for (int i = s.getH(); i < h; i++) {
      for (int j = s.getW(); j < w; j++) {
        if (b[i][j] == 0) {
          continue;
        }
        if (a[i][j] != b[i][j]) {
          count++;
        }
      }
    }
    return count;
  }

  public int hamDistance(int[][] a, int[][] b) {
    int h = a.length < b.length ? a.length : b.length;
    int w = a[0].length < b[0].length ? a[0].length : b[0].length;
    int count = 0;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        if (a[i][j] != b[i][j]) {
          count++;
        }
      }
    }
    return count;
  }

  public int editDistance(int[][] a, Point s, int[][] tem) {
    int n = min(s.getH() + tem.length, a.length);
    int m = min(s.getW() + tem[0].length, a[0].length);
    int[] a1 = new int[n * m];
    int[] b1 = new int[tem.length * tem[0].length];

    for (int i = s.getH(); i < n; i++) {
      for (int j = s.getW(); j < m; j++) {
        a1[i * j] = a[i][j];
      }
    }
    for (int i = 0; i < tem.length; i++) {
      for (int j = 0; j < tem[i].length; j++) {
        b1[i * j] = tem[i][j];
      }
    }
    return editDistance(a1, b1);
  }

  public int editDistance(int[][] a, Point s, Point e, int[][] tem, Point ts, Point te) {
    int n = e.getH() - s.getH();
    int m = e.getW() - s.getW();
    int[] a1 = new int[n * m + 10];
    int[] b1 = new int[(te.getH() - ts.getH()) * (te.getW() - ts.getW())];

    for (int i = 0; i < e.getH() - s.getH(); i++) {
      for (int j = 0; j < e.getW() - s.getW(); j++) {
        a1[i * j] = a[i][j];
      }
    }
    for (int i = 0; i < te.getH() - ts.getH(); i++) {
      for (int j = 0; j < te.getW() - ts.getW(); j++) {
        b1[i * j] = tem[i][j];
      }
    }
    return editDistance(a1, b1);
  }

  public int editDistance(int[][] a, int[][] b) {
    //Cut.cut(a);Cut.cut(b);
    int[] a1 = new int[a.length * a[0].length];
    int[] b1 = new int[b.length * b[0].length];

    for (int h = 0; h < a.length; h++) {
      for (int w = 0; w < a[h].length; w++) {
        a1[h*a[0].length + w] = a[h][w];
      }
    }

    for (int h = 0; h < b.length; h++) {
      for (int w = 0; w < b[h].length; w++) {
        b1[h*b[0].length + w] = b[h][w];
      }
    }
    return editDistance(a1, b1);
  }
  public int editDistance(int[][] a, int[] tmg) {
    int[] a1 = new int[a.length * a[0].length];
    for (int h = 0; h < a.length; h++) {
      for (int w = 0; w < a[h].length; w++) {
        a1[h*a[0].length + w] = a[h][w];
      }
    }

    return editDistance(a1, tmg);
  }

  public int editDistance(int[] a, int[] b) {
    int n = a.length;
    int m = b.length;
    if (n == 0) {
      return m;
    } else if (m == 0) {
      return n;
    }

    int[][] d = new int[n + 1][m + 1];
    for (int i = 0; i <= n; i++) {
      //初始化第一列
      d[i][0] = i;
    }
    for (int j = 0; j <= m; j++) {
      //初始化第一行
      d[0][j] = j;
    }

    for (int i = 1; i <= n; i++) {
      int a1 = a[i - 1];
      for (int j = 1; j <= m; j++) {
        int b1 = b[j - 1];
        int temp = 1;
        if (a1 == b1) {
          temp = 0;
        }
        d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
      }
    }
    return d[n][m];
  }

  public int hamDistance(int[] a, int[] b) {
    int count = 0;
    for (int i = 0; i < min(a.length, b.length); i++) {
      if (a[i] != b[i]) {
        count++;
      }
    }
    return count;
  }

  private int min(int a, int b, int c) {
    return a < b ?
        (a < c ? a : c)
        :
        (b < c ? b : c);
  }

  static int[] table = {
      1, 2, 3, 4, 5
  };

  static int[] table2 = {
      1, 2, 4, 5, 3
  };

  public static void main(String[] args) {
    int c = new Distance().editDistance(table, table2);
  }

  private int min(int a, int b) {
    return a < b ? a : b;
  }

  private int max(int a, int b) {
    return a > b ? a : b;
  }



}
