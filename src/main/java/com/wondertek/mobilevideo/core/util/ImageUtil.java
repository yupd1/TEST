package com.wondertek.mobilevideo.core.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class ImageUtil {

	/**
	 * 将原图片sourceImageFile 按照encoder格式转化为despImage目标图片
	 * 
	 * @param sourceImage
	 * @param despImage
	 * @param encoder
	 */
	public static void changeImgeEncode(File sourceImage, File despImage,
			String encoder)throws Exception {

		BufferedImage input = null;
			input = ImageIO.read(sourceImage);
			ImageIO.write(input, encoder, despImage);
			input.flush();
	}

	/**
	 * 注：2010.02.26 duguoc将该方法由private改为public 比例缩放图片
	 * 
	 * @param sourcePath
	 * @param despPath
	 * @param height
	 * @param width
	 * @throws Exception
	 */
	private static void chageImageSize(String sourcePath, String despPath,
			String imgType, int height, int width) throws Exception {
		sourcePath = CmsUtil.replaceSeparator(sourcePath);
		double Ratio = 0.0;

		File sourceFile = new File(sourcePath);
		File despFile = new File(despPath);

		if (!despFile.isFile())
			throw new Exception(despFile
					+ " is not image file error in getFixedBoundIcon!====");

		BufferedImage Bi = ImageIO.read(sourceFile);
		if ((Bi.getHeight() > height) || (Bi.getWidth() > width)) {
			if (Bi.getHeight() > height) {
				Ratio = (new Integer(height)).doubleValue() / Bi.getHeight();
			} else {
				Ratio = (new Integer(width)).doubleValue() / Bi.getWidth();
			}

			File ThF = new File(despPath);
			Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);
			AffineTransformOp op = new AffineTransformOp(AffineTransform
					.getScaleInstance(Ratio, Ratio), null);
			Itemp = op.filter(Bi, null);
			try {
				ImageIO.write((BufferedImage) Itemp, imgType, ThF);

			} catch (Exception ex) {

			}
		}
		return;
	}

	/**
	 * 对图片裁剪，并把裁剪完蛋新图片保存 。
	 */

	public static void operateImage(int x, int y, int width, int height,
			String srcpath, String despPath, String imgType) {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			is = new FileInputStream(srcpath);

			// 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader
			// 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
			// （例如 "jpeg" 或 "tiff"）等 。
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(imgType);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, imgType, new File(despPath));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (iis != null)
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 注：2010.02.26 duguoc将该方法由private改为public 比例缩放图片
	 * 
	 * @param sourcePath
	 * @param outPutFolder
	 *            目标文件夹
	 * @param despPath
	 *            目标路径
	 * @param height
	 * @param width
	 * @throws Exception
	 */
	public static void chageImageSize(String sourcePath,
			String outputFolderPath, String despPath, String imgType,
			int height, int width) throws Exception {
		sourcePath = CmsUtil.replaceSeparator(sourcePath);
		double Ratio = 0.0;

		File sourceFile = new File(sourcePath);

		BufferedImage Bi = ImageIO.read(sourceFile);
		if ((Bi.getHeight() > height) || (Bi.getWidth() > width)) {
			if (Bi.getHeight() > height) {
				Ratio = (new Integer(height)).doubleValue() / Bi.getHeight();
			} else {
				Ratio = (new Integer(width)).doubleValue() / Bi.getWidth();
			}

			File ThF = new File(despPath);
			Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);
			AffineTransformOp op = new AffineTransformOp(AffineTransform
					.getScaleInstance(Ratio, Ratio), null);
			Itemp = op.filter(Bi, null);
			try {
				FileUtil.checkDirExists(outputFolderPath);
				ImageIO.write((BufferedImage) Itemp, imgType, ThF);
			} catch (Exception ex) {

			}
		}
		return;
	}

	/**
	 * 注：2010.02.26 duguoc将该方法由private改为public 比例缩放图片 如果原图片的高比目标图片的高要大，则以高来缩小
	 * 如果原图片的高比目标图片的高要小，则以宽来放大
	 * 
	 * @param sourcePath
	 * @param outPutFolder
	 *            目标文件夹
	 * @param despPath
	 *            目标路径
	 * @param height
	 * @param width
	 * @throws Exception
	 *             0:默认情况返回 1：成功 2：太小了 3：转换失败
	 */
	public static boolean changeImageSize(String sourcePath,
			String outputFolderPath, String despPath, String imgType,
			int height, int width) throws Exception {
		sourcePath = CmsUtil.replaceSeparator(sourcePath);
		double Ratio = 0.0;

		File sourceFile = new File(sourcePath);

		BufferedImage Bi = ImageIO.read(sourceFile);
		if (Bi.getHeight() > height) {
			Ratio = (new Integer(height)).doubleValue() / Bi.getHeight();
		} else {
			Ratio = (new Integer(width)).doubleValue() / Bi.getWidth();
		}

		File ThF = new File(despPath);
		Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_REPLICATE  );
		AffineTransformOp op = new AffineTransformOp(AffineTransform
				.getScaleInstance(Ratio, Ratio), null);
		Itemp = op.filter(Bi, null);
		try {
			FileUtil.checkDirExists(outputFolderPath);
			boolean isSuc = ImageIO.write((BufferedImage) Itemp, imgType, ThF);
			return isSuc;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static BufferedImage gray(BufferedImage bi) {
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		bi = op.filter(bi, null);
		return bi;
	}
}
