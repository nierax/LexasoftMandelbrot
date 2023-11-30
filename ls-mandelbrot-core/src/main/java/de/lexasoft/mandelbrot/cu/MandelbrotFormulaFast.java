/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

/**
 * Represents one point to be investigated.
 * <p>
 * Based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
 * 
 * @author nierax
 *
 */
public class MandelbrotFormulaFast {
	
	/**
	 * This logic is based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
	 * 
	 * @param cx
	 * @param cy
	 * @param maxIt
	 * @return
	 */
	public int iterate(final double cx, final double cy, int maxIt) {
		int counter = 0;
		double zx = 0.0, zy = 0.0, tmp;
		do {
			tmp = zx * zx - zy * zy + cx;
			zy = 2 * zx * zy + cy;
			zx = tmp;
			counter = counter + 1;
		} while (zx * zx + zy * zy <= 4.0 && counter < maxIt);
		return counter;
	}
	
}
