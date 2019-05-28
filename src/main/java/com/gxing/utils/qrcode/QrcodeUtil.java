package com.gxing.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class QrcodeUtil {
	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	private static final int QRCODE_WIDTH = 600;
	private static final int QRCODE_HEIGHT = 800;
	private static final int LOGO_WIDTH = 100;
	private static final int LOGO_HEIGHT = 100;
	private static final int TEXT_IMG_WIDTH = 600;
	private static final int TEXT_IMG_HEIGHT = 50;

	/**
	 * 创建 二维码所需图片
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            Logo图片地址
	 * @param needCompress
	 *            是否压缩Logo大小
	 * @param needDescription
	 *            是否需要底部描述
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	private static BufferedImage createImage(String content, BufferedImage logoImage, String bottomDes,
			boolean needCompress) throws Exception {
		Hashtable hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 容错级别
		// H->30%
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH,
				QRCODE_HEIGHT, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		int tempHeight = height;
		boolean needDescription = (null == bottomDes && "".equals(bottomDes));
		if (needDescription) {
			tempHeight += 30;
		}
		BufferedImage image = new BufferedImage(width, tempHeight, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (null == logoImage)
			return image;
		// 插入图片
		QrcodeUtil.insertImage(image, logoImage, needCompress);

		if (needDescription)
			return image;

		QrcodeUtil.addFontImage(image, bottomDes);

		return image;
	}

	/**
	 * 添加 底部图片文字
	 * 
	 * @param source
	 *            图片源
	 * @param declareText
	 *            文字本文
	 */
	private static void addFontImage(BufferedImage source, String declareText) {
		BufferedImage textImage = createImage(declareText, new Font("微软雅黑", Font.PLAIN, 15), TEXT_IMG_WIDTH,
				TEXT_IMG_HEIGHT);
		BufferedImage tip = createImage("微信/支付宝扫一扫使用小程序", new Font("微软雅黑", Font.BOLD, 35), TEXT_IMG_WIDTH,
				TEXT_IMG_HEIGHT);
		Graphics2D graph = source.createGraphics();
		int width = textImage.getWidth(null);
		int height = textImage.getHeight(null);

		Image src = textImage;
		graph.drawImage(src, 0, QRCODE_HEIGHT - 50, width, height, null);
		graph.drawImage(tip, 0, QRCODE_HEIGHT - 130, tip.getWidth(), tip.getHeight(), null);
		graph.dispose();
	}

	/**
	 * 插入Logo图片
	 * 
	 * @param source
	 *            图片操作对象
	 * @param logoImage
	 *            Logo图片地址
	 * @param needCompress
	 *            是否压缩Logo大小
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, BufferedImage logoImage, boolean needCompress)
			throws Exception {
		int width = logoImage.getWidth(null);
		int height = logoImage.getHeight(null);
		Image src = logoImage;
		if (needCompress) {
			if (width > LOGO_WIDTH) {
				width = LOGO_WIDTH;
			}
			if (height > LOGO_HEIGHT) {
				height = LOGO_HEIGHT;
			}

			Image image = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			src = image;
		}
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_WIDTH - width) / 2;
		int y = (QRCODE_HEIGHT - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 100, 100);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 创建 目录
	 * 
	 * @param destPath
	 */
	public static void mkdirs(String destPath) {
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	/**
	 * 
	 * @param content
	 *            内容
	 * @param logoImage
	 *            图片源LOGO
	 * @param bottomDes
	 *            底部描述文字
	 * @param picName
	 *            图片名
	 * @param destPath
	 *            保存二维码 地址 (没有该目录会自动创建)
	 * @param needCompress
	 *            是否压缩logo
	 * @throws Exception
	 */
	public static String encode(String content, BufferedImage logoImage, String bottomDes, String picName,
			String destPath, boolean needCompress) throws Exception {
		BufferedImage image = QrcodeUtil.createImage(content, logoImage, bottomDes, needCompress);

		mkdirs(destPath);
		String file = picName + ".jpg";
		String pathname = destPath + File.separator + file;
		boolean flag = ImageIO.write(image, FORMAT_NAME, new File(pathname));
		if (flag) {
			return pathname;
		}
		return null;
	}

	public static BufferedImage createImage(String content, Font font, Integer width, Integer height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);
		g2.setPaint(Color.BLACK);
		g2.setFont(font);
		
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(content, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;

		g2.drawString(content, (int) x, (int) baseY);
		return bi;
	}
	
}
//976343B2333A13CE45648BDD6794F52B
//C169939A2DAB03BBEC476D4286D02B41
