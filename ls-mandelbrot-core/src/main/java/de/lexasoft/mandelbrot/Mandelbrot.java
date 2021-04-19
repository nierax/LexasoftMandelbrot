/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Point;

/**
 * This class generates an image of the Mandelbrot set
 * <p>
 * 
 * @author nierax
 */
public class Mandelbrot {

	private MandelbrotColorize colorize;

	/**
	 * Do not create other than with of() method.
	 */
	private Mandelbrot() {
		super();
	}

	/**
	 * Based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
	 * 
	 * @param topLeft     The top left point of the calculation limit.
	 * @param bottomRight The bottom right point of the calculation limit.
	 * @param maxIt       The maximum number of iterations, before the point is
	 *                    considered to be in the Mandelbrot set.
	 * @param imageWidth  The width of the image in pixel.
	 * @param imageHeight The height of the image in pixel.
	 */
	public MandelbrotImage drawMandelbrot(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight, int maxIt,
	    int imageWidth, int imageHeight) {
		double xstart = topLeft.cx();
		double xend = bottomRight.cx();
		double ystart = topLeft.cy();
		double yend = bottomRight.cy();

		// Steps for calculations are orientated on the width / height of the image
		double dx = (xend - xstart) / (imageWidth - 1);
		double dy = (yend - ystart) / (imageHeight - 1);

		MandelbrotPoint point = new MandelbrotPoint();
		MandelbrotImage image = new MandelbrotImage(imageWidth, imageHeight);

		// Start position
		MandelbrotPointPosition cpos = MandelbrotPointPosition.of(xstart, yend);
		long time = System.currentTimeMillis();
		for (int column = 0; column < imageWidth; column++) {
			cpos.cy(yend);
			for (int line = 0; line < imageHeight; line++) {
				int iterate = point.iterate(cpos.cx(), cpos.cy(), maxIt);
				Point iPoint = new Point();
				iPoint.x = column;
				iPoint.y = line;
				image.colorizePoint(iPoint, colorize.getColorForIteration(iterate, maxIt));
				cpos.movey(-dy);
			}
			cpos.movex(dx);
		}
		System.out.println("Time needed " + (System.currentTimeMillis() - time) + " ms");
		return image;
	}

	/**
	 * Create Mandelbrot object with the given colorize strategy.
	 * 
	 * @param colorize The colorize strategy to use.
	 * @return Newly created Mandelbrot object.
	 */
	public final static Mandelbrot of(MandelbrotColorize colorize) {
		Mandelbrot mb = new Mandelbrot();
		mb.colorize = colorize;
		return mb;
	}

	/**
	 * Create Mandelbrot object with a standard colorize strategy (black and white
	 * only).
	 * 
	 * @return Newly created Mandelbrot object.
	 */
	public final static Mandelbrot of() {
		return of(new MandelbrotBlackWhite());
	}
}
