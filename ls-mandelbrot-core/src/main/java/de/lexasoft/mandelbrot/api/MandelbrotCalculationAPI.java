/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.image.BufferedImage;

import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotColorizeFactory;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.cu.MandelbrotIterator;

/**
 * Facade to the calculation
 * <p>
 * Returns the result of the calculation in a {@link BufferedImage} object for
 * further use. Does not persist the image in any way, neither on disk or in
 * memory.
 * 
 * @author nierax
 *
 */
public class MandelbrotCalculationAPI {

	/**
	 * Calls the calculation of the Mandelbrot set.
	 * 
	 * @param model
	 * @return
	 */
	public final MandelbrotImage calculate(MandelbrotCalculationProperties model) {
		MandelbrotColorize colorize = MandelbrotColorizeFactory.of(model.getPaletteVariant(), model.getCustomColorPalette(),
		    model.getColorGrading(), model.getMandelbrotColor());
		MandelbrotIterator calculator = MandelbrotIterator.of(colorize);
		long start = System.currentTimeMillis();
		MandelbrotImage image = calculator.drawMandelbrot(model.getTopLeft(), model.getBottomRight(),
		    model.getMaximumIterations(), model.getImageWidth(), model.getImageHeight());
		long stop = System.currentTimeMillis();
		InfoCallbackAPI.of().outCalculationReady(stop - start);
		return image;
	}
}
