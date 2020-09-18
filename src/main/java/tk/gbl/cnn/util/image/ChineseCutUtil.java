package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Date: 2016/7/12
 * Time: 14:42
 *
 * @author Tian.Dong
 */
public class ChineseCutUtil {
  public static List<BufferedImage> cutWord(BufferedImage image) {
    List<Integer> rgbList = new ArrayList<>();
    for (int w = 0; w < image.getWidth(); w++) {
      int averageRgb = 0;
//      FatArrayList<Integer> fatArrayList = new FatArrayList<>();
      for (int h = 0; h < 10; h++) {
        int argb = image.getRGB(w, h);
//        fatArrayList.add(argb);
        averageRgb += argb;
      }
      averageRgb = (int) (averageRgb * 1.0 / image.getHeight());
      //Collections.sort(fatArrayList);
      //averageRgb = fatArrayList.getMedian();
      rgbList.add(averageRgb);
    }

    int[] rgbFCList = new int[rgbList.size()];
    int[] rgbFCIndexList = new int[rgbList.size()];
    for (int i = 1; i < rgbList.size(); i++) {
      int prev = rgbList.get(i - 1);
      int curr = rgbList.get(i);
      rgbFCList[i] = Math.abs(curr - prev);
      rgbFCIndexList[i] = i;
    }
    for (int i = 0; i < rgbFCList.length; i++) {
      for (int j = i; j < rgbFCList.length; j++) {
        if (rgbFCList[i] < rgbFCList[j]) {
          int temp = rgbFCList[i];
          rgbFCList[i] = rgbFCList[j];
          rgbFCList[j] = temp;

          int tempIndex = rgbFCIndexList[i];
          rgbFCIndexList[i] = rgbFCIndexList[j];
          rgbFCIndexList[j] = tempIndex;
        }
      }
    }

//    int[] indexArray = new int[4];
//    indexArray[0] = rgbFCIndexList[0];
//    indexArray[1] = rgbFCIndexList[1];
//    indexArray[2] = rgbFCIndexList[2];
//    indexArray[3] = rgbFCIndexList[3];
//    Arrays.sort(indexArray);

    int l = rgbFCIndexList[0];
    int r = rgbFCIndexList[1];
    if (l < 10) {
      l = rgbFCIndexList[2];
    }
    if (r < 10) {
      r = rgbFCIndexList[2];
    }
    if (Math.abs(r - l) < 10) {
      r = rgbFCIndexList[2];
    }
    if (l > r) {
      int temp = l;
      l = r;
      r = temp;
    }


    List<BufferedImage> bufferedImages = new ArrayList<>();
    BufferedImage image1 = new BufferedImage(l, image.getHeight(), BufferedImage.TYPE_INT_RGB);
    BufferedImage image2 = new BufferedImage(r - l, image.getHeight(), BufferedImage.TYPE_INT_RGB);
    bufferedImages.add(image1);
    bufferedImages.add(image2);
    for (int w = 0; w < l; w++) {
      for (int h = 0; h < image.getHeight(); h++) {
        image1.setRGB(w, h, image.getRGB(w, h));
      }
    }
    for (int w = l; w < r; w++) {
      for (int h = 0; h < image.getHeight(); h++) {
        image2.setRGB(w - l, h, image.getRGB(w, h));
      }
    }
    return bufferedImages;
  }

  public static List<int[][]> cutCharacter2(File file) throws IOException {
    int[][] img = Binary.deal(file);
    img = Cut.cut(img);
    List<int[][]> images = Cut.cutHistogram(img);
    List<int[][]> imgs = new ArrayList<>();
    if(images == null) {
      List<int[][]> igs = cutAvgCharacter(img);
      imgs.addAll(igs);
      return imgs;
    }
    for (int i = 0; i < images.size(); i++) {
      int[][] image = images.get(i);
      int wight = image[0].length;
      if (wight > 20) {
        List<int[][]> igs = cutAvgCharacter(image);
        imgs.addAll(igs);
      } else {
        imgs.add(image);
      }
    }
    return imgs;
  }

  private static List<int[][]> cutAvgCharacter(int[][] temp) {
    int wight = temp[0].length;
    if (wight > 20 && wight <= 40) {
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
      int[][] t1 = new int[temp.length][index];
      for (int i = 0; i < temp.length; i++) {
        for (int j = 0; j < index; j++) {
          t1[i][j] = temp[i][j];
        }
      }
      int[][] t2 = new int[temp.length][temp[0].length - index];
      for (int i = 0; i < temp.length; i++) {
        for (int j = index; j < temp[0].length; j++) {
          t2[i][j - index] = temp[i][j];
        }
      }

      List<int[][]> images = new ArrayList<>();
      images.add(t1);
      images.add(t2);
      return images;
    } else if(wight>=32 && wight<46) {
      int m1 = wight/3;
      int m2 = m1*2;

      int[][] t1 = new int[temp.length][m1];
      for (int i = 0; i < temp.length; i++) {
        for (int j = 0; j < m1; j++) {
          t1[i][j] = temp[i][j];
        }
      }
      int[][] t2 = new int[temp.length][m2-m1];
      for (int i = 0; i < temp.length; i++) {
        for (int j = m1; j < m2; j++) {
          t2[i][j - m1] = temp[i][j];
        }
      }

      int[][] t3 = new int[temp.length][wight-m2];
      for (int i = 0; i < temp.length; i++) {
        for (int j = m2; j < temp[0].length; j++) {
          t3[i][j - m2] = temp[i][j];
        }
      }
      List<int[][]> images = new ArrayList<>();
      images.add(t1);
      images.add(t2);
      images.add(t3);
      return images;
    } else {
      try {
        ArrayToImage.createImage(temp,new File("F:\\验证码\\new12306\\cut_ch_test\\test.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(wight);
    }
    return null;
  }

  public static List<BufferedImage> cutCharacter333(BufferedImage image) {
    List<BufferedImage> images = new ArrayList<>();
    BufferedImage image1 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int w = 0; w < image.getWidth(); w++) {
      int blackCount = 0;
      for (int h = 0; h < image.getHeight(); h++) {
        int argb = image.getRGB(w, h);
        if (argb != -1) {
          blackCount++;
        }
      }
      for (int i = image.getHeight() - 1; i > image.getHeight() - 1 - blackCount; i--) {
        image1.setRGB(w, i, -1);
      }
      System.out.println(blackCount);
    }
    images.add(image1);
    return images;
  }
}
