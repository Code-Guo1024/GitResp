package com.guo.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;



import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;




import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.util.HashMap;



public class qrCodeUtil {
    private static final String CHARSET = "utf-8";
    //二维码图片格式
    public static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 800;
    // LOGO宽度
    private static final int LOGO_WIDTH = 200;
    // LOGO高度
    private static final int LOGO_HEIGHT =220;

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     */
    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        HashMap map = new HashMap();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//纠错等级
        map.put(EncodeHintType.CHARACTER_SET, CHARSET);//字符编码
        map.put(EncodeHintType.MARGIN, 1);//边框距离

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                map);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        qrCodeUtil.insertLogo(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     *  source       二维码图片
     * logoPath     LOGO图片地址
     *  needCompress 是否压缩
     */
    private static void insertLogo(BufferedImage source, String logoPath, boolean needCompress) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = qrCodeUtil.getResourceAsStream(logoPath);
            Image src = ImageIO.read(inputStream);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            if (needCompress) { // 压缩LOGO
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     */
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = qrCodeUtil.createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 获取指定文件的输入流，获取logo
     * @param logoPath 文件的路径
     * @return
     */
    public static InputStream getResourceAsStream(String logoPath) {
        return qrCodeUtil.class.getResourceAsStream(logoPath);
    }

}
