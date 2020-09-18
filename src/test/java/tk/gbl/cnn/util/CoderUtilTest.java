package tk.gbl.cnn.util;

import java.io.UnsupportedEncodingException;

/**
 * 45269
 * 45310
 * 45559
 * 45566
 * <p>
 * Date: 2016/6/16
 * Time: 16:34
 *
 * @author Tian.Dong
 */
public class CoderUtilTest {
  public static void main(String[] args) throws UnsupportedEncodingException {

    String a = CoderUtil.gb2312ToUtf8(45217);
    String b = CoderUtil.gb2312ToUtf8(55289);

//    System.out.println(a);
//    System.out.println(b);45473-45310
    System.out.println(CoderUtil.gb2312ToUtf8(45270));

    System.out.println(CoderUtil.utf8ToGB2312("啊"));

  }
}
