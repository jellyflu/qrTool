package com.tingcream.qrTool.qrcode;

 
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类  google.zxing包
 * 
 * @author jelly
 * @date 2018年12月17日
 */
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "png";
    
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300; //即 300 * 300 像素
    
    
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;
    

    /**
     * 创建qrcode 二维码
     * @param content  原文内容
     * @param logoInput  可为null，若为null，则二维码中不会插入logo
     * @param needCompress  是否需要颜色
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public  static BufferedImage createQrImage(String content,InputStream logoInput,
            boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (logoInput==null) {//如果logoInput
            return image;
        }else {
        	 // 插入logo图片
            QRCodeUtil.insertLogo(image, logoInput, needCompress);
            return image;
        }
       
    }

     /**
      * 图形二维码中插入logo图形
      * @param source
      * @param logoPath
      * @param needCompress
      * @throws Exception
      */
    private static void insertLogo(BufferedImage source, InputStream logoInput,
            boolean needCompress) throws Exception {
    	
    	Image src =ImageIO.read(logoInput);
    	
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
 
 
    
    
 
      /**
       * 二维码编码    xx
       * @param text   原文内容
       * @param logoIn  logo图 ,可为null
       * @param output  二维码图形输出outputstream
       * @param needCompress  是否需要颜色
       * @throws Exception
       */
      public static void encode(String  text,InputStream logoIn,OutputStream output,boolean needCompress) throws Exception {
    	  BufferedImage image = createQrImage(text, logoIn, needCompress);
	      ImageIO.write(image, FORMAT_NAME, output);
    	  
      }
    
     

    /**
     * 二维码解码 xx
     * @param in  二维码图input流
     * @return 原文内容
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String decode(InputStream in) throws Exception {
    	
    	BufferedImage  image=ImageIO.read(in);
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
         
        Hashtable hints = new Hashtable();
         hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
          Result  result = new MultiFormatReader().decode(bitmap, hints);
         return  result.getText();
    }
   
   
//    public static void main(String[] args) throws Exception {
//    	
//    	 //二维码编码
////    	String text = "hello\n你好哈哈";
////    	OutputStream output=new FileOutputStream(new File("d:/qr.png"));
////    	QRCodeUtil.encode(text, null, output, true);
//    	
//    	//二维码解码
////    	InputStream in=new FileInputStream(new File("d:/qr.png"));
////    	 String result=QRCodeUtil.decode(in);
////    	 System.out.println(result);
//    	
//    }
   
}
