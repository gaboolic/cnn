package tk.gbl.cnn;

import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.CoderUtil;
import tk.gbl.cnn.util.ImageUtil;
import tk.gbl.cnn.util.MatrixUtil;
import tk.gbl.cnn.util.Util;
import tk.gbl.cnn.util.image.ArrayToImage;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;

/**
 * 45217
 * 55289
 * <p/>
 * Date: 2016/6/16
 * Time: 17:00
 *
 * @author Tian.Dong
 */
public class ChineseTrainTest {
  static String chs = "T七丝中丸丹乐乒乓书云亭亮人仁仔他仙仪件企保像光克兔全公典冰凉几刀创刷刺剪力办加动勋包匙千南卜卫印卷叉古可台叶吉向听吸味咖品哑啡啤喷器囊围国图圆圈土圣地块坪垫堡塑塔墨壳壶大天太头夹套奶姜娃子字安小尘尺山巧巾币布帐带帽床开弹彩心恐恤戒戳户扇手打扣扫扳把护报披拉拍拖拨拼指挖挞掌排掘提摩擦收救文斑斗料方日明星春月服木本机杏条杯板枕果枣架柚柜柴柿标树核栽桃案桌档桥梅梯梳棋椅椒椰楂楼榔榨榴槟樟樱橙母毛民气水汁汉池汤沙油泉泳洋洗流浏浪海消液淇淋温游湿滩漏漠瀑火灯炭炮烛烟烤热煤熊熨爆片牌牙牛物犀狐狗狮狸猕猫猬猴玉玻珍珠球琴璃瓜瓢瓶生电白皂皮盆盒盖盘相眼石矿砂硬磁示福秋秤空窗窝章竹笋笔笼筒筝算管箭箱篮篷簧米类粉粽糕糖紫红纳纸纽线绒结绳绿羽翠翡老联肥胎胶脑腕膏自舞舟航艇色芒芦花芽苍英茶草药荷莓菜菠萝萨葫葱葵蒙蒲蒸薯藕虎虫虹虾蚁蚂蚊蛋蛙蜂蜓蜗蜜蜡蜻蝇蝴蝶螃螺蟹行衣表袋被装裙裤西视览警诊话诞调豆豚象豹贝购贴赛路跳躺车轨轮轿辣运透邮酒酱钥钻铁铃铛银链锅锣键锹镜长门闪防阳雕露青面靴鞋鞭音顶领颈风飞餐饨饭饼馄馆馒香马驼骆魔鱼鲨鲸鳄鸟鸡鸥鸭鸽鹅鹤鹰鹿麦麻黄黑鼓鼠齿龄龙";
//  static String serializeFileName = "E:\\DT\\cnn_json\\chinese_cnn.obj";
//  static String inputFilePathName = "E:\\DT\\cnn\\chinese";

//  static String serializeFileName = "/home/work/cnn_json/chinese_cnn.obj";
//  static String inputFilePathName = "/home/work/cnn/chinese";

  static String serializeFileName = "D:\\cnn_json\\chinese_cnn.obj";
  static String inputFilePathName = "D:\\cnn\\chinese";

