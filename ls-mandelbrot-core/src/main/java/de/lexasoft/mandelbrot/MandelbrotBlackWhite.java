/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;

/**
 * Simple implementation of the #MandelbrotColorize interface with only black and white.
 * 
 * @author nierax
 */
public class MandelbrotBlackWhite implements MandelbrotColorize {

	@Override
	public Color getColorForIteration(int iteration, int maxIter) {
		return (iteration == maxIter) ? Color.BLACK : Color.WHITE;
	}

}
