/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import java.awt.Point;

import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * This class generates an image of the Mandelbrot set
 * <p>
 * 
 * @author nierax
 */
public class MandelbrotIteratorFast {

	private final MandelbrotColorize colorize;

	/**
	 * Do not create other than with of() method.
	 */
	private MandelbrotIteratorFast() {
		this(new MandelbrotBlackWhite());
	}

	private MandelbrotIteratorFast(MandelbrotColorize colorize) {
		super();
		this.colorize = colorize;
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
	 * @return The image with the graphics written in.
	 */
	public MandelbrotImage drawMandelbrot(CalculationArea calculation, int maxIt, ImageArea imageDim) {
		double xstart = calculation.topLeft().cx();
		double xend = calculation.bottomRight().cx();
		double ystart = calculation.topLeft().cy();
		double yend = calculation.bottomRight().cy();
		int imageWidth = imageDim.width();
		int imageHeight = imageDim.height();
		MandelbrotImage image = MandelbrotImage.of(imageDim, calculation);

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
	 * Create MandelbrotIteratorFast object with the given colorize strategy.
	 * 
	 * @param colorize The colorize strategy to use.
	 * @return Newly created MandelbrotIterator object.
	 */
	public final static MandelbrotIteratorFast of(MandelbrotColorize colorize) {
		MandelbrotIteratorFast mb = new MandelbrotIteratorFast(colorize);
		return mb;
	}

	/**
	 * Create MandelbrotIteratorFast object with a standard colorize strategy (black
	 * and white only).
	 * 
	 * @return Newly created MandelbrotIterator object.
	 */
	public final static MandelbrotIteratorFast of() {
		return of(new MandelbrotBlackWhite());
	}
}
