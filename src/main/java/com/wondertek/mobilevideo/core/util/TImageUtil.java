package com.wondertek.mobilevideo.core.util;

import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.Interpolation;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.FileSeekableStream;

public class TImageUtil {
	
	public static  void rotateImage(String src,String dest, String imageType, int rotateAngle){
		FileSeekableStream stream = null;
		try {
			stream = new FileSeekableStream(src);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		/* Create an operator to decode the image file. */
		RenderedOp image = JAI.create("stream", stream);
		/*
		 * Create a standard bilinear interpolation object to be used with the
		 * "scale " operator.
		 */
		Interpolation interp = Interpolation
				.getInstance(Interpolation.INTERP_BILINEAR);

		int value = rotateAngle;
		float angle = (float) (value * (Math.PI / 180.0F));

		// Create a ParameterBlock and specify the source and
		// parameters
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image); // The source image//******************replace im
								// with image****************
		pb.add(0.0F); // The x origin
		pb.add(0.0F); // The y origin
		pb.add(angle); // The rotation angle
		pb.add(new InterpolationNearest()); // The interpolation

		// Create the scale operation
		RenderedOp im = JAI.create("Rotate", pb, null);// **************************************************create
														// an instance im of
														// type
														// RenderedOp********
		
		try {
			ImageIO.write(im.getAsBufferedImage(), imageType, new File(dest));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
