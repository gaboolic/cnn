package tk.gbl.cnn.core;

import com.alibaba.fastjson.JSON;
import tk.gbl.cnn.util.Output;
import tk.gbl.cnn.util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2016/6/3
 * Time: 11:15
 *
 * @author Tian.Dong
 */
public class Cnn implements Serializable {
  List<Layer> layerList;
  Layer outputLayer;

  public Cnn addLayer(Layer layer) {
    if (layerList == null) {
      layerList = new ArrayList<>();
    }
    layerList.add(layer);
    return this;
  }

  public Cnn addOutputLayer(Layer layer) {
    this.outputLayer = layer;
    if (layerList == null) {
      layerList = new ArrayList<>();
    }
    layerList.add(layer);
    return this;
  }


  public void init() {
    for (int i = 1; i < layerList.size(); i++) {
      Layer layer = layerList.get(i);
      if (layer instanceof ConvolutionLayer) {
        Layer lastLayer = layerList.get(i - 1);
        ((ConvolutionLayer) layer).initKernel(lastLayer);
      } else if (layer instanceof OutputLayer) {
        Layer lastLayer = layerList.get(i - 1);
        ((OutputLayer) layer).initKernel(lastLayer);
      } else if (layer instanceof DenseLayer) {
        Layer lastLayer = layerList.get(i - 1);
        ((DenseLayer) layer).initKernel(lastLayer);
      }
    }
  }

  public int predict(DataItem dataItem) {
    forward(dataItem);
    int output = Util.getMaxIndex(outputLayer.getOutputs());
    return output;
  }

  public boolean train(DataItem dataItem) {
    forward(dataItem);
    boolean result = backPropagation(dataItem);
    updateParam();
    return result;
  }

  private void updateParam() {
    //更新权值
    for (int i = 1; i < layerList.size(); i++) {
      Layer layer = layerList.get(i);
      Layer lastLayer = layerList.get(i - 1);
      layer.updateParam(lastLayer.getOutputs());
    }
  }


  private double[][][] forward(DataItem dataItem) {
    double[][][] images = new double[1][dataItem.getImage().length][dataItem.getImage().length];
    images[0] = dataItem.getImage();
    for (Layer layer : layerList) {
      images = layer.doLayer(images);
    }
    return images;
  }

  /**
   * 反向传播
   */
  private boolean backPropagation(DataItem dataItem) {
    boolean result = setOutLayerErrors(dataItem);
    setHiddenLayerErrors();
//    for (int i = 1;i<layerList.size();i++) {
//      Layer layer = layerList.get(i);
//      Output.output(i+"",layer.getErrors());
//    }
    return result;
  }

  private void setHiddenLayerErrors() {
    double[][][] errors = outputLayer.getErrors();
    for (int i = layerList.size() - 2; i > 0; i--) {
      Layer layer = layerList.get(i);
      Layer nextLayer = layerList.get(i + 1);
      errors = layer.doError(errors, nextLayer);
    }
  }


  private boolean setOutLayerErrors(DataItem dataItem) {
    int mapNum = outputLayer.getMapNum();
    double[][][] target = new double[mapNum][1][1];
    double[][][] outputs = outputLayer.getOutputs();
    double[][][] errors = new double[mapNum][1][1];

    int label = dataItem.getLabel();
    target[label][0][0] = 1;
    for (int m = 0; m < mapNum; m++) {
      errors[m][0][0] = outputs[m][0][0] * (1 - outputs[m][0][0]) * (target[m][0][0] - outputs[m][0][0]);
//      errors[m][0][0] = (target[m][0][0] - outputs[m][0][0]);
    }
    outputLayer.setErrors(errors);
    return label == Util.getMaxIndex(outputs);
  }

  public Layer getOutputLayer() {
    return this.outputLayer;
  }

  public List<Layer> getLayerList() {
    return layerList;
  }

  public void setLayerList(List<Layer> layerList) {
    this.layerList = layerList;
  }

  public void setOutputLayer(Layer outputLayer) {
    this.outputLayer = outputLayer;
  }
}
