package tk.gbl.cnn.util;

import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;

import java.io.File;
import java.io.IOException;

/**
 * Date: 2016/7/7
 * Time: 11:12
 *
 * @author Tian.Dong
 */
public class AffineUtilTest {
  public static void main(String[] args) throws IOException {
    int[][] image = Binary.deal(new File("E:\\DT\\cnn\\jianpan_dianhuating.png"));
    //double[][] matrix = MatrixUtil.shapeChange2(MatrixUtil.int2double(image));
//    double[][] matrix = AffineUtil.shear(MatrixUtil.int2double(image),-0.3,0.3);
    double[][] matrix = AffineUtil.swap2(MatrixUtil.int2double(image));
    ArrayToImage.createImage(MatrixUtil.double2int(matrix), new File("E:\\DT\\cnn\\test.png"));
  }
}

