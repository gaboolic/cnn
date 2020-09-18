package tk.gbl.cnn.util.image;

import java.io.File;
import java.io.IOException;

/**
 * Date: 2014/10/22
 * Time: 17:21
 *
 * @author Tian.Dong
 */
public class NewOCR {
  public static String sb(int[][] img) throws IOException {

    return null;
  }

  public static String ocr(int[][] img) throws IOException {
    int startWidth = 0;
    int endWidth = img[0].length;
    return ocr(img, startWidth, endWidth);
  }

  public static String ocr(int[][] img, int startWidth) throws IOException {
    int endWidth = img[0].length;
    return ocr(img, startWidth, endWidth);
  }

  public static String ocr(int[][] img, int startWidth, int endWidth) throws IOException {
    //File t = new File("F:\\12306\\temp\\3\\1413967313045.png");
    File dir = new File("F:\\12306\\temp_clean");
    for (File tempDir : dir.listFiles()) {
      for (File t : tempDir.listFiles()) {
        int[][] tmg = Binary.deal(t);

        int tmgCount = 0;
        for (int h = 0; h < tmg.length; h++) {
          for (int w = 0; w < tmg[0].length; w++) {
            if (w != 0) {
              if (tmg[h][w] == 1) {

              }
            }
          }
        }

      }
    }
    return "0";
  }

  public static String ocrx(int[][] img, int startWidth, int endWidth) throws IOException {
    //File t = new File("F:\\12306\\temp\\3\\1413967313045.png");
    File dir = new File("F:\\12306\\temp_clean");
    for (File tempDir : dir.listFiles()) {
      for (File t : tempDir.listFiles()) {
        int[][] tmg = Binary.deal(t);

        int tmgCount = 0;
        for (int h = 0; h < tmg.length; h++) {
          for (int w = 0; w < tmg[0].length; w++) {
            if (tmg[h][w] == 1) {
              tmgCount++;
            }
          }
        }
        for (int h = 0; h + tmg.length < img.length; h++) {
          for (int w = startWidth; w + tmg[0].length < endWidth; w++) {
            int count = ocr(img, tmg, h, w, tmgCount);
            if (1.0 * count / tmgCount > 0.8) {
              System.out.println(t.getName() + " " + count + " " + tmgCount + " " + (1.0 * count / tmgCount));
              return t.getParentFile().getName();
            }
          }
        }
      }
    }
    return "0";
  }

  public static int ocr(int[][] img, int[][] tmg, int x, int y, int tmgCount) {
//    int[][] imgc = new int[img.length][img[0].length];
//    for (int h = 0; h < img.length; h++) {
//      for (int w = 0; w < img[0].length; w++) {
//        imgc[h][w] = img[h][w];
//      }
//    }


    int count = 0;
    for (int h = 0; h < tmg.length; h++) {
      for (int w = 0; w < tmg[0].length; w++) {
        if (tmg[h][w] == 1) {
          if (aroundIs1(img, h + x, w + y)) {
            count++;
          }

        }
      }
    }
    return count;
  }

  private static boolean aroundIs1(int[][] img, int h, int w) {
    if (img[h][w] == 1) {
      return true;
    }
//    for(int i=Math.max(0,h-1);i<Math.min(h+2,img.length);i++) {
//      for(int j=Math.max(0,w-1);j<Math.min(w+2,img[0].length);j++) {
//        if(img[i][j] == 1) {
//          return true;
//        }
//      }
//    }

    return false;
  }
}
