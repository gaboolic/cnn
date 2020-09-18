package tk.gbl.cnn.numbertest;

import tk.gbl.cnn.util.image.ArrayToImage;
import tk.gbl.cnn.util.image.Binary;

import java.io.File;
import java.io.IOException;

/**
 * Date: 2017/1/19
 * Time: 10:19
 *
 * @author Tian.Dong
 */
public class BinaryTest {
    public static void main(String[] args) throws IOException {
        int[][] img = Binary.deal(new File(""));
        ArrayToImage.createImage(img,new File(""));
    }
}
