package tk.gbl.cnn.util.image;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

public class ConBriFilter {

  static double contrast = 2;
  static double brightness = 2.5;

  public static int clamp(int value) {
    return value > 255 ? 255 : (value < 0 ? 0 : value);
  }

  public static BufferedImage greyFilter(BufferedImage src) {
    //获得源图片长度和宽度
    int width = src.getWidth();
    int height = src.getHeight();
    BufferedImage dest = new BufferedImage(width, height, src.getType());
    int[] inPixels = new int[width * height];
    int[] outPixels = new int[width * height];
    src.getRGB(0, 0, width, height, inPixels, 0, width);

    //计算一个像素的红，绿，蓝方法
    int index = 0;
    int[] rgbmeans = new int[3];
    double redSum = 0, greenSum = 0, blueSum = 0;
    double total = height * width;
    for (int row = 0; row < height; row++) {
      int ta = 0, tr = 0, tg = 0, tb = 0;
      for (int col = 0; col < width; col++) {
        index = row * width + col;
        ta = (inPixels[index] >> 24) & 0xff;
        tr = (inPixels[index] >> 16) & 0xff;
        tg = (inPixels[index] >> 8) & 0xff;
        tb = inPixels[index] & 0xff;
        redSum += tr;
        greenSum += tg;
        blueSum += tb;
      }
    }
    //求出图像像素平均值
    rgbmeans[0] = (int) (redSum / total);
    rgbmeans[1] = (int) (greenSum / total);
    rgbmeans[2] = (int) (blueSum / total);

    //调整对比度，亮度
    for (int row = 0; row < height; row++) {
      int ta = 0, tr = 0, tg = 0, tb = 0;
      for (int col = 0; col < width; col++) {
        index = row * width + col;
        ta = (inPixels[index] >> 24) & 0xff;
        tr = (inPixels[index] >> 16) & 0xff;
        tg = (inPixels[index] >> 8) & 0xff;
        tb = inPixels[index] & 0xff;

        //移去平均值
        tr -= rgbmeans[0];
        tg -= rgbmeans[1];
        tb -= rgbmeans[2];

        //调整对比度
        tr = (int) (tr * contrast);
        tg = (int) (tg * contrast);
        tb = (int) (tb * contrast);

        //调整亮度
        tr = (int) ((tr + rgbmeans[0]) * brightness);
        tg = (int) ((tg + rgbmeans[1]) * brightness);
        tb = (int) ((tb + rgbmeans[2]) * brightness); //end;
                /*tr +=(int)(rgbmeans[0] * brightness);
                tg +=(int)(rgbmeans[1] * brightness);
                tb +=(int)(rgbmeans[2] * brightness);  //end;*/

        outPixels[index] = (ta << 24) | (clamp(tr) << 16) | (clamp(tg) << 8) | clamp(tb);

      }
    }
    dest.setRGB(0, 0, width, height, outPixels, 0, width);
    return dest;
  }

}  