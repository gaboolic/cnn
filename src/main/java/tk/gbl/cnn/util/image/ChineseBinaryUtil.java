package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 2016/7/12
 * Time: 16:15
 *
 * @author Tian.Dong
 */
public class ChineseBinaryUtil {

  public static int[][] dealBlack(File file) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(file);
    int h = bufferedImage.getHeight();
    int w = bufferedImage.getWidth();
    //System.out.println(h + " " + w);
    int[][] img = new int[h][w];


    // 矩阵打印
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (isBlack(bufferedImage.getRGB(x, y))) {
          img[y][x] = 1;
        } else {
          img[y][x] = 0;
        }
      }
    }

    return img;
  }

  public static int[][] gray(File file) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(file);
    int h = bufferedImage.getHeight();
    int w = bufferedImage.getWidth();
    //System.out.println(h + " " + w);
    int[][] img = new int[h][w];


    // 矩阵打印
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int argb = bufferedImage.getRGB(x, y);
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = (argb >> 0) & 0xFF;
        int grayPixel = (int) ((b * 29 + g * 150 + r * 77 + 128) >> 8);
        grayPixel = (int) Math.pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);

        img[y][x] = grayPixel;
      }
    }

    return img;
  }

  public static boolean isBlack(int colorInt) {
    Color color = new Color(colorInt);
    if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
      return true;
    }
    return false;
  }
}
