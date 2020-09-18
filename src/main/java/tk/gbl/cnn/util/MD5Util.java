package tk.gbl.cnn.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Util {
  /**
   * 默认构造方法
   */
  private MD5Util() {

  }

  /**
   * 获得MD5加密字符串
   *
   * @param source 源字符串
   * @return 加密后的字符串
   */
  public static String getMD5(String source) {
    String mdString = null;
    if (source != null) {
      try {
        mdString = getMD5(source.getBytes("UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return mdString;
  }

  /**
   * 获得MD5加密字符串
   *
   * @param source 源字节数组
   * @return 加密后的字符串
   */
  public static String getMD5(byte[] source) {
    String s = null;
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    final int temp = 0xf;
    final int arraySize = 32;
    final int strLen = 16;
    final int offset = 4;
    try {
      MessageDigest md = MessageDigest
          .getInstance("MD5");
      md.update(source);
      byte[] tmp = md.digest();
      char[] str = new char[arraySize];
      int k = 0;
      for (int i = 0; i < strLen; i++) {
        byte byte0 = tmp[i];
        str[k++] = hexDigits[byte0 >>> offset & temp];
        str[k++] = hexDigits[byte0 & temp];
      }
      s = new String(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return s;
  }
}