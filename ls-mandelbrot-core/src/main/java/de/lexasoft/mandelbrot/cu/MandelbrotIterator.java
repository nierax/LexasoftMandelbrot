/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import java.awt.Point;

import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * This class generates an image of the MandelbrotIterator set
 * <p>
 * 
 * @author nierax
 */
public class MandelbrotIterator {

	private final MandelbrotColorize colorize;

	/**
	 * Do not create other than with of() method.
	 */
	private MandelbrotIterator() {
		this(new MandelbrotBlackWhite());
	}

	private MandelbrotIterator(MandelbrotColorize colorize) {
		super();
		this.colorize = colorize;
	}

	/**
	 * Based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
	 * 
	 * @param topLeft     The top left point of the calculation limit.
	 * @param bottomRight The bottom right point of the calculation limit.
	 * @param maxIt       The maximum number of iterations, before the point is
	 *                    considered to be in the MandelbrotIterator set.
	 * @param imageWidth  The width of the image in pixel.
	 * @param imageHeight The height of the image in pixel.
	 * @param image       The image interface to write the image to.
	 * @return The image with the graphics written in.
	 */
	public MandelbrotImage drawMandelbrot(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight, int maxIt,
	    int imageWidth, int imageHeight, MandelbrotImage image) {
		double xstart = topLeft.cx();
		double xend = bottomRight.cx();
		double ystart = topLeft.cy();
		double yend = bottomRight.cy();

		// Steps for calculations are orientated on the width / height of the image
		double dx = (xend - xstart) / (imageWidth - 1);
		double dy = (yend - ystart) / (imageHeight - 1);

		MandelbrotFormula point = new MandelbrotFormula();

		// Start position
		MandelbrotPointPosition cpos = MandelbrotPointPosition.of(xstart, ystart);
		for (int column = 0; column < imageWidth; column++) {
			cpos.setCy(ystart);
			for (int line = 0; line < imageHeight; line++) {
				int iterate = point.iterate(cpos.cx(), cpos.cy(), maxIt);
				Point iPoint = new Point();
				iPoint.x = column;
				iPoint.y = line;
				image.colorizePoint(iPoint, colorize.getColorForIteration(iterate, maxIt));
				cpos.movey(dy);
			}
			cpos.movex(dx);
		}
		return image;
	}

	/**
	 * Create MandelbrotIterator object with the given colorize strategy.
	 * 
	 * @param colorize The colorize strategy to use.
	 * @return Newly created MandelbrotIterator object.
	 */
	public final static MandelbrotIterator of(MandelbrotColorize colorize) {
		MandelbrotIterator mb = new MandelbrotIterator(colorize);
		return mb;
	}

	/**
	 * Create MandelbrotIterator object with a standard colorize strategy (black and
	 * white only).
	 * 
	 * @return Newly created MandelbrotIterator object.
	 */
	public final static MandelbrotIterator of() {
		return of(new MandelbrotBlackWhite());
	}
}
