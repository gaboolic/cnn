package tk.gbl.cnn.util.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Date: 2314/9/25
 * Time: 15:56
 *
 * @author Tian.Dong
 */
public class CreateCode {
  int width = 60;
  int height = 30;

  public BufferedImage create(String s) {
    BufferedImage image = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    Graphics g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    //g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

    BufferedImage sbi = image.getSubimage(0, 0, 30, 30);
    Graphics2D gsbi = (Graphics2D) g.create(0, 0, 15, 23);
    gsbi.setColor(Color.BLACK);
    gsbi.setFont(new Font("Arial Black", Font.BOLD, 23));
    gsbi.rotate(-0.5, 5, 10);
    gsbi.drawString(s, 1, 23);

    g.drawImage(sbi, 1, 1, 1, 1, null);

    g.dispose();
    return image;
  }
  public BufferedImage create(String s,double theta,int fontSize) {
    BufferedImage image = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_RGB);
    Graphics g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    BufferedImage sbi = image.getSubimage(0, 0, 30, 30);
    Graphics2D gsbi = (Graphics2D) g.create(0, 0, 15, 23);
    gsbi.setColor(Color.BLACK);
    gsbi.setFont(new Font("Arial", Font.PLAIN, fontSize));
    gsbi.rotate(theta, 5, 10);
    gsbi.drawString(s, 1, 23);

    g.drawImage(sbi, 1, 1, 1, 1, null);

    g.dispose();
    return image;
  }

  public static void main(String[] args) throws IOException {
    CreateCode cc = new CreateCode();
    for(int i=0;i<=9;i++){
      BufferedImage image = cc.create(i+"",0.1,18);
      File file = new File("F:/codeT/"+"R"+i+".png");
      ImageIO.write(image, "PNG", file);
    }
    for(int i=0;i<=9;i++){
      BufferedImage image = cc.create(i+"",-0.1,18);
      File file = new File("F:/codeT/"+"L"+i+".png");
      ImageIO.write(image, "PNG", file);
    }
  }

  public static void main2(String[] args) throws IOException {
    CreateCode cc = new CreateCode();
    for(int i=2;i<=9;i++){
      BufferedImage image = cc.create(i+"",-0.4,23);
      File file = new File("F:/codeT/"+"b"+i+"L.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='a';a<='z';a++) {
      BufferedImage image = cc.create(a+"",-0.4,23);
      File file = new File("F:/codeT/"+"b"+"_"+a+"L.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='A';a<='Z';a++) {
      BufferedImage image = cc.create(a+"",-0.4,23);
      File file = new File("F:/codeT/"+"b"+a+"L.png");
      ImageIO.write(image, "PNG", file);
    }
//
    for(int i=2;i<=9;i++){
      BufferedImage image = cc.create(i+"",0.4,23);
      File file = new File("F:/codeT/"+"b"+i+"R.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='a';a<='z';a++) {
      BufferedImage image = cc.create(a+"",0.4,23);
      File file = new File("F:/codeT/"+"b"+"_"+a+"R.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='A';a<='Z';a++) {
      BufferedImage image = cc.create(a+"",0.4,23);
      File file = new File("F:/codeT/"+"b"+a+"R.png");
      ImageIO.write(image, "PNG", file);
    }
    ///////////


    for(int i=2;i<=9;i++){
      BufferedImage image = cc.create(i+"",-0.4,23);
      File file = new File("F:/codeT/"+"l"+i+"L.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='a';a<='z';a++) {
      BufferedImage image = cc.create(a+"",-0.4,23);
      File file = new File("F:/codeT/"+"l"+"_"+a+"L.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='A';a<='Z';a++) {
      BufferedImage image = cc.create(a+"",-0.4,23);
      File file = new File("F:/codeT/"+"l"+a+"L.png");
      ImageIO.write(image, "PNG", file);
    }
//
    for(int i=2;i<=9;i++){
      BufferedImage image = cc.create(i+"",0.4,23);
      File file = new File("F:/codeT/"+"l"+i+"R.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='a';a<='z';a++) {
      BufferedImage image = cc.create(a+"",0.4,23);
      File file = new File("F:/codeT/"+"l"+"_"+a+"R.png");
      ImageIO.write(image, "PNG", file);
    }
    for(char a='A';a<='Z';a++) {
      BufferedImage image = cc.create(a+"",0.4,23);
      File file = new File("F:/codeT/"+"l"+a+"R.png");
      ImageIO.write(image, "PNG", file);
    }



  }




}
