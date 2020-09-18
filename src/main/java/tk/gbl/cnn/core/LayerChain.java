package tk.gbl.cnn.core;

import java.io.Serializable;

/**
 * Date: 2016/6/3
 * Time: 17:12
 *
 * @author Tian.Dong
 */
public class LayerChain implements Serializable {
  private Layer layer;

  private LayerChain nextLayerChain;

  public LayerChain(Layer layer) {
    this.layer = layer;
  }

  public LayerChain addLayer(Layer layer) {
    this.nextLayerChain = new LayerChain(layer);
    return this.nextLayerChain;
  }


  public Layer getLastLayer() {
    LayerChain layerChain = nextLayerChain;
    while (layerChain != null) {
      if(layerChain.nextLayerChain==null) {
        return layerChain.layer;
      }
      layerChain = layerChain.nextLayerChain;
    }
    return null;
  }

  public Layer getLayer() {
    return layer;
  }

  public void setLayer(Layer layer) {
    this.layer = layer;
  }

  public LayerChain getNextLayerChain() {
    return nextLayerChain;
  }

  public void setNextLayerChain(LayerChain nextLayerChain) {
    this.nextLayerChain = nextLayerChain;
  }
}
