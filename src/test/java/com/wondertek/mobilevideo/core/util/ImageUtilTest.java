package com.wondertek.mobilevideo.core.util;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.junit.Test;

public class ImageUtilTest {

	/**
	 * @param srcURL
	 *            原图地址
	 * @param destURL
	 *            缩略图地址
	 * @param extractBase
	 *            压缩基数
	 * @param scale
	 *            压缩限制(宽/高)比例
	 * @throws Exception
	 */
	
	/*
	public void extractPhoto(String srcURL, String destURL, double extractBase,
			double scale) throws Exception {

		File srcFile = new File(srcURL);
		Image src = ImageIO.read(srcFile);
		int srcHeight = src.getHeight(null);
		int srcWidth = src.getWidth(null);
		int deskHeight = 0;
		int deskWidth = 0;
		double srcScale = (double) srcHeight / srcWidth;
		if ((double) srcHeight > extractBase || (double) srcWidth > extractBase) {
			if (srcScale >= scale || 1 / srcScale > scale) {
				if (srcScale >= scale) {
					deskHeight = (int) extractBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) extractBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			} else {
				if ((double) srcHeight > extractBase) {
					deskHeight = (int) extractBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) extractBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			}
		} else {
			deskHeight = srcHeight;
			deskWidth = srcWidth;

		}
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight,
				BufferedImage.TYPE_3BYTE_BGR);
		tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);
		FileOutputStream deskImage = new FileOutputStream(destURL); // 输出到文件流
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		encoder.encode(tag);
		deskImage.close();
	}
*/
	/**
	 * 给图片添加水印
	 * 
	 * @param filePath
	 *            需要添加水印的图片的路径
	 * @param markContent
	 *            水印的文字
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param qualNum
	 *            图片质量
	 * @return
	 */
	/*
	public boolean createMark(String filePath, String markContent,
			Color markContentColor, float qualNum) {
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(markContentColor);
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		g.drawString(markContent, width / 5, height / 5); // 添加文字
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(qualNum, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
*/



    static IndexColorModel createIndexColorModel() {  
        BufferedImage ex = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);  
        IndexColorModel icm = (IndexColorModel) ex.getColorModel();  
        int SIZE = 256;  
        byte[] r = new byte[SIZE];  
        byte[] g = new byte[SIZE];  
        byte[] b = new byte[SIZE];  
        byte[] a = new byte[SIZE];  
        icm.getReds(r);  
        icm.getGreens(g);  
        icm.getBlues(b);  
        java.util.Arrays.fill(a, (byte)255);  
        r[0] = g[0] = b[0] = a[0] = 0; //transparent  
        return  new IndexColorModel(8, SIZE, r, g, b, a);  
    } 
    
    
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();

		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}

//		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
		
		ColorModel cm = null;
	       cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] { 8, 8, 8 },
            /* alpha */ false,
            /* premultipliedAlpha */ false,
            Transparency.OPAQUE,
            DataBuffer.TYPE_BYTE);
	        
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
//		} else
//			target = new BufferedImage(targetW, targetH, type);
		
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
	

	@Test
	public void testChangeImgeEncode() throws IOException {

		String path = this.getClass().getResource("/image").getPath();
		File sourceFile = new File(path + "/a.jpg");
		File despFile1 = new File(path + "/a1.png");
		try {
			ImageUtil.changeImgeEncode(sourceFile, despFile1, "png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ImageUtil.changeImgeEncode256(sourceFile, despFile2, "png");
//		
//		File sourceFileb = new File(path + "/b.jpg");
//		File despFileb1 = new File(path + "/b1.png");
//		File despFileb2 = new File(path + "/b2.png");
//		ImageUtil.changeImgeEncode(sourceFileb, despFileb1, "png");
//		ImageUtil.changeImgeEncode256(sourceFileb, despFileb2, "png");
		


		
//		BufferedImage input = null;
//		try {
//			
//			BufferedImage stand = ImageIO.read(sourceFile);
//			
//			
//			input = ImageIO.read(sourceFileb);
//			ColorModel cm = stand.getColorModel();
//			
//			WritableRaster raster = cm.createCompatibleWritableRaster(input.getWidth(),
//					input.getHeight());
//			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
//			BufferedImage output = new BufferedImage(cm, raster, alphaPremultiplied, null);
//			Graphics2D g = output.createGraphics();
//			// smoother than exlax:
//			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//
//			g.drawRenderedImage(input, AffineTransform.getScaleInstance(1, 1));
//			g.dispose();
//			
//			ImageIO.write(output, "png", despFileb2);
//			input.flush();
//			
//		} catch (IOException e) {
//		}


	}

	@Test
	public void testOperateImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testChageImageSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeImageSize() {
		fail("Not yet implemented");
	}

}
