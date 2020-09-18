package tk.gbl.cnn.util;

import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;


import java.io.File;
import java.io.IOException;

/**
 * Date: 2016/6/30
 * Time: 11:14
 *
 * @author Tian.Dong
 */
public class MatrixUtilTest {
  public static void main(String[] args) throws IOException {
    int[][] image = Binary.deal(new File("E:\\DT\\cnn\\chinese\\案.png"));
    //double[][] matrix = MatrixUtil.shapeChange2(MatrixUtil.int2double(image));
    double[][] matrix = MatrixUtil.shapeChange(MatrixUtil.int2double(image));
    ArrayToImage.createImage(MatrixUtil.double2int(matrix), new File("E:\\DT\\cnn\\test.png"));
  }
}