  public static void main(String[] args) throws IOException, ClassNotFoundException {
//    Cnn cnn = new Cnn();
//    cnn.addLayer(new InputLayer(1))    //输入层 32
//        //C1 30
//        .addLayer(new ConvolutionLayer("C1", 100, new KernelBuilder(3, 3, 1)))
//            //S2 输出15
//        .addLayer(new SamplingLayer("S2", 100, new Sampling(2, 2)))
//            //C3 5*5卷积核 14
//        .addLayer(new ConvolutionLayer("C3", 200, new KernelBuilder(2, 2, 1)))
//            //S4 采样 输出7
//        .addLayer(new SamplingLayer("S4", 200, new Sampling(2, 2)))
//            //C3 2*2 卷积核 6
//        .addLayer(new ConvolutionLayer("C5", 400, new KernelBuilder(2, 2, 1)))
//            //S6 采样 输出3
//        .addLayer(new SamplingLayer("S6", 400, new Sampling(2, 2)))
//            //D7 全连接 输出1
//        .addLayer(new DenseLayer("D7", 500, new KernelBuilder(3, 3, 1)))
//            //Output 输出 1
//        .addOutputLayer(new OutputLayer("Output", 500, new KernelBuilder(1, 1, 1)));//26
//    cnn.init();

//    Cnn cnn = new Cnn();
//    cnn.addLayer(new InputLayer(1))
//        //C1 5*5卷积核 步长为1 数量为6 32
//        .addLayer(new ConvolutionLayer("C1", 25, new KernelBuilder(5, 5, 1)))
//            //S2 采样 输出14
//        .addLayer(new SamplingLayer("S2", 25, new Sampling(2, 2)))
//            //C3 5*5卷积核 10
//        .addLayer(new ConvolutionLayer("C3", 50, new KernelBuilder(5, 5, 1)))
//            //S4 采样 输出5
//        .addLayer(new SamplingLayer("S4", 50, new Sampling(2, 2)))
//            //D5 全连接 输出1
//        .addLayer(new DenseLayer("D5", 100, new KernelBuilder(5, 5, 1)))
//            //Output 输出 1
//        .addOutputLayer(new OutputLayer("Output", 500, new KernelBuilder(1, 1, 1)));//26
//    cnn.init();

    ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializeFileName));
    Cnn cnn = (Cnn) in.readObject();
    in.close();

    ImageDbFile imageDbFile = new ImageDbFile(inputFilePathName);
    System.out.println("size:" + imageDbFile.getDataItemList().size());
    for (int repeat = 0; repeat <= 300; repeat++) {
      int right = 0;
      int allCount = 0;
      for (int count = 0; count < 10000; count++) {
        //for (int i = 0; i < imageDbFile.getDataItemList().size(); i++) {
        int i = (int) (Math.random() * imageDbFile.getDataItemList().size());
        DataItem originDataItem = imageDbFile.getDataItemList().get(i);

        DataItem dataItem = new DataItem();
        dataItem.setLabel(originDataItem.getLabel());
        dataItem.setImage(MatrixUtil.shapeChange(originDataItem.getImage()));
        dataItem.setWidth(originDataItem.getWidth());
        dataItem.setHeight(originDataItem.getHeight());
        //ArrayToImage.createImage(MatrixUtil.double2int( dataItem.getImage()),"E:\\DT\\cnn\\test.png");
        cnn.train(dataItem);
        double[][][] outputs = cnn.getOutputLayer().getOutputs();
        int output = Util.getMaxIndex(outputs);
        int realResult = dataItem.getLabel();
        if (output == realResult) {
          right++;
        }
        allCount++;

        if (count % 1000 == 0) {
          System.out.println("第" + repeat + "次迭代," + right + "," + allCount + ",正确率:" + (1.0 * right / allCount));
        }
      }
      System.out.println("第" + repeat + "次迭代," + right + "," + allCount + ",正确率:" + (1.0 * right / allCount));
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializeFileName));
      out.writeObject(cnn);
      out.close();
    }

  }

  public static void main2(String[] args) throws IOException, ClassNotFoundException {
    Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1)) //32
        //C1 3*3 100 输出30*30
        .addLayer(new ConvolutionLayer("C1", 100, new KernelBuilder(3, 3, 1)))
            //S2 采样 输出15*15
        .addLayer(new SamplingLayer("S2", 100, new Sampling(2, 2)))
            //C3 2*2 输出14*14
        .addLayer(new ConvolutionLayer("C3", 200, new KernelBuilder(2, 2, 1)))
            //S4 采样 输出7*7
        .addLayer(new SamplingLayer("S4", 200, new Sampling(2, 2)))
            //C5 2*2 输出6*6
        .addLayer(new ConvolutionLayer("C5", 300, new KernelBuilder(2, 2, 1)))
            //S6 采样 输出3*3
        .addLayer(new SamplingLayer("S6", 300, new Sampling(2, 2)))
            //7 输出 1
        .addOutputLayer(new ConvolutionLayer("Output", 3755, new KernelBuilder(3, 3, 1)));
    cnn.init();

//    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\chinese_cnn.obj"));
//    Cnn cnn = (Cnn) in.readObject();
//    in.close();

    File path = new File("/home/work/cnn/gnt");
    for (int repeat = 0; repeat < 5; repeat++)
      for (File file : path.listFiles()) {
        HWDBGntFile hwdbGntFile = new HWDBGntFile(path.getPath(), file.getName());
//    System.out.println(hwdbGntFile.getDataItemList().get(0).getLabel());
//    System.out.println(hwdbGntFile.getDataItemList().get(hwdbGntFile.getDataItemList().size()-1).getLabel());
        Collections.sort(hwdbGntFile.getDataItemList(), new Comparator<DataItem>() {
          @Override
          public int compare(DataItem o1, DataItem o2) {
            return o1.getLabel() - o2.getLabel();
          }
        });

        System.out.println("size:" + hwdbGntFile.getDataItemList().size());

        int right = 0;
        int allCount = 0;
        for (int i = 0; i < hwdbGntFile.getDataItemList().size(); i++) {
          long startTime = System.currentTimeMillis();
          DataItem dataItem = hwdbGntFile.getDataItemList().get(i);
          dataItem.setImage(ImageUtil.standard32(dataItem.getMatrix()));
          //ImageUtil.writeImage(dataItem.getImage(),CoderUtil.gb2312ToUtf8(dataItem.getLabel())+".png");
          dataItem.setLabel(CoderUtil.gb2312Map.get(CoderUtil.gb2312ToUtf8(dataItem.getLabel())));

          cnn.train(dataItem);
          double[][][] outputs = cnn.getOutputLayer().getOutputs();
          int output = Util.getMaxIndex(outputs);
          int realResult = dataItem.getLabel();
          if (output == realResult) {
            right++;
          }
          if (i % 100 == 0) {
            System.out.print(".");
            System.out.println((System.currentTimeMillis() - startTime) + "ms,output:" + CoderUtil.gb2312Map2.get(output) + "," + "real:" + CoderUtil.gb2312Map2.get(realResult));
          }
          allCount++;
        }
        System.out.println();
        System.out.println("第" + repeat + "次迭代:" + file.getName() + "|" + right + "," + allCount + ",正确率:" + (1.0 * right / allCount));


        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/home/work/cnn/chinese_cnn.obj"));
        out.writeObject(cnn);
        out.close();
      }
  }
}
