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
public class MandelbrotIteratorFast implements MandelbrotIterator {

	private final MandelbrotColorize colorize;

	/**
	 * Do not create other than with of() method.
	 */
	MandelbrotIteratorFast() {
		this(new MandelbrotBlackWhite());
	}

	MandelbrotIteratorFast(MandelbrotColorize colorize) {
		super();
		this.colorize = colorize;
	}

	/**
	 * Based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
	 * 
	 * Implementation of the Mandelbrot calculation with the type "double". Faster
	 * but less exact.
	 * 
	 * @param calculation The calculation area.
	 * @param maxIt       Number of maximum iterations.
	 * @param imageDim    The dimensions of the image to draw.
	 * @return The image with the graphics written in.
	 */
	@Override
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

}
