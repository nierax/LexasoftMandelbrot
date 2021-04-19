package de.lexasoft.mandelbrot;

import java.awt.Color;

public interface MandelbrotColorize {

	/**
	 * Find the color for this number of iterations.
	 * <p>
	 * If the point is in the Mandelbrot set, black should be returned, otherwise a color
	 * depending on the algorithm of the implementation.
	 * 
	 * @param iteration Number of iterations on a special point.
	 * @param maxIter   Maximum number of iterations on a special point.
	 * @return The color of the point with the given number of iterations.
	 */
	Color getColorForIteration(int iteration, int maxIter);

}