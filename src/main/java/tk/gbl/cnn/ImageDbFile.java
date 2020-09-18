package tk.gbl.cnn;

import tk.gbl.cnn.core.DataItem;
import tk.gbl.cnn.util.image.Binary;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2016/6/21
 * Time: 17:57
 *
 * @author Tian.Dong
 */
public class ImageDbFile {

  String chs = "T七丝中丸丹乐乒乓书云亭亮人仁仔他仙仪件企保像光克兔全公典冰凉几刀创刷刺剪力办加动勋包匙千南卜卫印卷叉古可台叶吉向听吸味咖品哑啡啤喷器囊围国图圆圈土圣地块坪垫堡塑塔墨壳壶大天太头夹套奶姜娃子字安小尘尺山巧巾币布帐带帽床开弹彩心恐恤戒戳户扇手打扣扫扳把护报披拉拍拖拨拼指挖挞掌排掘提摩擦收救文斑斗料方日明星春月服木本机杏条杯板枕果枣架柚柜柴柿标树核栽桃案桌档桥梅梯梳棋椅椒椰楂楼榔榨榴槟樟樱橙母毛民气水汁汉池汤沙油泉泳洋洗流浏浪海消液淇淋温游湿滩漏漠瀑火灯炭炮烛烟烤热煤熊熨爆片牌牙牛物犀狐狗狮狸猕猫猬猴玉玻珍珠球琴璃瓜瓢瓶生电白皂皮盆盒盖盘相眼石矿砂硬磁示福秋秤空窗窝章竹笋笔笼筒筝算管箭箱篮篷簧米类粉粽糕糖紫红纳纸纽线绒结绳绿羽翠翡老联肥胎胶脑腕膏自舞舟航艇色芒芦花芽苍英茶草药荷莓菜菠萝萨葫葱葵蒙蒲蒸薯藕虎虫虹虾蚁蚂蚊蛋蛙蜂蜓蜗蜜蜡蜻蝇蝴蝶螃螺蟹行衣表袋被装裙裤西视览警诊话诞调豆豚象豹贝购贴赛路跳躺车轨轮轿辣运透邮酒酱钥钻铁铃铛银链锅锣键锹镜长门闪防阳雕露青面靴鞋鞭音顶领颈风飞餐饨饭饼馄馆馒香马驼骆魔鱼鲨鲸鳄鸟鸡鸥鸭鸽鹅鹤鹰鹿麦麻黄黑鼓鼠齿龄龙";
  Map<String, Integer> ziMap = new HashMap<>();
  Map<Integer, String> izMap = new HashMap<>();

  List<DataItem> dataItemList = new ArrayList<>();

  public ImageDbFile(String pathStr) throws IOException {
    File path = new File(pathStr);
    File[] files = path.listFiles();
    for (int i = 0; i < files.length; i++) {
      try {
        File file = files[i];
        String fileName = file.getName();
        String chineseWord = fileName.split("\\.")[0];
        if (chineseWord.contains("_")) {
          chineseWord = chineseWord.split("_")[0];
        }
        ziMap.put(chineseWord, i);
        izMap.put(i, chineseWord);

        int[][] image = Binary.deal(file);
        DataItem dataItem = new DataItem();
        dataItem.setLabel(chs.indexOf(chineseWord));
        dataItem.setImage(image);
        dataItem.setWidth(32);
        dataItem.setHeight(32);
        dataItemList.add(dataItem);
      } catch (EOFException e) {
        break;
      }
    }
  }

  public List<DataItem> getDataItemList() {
    return dataItemList;
  }

  public void setDataItemList(List<DataItem> dataItemList) {
    this.dataItemList = dataItemList;
  }


  public Map<String, Integer> getZiMap() {
    return ziMap;
  }

  public void setZiMap(Map<String, Integer> ziMap) {
    this.ziMap = ziMap;
  }

  public Map<Integer, String> getIzMap() {
    return izMap;
  }

  public void setIzMap(Map<Integer, String> izMap) {
    this.izMap = izMap;
  }
}
