/**
 * 
 */
package de.lexasoft.mandelbrot;

import de.lexasoft.mandelbrot.api.ColorGrading;

/**
 * Implements the color line grading.
 * 
 * @see <a
 *      href=https://github.com/nierax/LexasoftMandelbrot/blob/feature/ColorGradingCircle/documentation/ColorGrading-Line.pdf>Color
 *      Grading Line documentation</a>
 * 
 * @author nierax
 *
 */
public class ColorGradingLine extends AbstractColorGrading {

	/**
	 * 
	 */
	ColorGradingLine() {
	}

	public static ColorGrading of() {
		return new ColorGradingLine();
	}

	/**
	 * In the line implementation there must be at least one step between all
	 * colors.
	 * <p>
	 * In that case we would need n - 1 steps + the original n (n + n -1). Which can
	 * be calculated by 2*n - 1.
	 */
	@Override
	protected int minimumGrading(int noCUngraded) {
		return 2 * noCUngraded - 1;
	}

	/**
	 * In the line implementation there is one interval between all colors, but not
	 * between the last and the first one.
	 * <p>
	 * That's why the number of intervals is n -1.
	 */
	@Override
	protected int nrOfIntervals(int noCUngraded) {
		return noCUngraded - 1;
	}

}
