package tk.gbl.cnn.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 2016/6/6
 * Time: 10:57
 *
 * @author Tian.Dong
 */
public class ImageUtil {
  public static void writeImage(double[][] data, String fileName) throws IOException {
    BufferedImage bufferedImage = arrayToImage(data);
    writeImage(bufferedImage, new File("E:\\DT\\cnn\\test", fileName));
  }

  public static BufferedImage arrayToImage(double[][] dat) {
    BufferedImage bufferedImage = new BufferedImage(dat[0].length, dat.length, BufferedImage.TYPE_USHORT_GRAY);
    for (int h = 0; h < dat.length; h++) {
      for (int w = 0; w < dat[0].length; w++) {
        bufferedImage.setRGB(w, h, (int) (dat[h][w] * 256 * 256) + (int) (dat[h][w] * 256 + dat[h][w]));
      }
    }
    return bufferedImage;
  }

  public static void writeImage(BufferedImage bufferedImage, File file) throws IOException {
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdir();
    }
    ImageIO.write(bufferedImage, "PNG", file);
  }

  public static double[][] standard(double[][] image) {
    double[][] standardImage = new double[64][64];
    for (int h = 0; h < 64; h++) {
      for (int w = 0; w < 64; w++) {
        if (h < image.length-1) {
          if (w < image[h].length-1) {
            try {
              standardImage[h][w] = image[h][w];
            }catch (Exception e){
              e.printStackTrace();
            }
          }
        }
      }
    }
    return standardImage;
  }
  public static double[][] standard32(double[][] image) {
    double[][] standardImage = new double[64][64];
    for (int h = 0; h < 64; h++) {
      for (int w = 0; w < 64; w++) {
        if (h < image.length-1) {
          if (w < image[h].length-1) {
            try {
              standardImage[h][w] = image[h][w];
            }catch (Exception e){
              e.printStackTrace();
            }
          }
        }
      }
    }
    double[][] samplingImage = new double[32][32];
    for (int x = 0; x < samplingImage.length; x++) {
      for (int y = 0; y < samplingImage[x].length; y++) {
        double sum = 0;
        for (int i = 0; i < 2; i++) {
          for (int j = 0; j < 2; j++) {
            try {
              sum += standardImage[x * 2 + i][y * 2 + j];
            }catch (Exception e){
              e.printStackTrace();
            }
          }
        }
        samplingImage[x][y] = sum > 3 ? 1000 : 0;
      }
    }
    return samplingImage;
  }
}
