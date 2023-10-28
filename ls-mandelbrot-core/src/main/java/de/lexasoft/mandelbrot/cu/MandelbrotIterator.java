package de.lexasoft.mandelbrot.cu;

import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * This interface represents an iterator for calculation of the Mandelbrot set.
 * 
 * @author nierax
 */
public interface MandelbrotIterator {

	/**
	 * This method is designed to calculate the Mandelbrot set into an Image.
	 *
	 * @param calculation The calculation area.
	 * @param maxIt       Number of maximum iterations.
	 * @param imageDim    The dimensions of the image to draw.
	 * @return The image with the graphics written in.
	 */
	MandelbrotImage drawMandelbrot(CalculationArea calculation, int maxIt, ImageArea imageDim);

	/**
	 * Create MandelbrotIteratorFast object with the given colorize strategy.
	 * 
	 * @param colorize The colorize strategy to use.
	 * @return Newly created MandelbrotIterator object.
	 */
	public static MandelbrotIterator of(MandelbrotColorize colorize) {
		MandelbrotIterator mb = new MandelbrotIteratorFast(colorize);
		return mb;
	}

	/**
	 * Create MandelbrotIteratorFast object with a standard colorize strategy (black
	 * and white only).
	 * 
	 * @return Newly created MandelbrotIterator object.
	 */
	public static MandelbrotIterator of() {
		return of(new MandelbrotBlackWhite());
	}
}
