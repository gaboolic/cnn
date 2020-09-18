package tk.gbl.cnn;

import org.junit.Test;
import tk.gbl.cnn.MnistDbFile;
import tk.gbl.cnn.core.*;
import tk.gbl.cnn.util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Date: 2016/6/6
 * Time: 15:47
 *
 * @author Tian.Dong
 */
public class TestMakeCnn {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Cnn cnn = new Cnn();
    //输入层
    cnn.addLayer(new InputLayer(1))
        //C1 5*5卷积核 步长为1 数量为6
        .addLayer(new ConvolutionLayer("C1", 6, new KernelBuilder(5, 5, 1)))
            //S2 采样 输出12*12
        .addLayer(new SamplingLayer("S2", 6, new Sampling(2, 2)))
            //C3 5*5卷积核 输出8*8
        .addLayer(new ConvolutionLayer("C3", 12, new KernelBuilder(5, 5, 1)))
            //S4 采样 输出4*4
        .addLayer(new SamplingLayer("S4", 12, new Sampling(2, 2)))
            //5 输出
        .addOutputLayer(new ConvolutionLayer("Output", 10, new KernelBuilder(4, 4, 1)));//4 输出层

//    ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:\\DT\\cnn_json\\cnn.obj"));
//    Cnn cnn = (Cnn) in.readObject();
//    in.close();

    MnistDbFile trainFile = new MnistDbFile("C:\\Users\\tian\\MNIST", "images-idx3-ubyte", "labels-idx1-ubyte");
    for (int i = 0; i < 100; i++) {
      DataItem dataItem = trainFile.getDataItem();
      cnn.train(dataItem);
      int output = Util.getMaxIndex(cnn.getOutputLayer().getOutputs());
      int realResult = dataItem.getLabel();
      System.out.println("real:" + realResult + ",output:" + output);
    }

    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:\\DT\\cnn_json\\cnn.obj"));
    out.writeObject(cnn);
    out.close();
  }
}
