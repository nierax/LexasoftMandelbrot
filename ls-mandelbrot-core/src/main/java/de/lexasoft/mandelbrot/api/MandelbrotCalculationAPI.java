/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.image.BufferedImage;

import de.lexasoft.mandelbrot.MandelbrotImage;

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

	public final MandelbrotImage calculate(MandelbrotCalculationProperties model) {
		return null;
	}
}
