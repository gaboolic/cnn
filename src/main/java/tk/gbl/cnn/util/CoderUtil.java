package tk.gbl.cnn.util;

import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoderUtil {

  public static Map<String, Integer> gb2312Map = new HashMap<>();
  public static Map<Integer, String> gb2312Map2 = new HashMap<>();

  static{
    genGB2312Map();
  }

  public static void genGB2312Map() {
    int index = 0;
    for (int q = 16; q <= 55; q++)
      for (int w2 = (q == 55) ? 89 : 94, w = 1; w <= w2; w++) {
        int b1 = q + 0xa0;
        int b2 = w + 0xa0;
        int tag = ((b1 << 8) + (b2 << 0));
        String ch = gb2312ToUtf8(tag);
        gb2312Map.put(ch, index);
        gb2312Map2.put(index, ch);
        index++;
      }
  }

  public static String gb2312ToUtf8(int tag) {
    byte[] src = new byte[2];
    src[0] = (byte) (tag >> 8);
    src[1] = (byte) (tag & 0xFF);
    try {
      return new String(src, "gb2312");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static int utf8ToGB2312(String ch) throws UnsupportedEncodingException {
    byte[] bytes = ch.getBytes("gb2312");
    int b1 = bytes[0];
    int b2 = bytes[1];
    int result = ((b1 + 256) << 8) + ((b2 + 256) << 0);
    return result;
  }

}  