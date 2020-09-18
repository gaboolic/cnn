package tk.gbl.cnn;

import org.junit.Test;
import tk.gbl.cnn.core.Cnn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

/**
 * Date: 2016/6/6
 * Time: 15:44
 *
 * @author Tian.Dong
 */
public class TestDeserializ {
  @Test
  public void test() throws Exception {
    //反序列化对象
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\cnn.obj"));
    Cnn cnn = (Cnn) in.readObject();
    in.close();
  }
}
