package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 二值化
 * <p/>
 * Date: 2014/9/22
 * Time: 13:56
 *
 * @author Tian.Dong
 */
public class BinaryOstu {

  public static int[][] gray(BufferedImage bufferedImage){
    int h = bufferedImage.getHeight();
    int w = bufferedImage.getWidth();
    // 灰度化
    int[][] gray = new int[w][h];
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        int argb = bufferedImage.getRGB(x, y);
        // 图像加亮（调整亮度识别率非常高）
        int r = (int) (((argb >> 16) & 0xFF));
        int g = (int) (((argb >> 8) & 0xFF));
        int b = (int) (((argb >> 0) & 0xFF));
        if (r >= 255) {
          r = 255;
        }
        if (g >= 255) {
          g = 255;
        }
        if (b >= 255) {
          b = 255;
        }
        int grayPixel = (int) ((b * 29 + g * 150 + r * 77 + 128) >> 8);
        gray[x][y] = grayPixel;
      }
    }
    return gray;
  }

  public static int[][] deal(BufferedImage bufferedImage) {
    int h = bufferedImage.getHeight();
    int w = bufferedImage.getWidth();
    //System.out.println(h + " " + w);
    int[][] img = new int[h][w];

    int[][] gray = gray(bufferedImage);

    // 二值化
    int threshold = ostu(gray, w, h);
    BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        if (gray[x][y] > threshold) {
          gray[x][y] |= 0x00FFFF;
        } else {
          gray[x][y] &= 0xFF0000;
        }
        binaryBufferedImage.setRGB(x, y, gray[x][y]);
      }
    }

    // 矩阵打印
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (isBlack(binaryBufferedImage.getRGB(x, y))) {
          img[y][x] = 1;
        } else {
          img[y][x] = 0;
        }
//        if (binaryBufferedImage.getRGB(x, y)!=-1) {
//          img[y][x] = 1;
//        } else {
//          img[y][x] = 0;
//        }
      }
    }

    return img;
  }

  public static int[][] deal(File file) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(file);
    bufferedImage = ConBriFilter.greyFilter(bufferedImage);
    return deal(bufferedImage);
  }

  public static int[][] gray(File file) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(file);
    return gray(bufferedImage);
  }

  public static int ostu(int[][] gray, int w, int h) {
    int[] histData = new int[256];
    // Calculate histogram
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        int red = 0xFF & gray[x][y];
        histData[red]++;
      }
    }

    // Total number of pixels
    int total = w * h;

    float sum = 0;
    for (int t = 0; t < 256; t++)
      sum += t * histData[t];

    float sumB = 0;
    int wB = 0;
    int wF = 0;

    float varMax = 0;
    int threshold = 0;

    for (int t = 0; t < 256; t++) {
      wB += histData[t]; // Weight Background
      if (wB == 0)
        continue;

      wF = total - wB; // Weight Foreground
      if (wF == 0)
        break;

      sumB += (float) (t * histData[t]);

      float mB = sumB / wB; // Mean Background
      float mF = (sum - sumB) / wF; // Mean Foreground

      // Calculate Between Class Variance
      float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

      // Check if new maximum found
      if (varBetween > varMax) {
        varMax = varBetween;
        threshold = t;
      }
    }

    return threshold;
  }

  public static boolean isBlack(int colorInt) {
    Color color = new Color(colorInt);
    if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
      return true;
    }
    return false;
  }

  public static boolean isWhite(int colorInt) {
    Color color = new Color(colorInt);
    if (color.getRed() > 200 && color.getGreen() > 200 && color.getBlue() > 200) {
      return true;
    }
    return false;
  }

}
