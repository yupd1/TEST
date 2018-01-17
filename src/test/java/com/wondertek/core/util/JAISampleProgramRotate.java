package com.wondertek.core.util;

import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.Interpolation;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.FileSeekableStream;

/**
 * This program decodes an image file of any JAI supported formats, such as GIF,
 * JPEG, TIFF, BMP, PNM, PNG, into a RenderedImage, scales the image by 2X with
 * bilinear interpolation, and then displays the result of the scale operation.
 */
public class JAISampleProgramRotate {

	/** The main method. */
	public static void main(String[] args) {
		/* Validate input. */

		/*
		 * Create an input stream from the specified file name to be used with
		 * the file decoding operator.
		 */
		FileSeekableStream stream = null;
		try {
			stream = new FileSeekableStream("D:\\workspace\\mobilevideo\\mobilevideo\\core\\core-util\\src\\test\\java\\123456_130_ud_QVGA.png");
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

		int value = 90;
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
			ImageIO.write(im.getAsBufferedImage(), "PNG", new File("d:\\123111.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// -----------------------------------add the other operator sample code
		// here-------------------------
//
//		/* Get the width and height of image2. */
//		int width = im.getWidth();
//		int height = im.getHeight();
//
//		/* Attach image2 to a scrolling panel to be displayed. */
//		ScrollingImagePanel panel = new ScrollingImagePanel(im, width, height);
//
//		/* Create a frame to contain the panel. */
//		Frame window = new Frame("JAI   Sample   Program ");
//		window.add(panel);
//		window.pack();
//		window.show();
	}
}