/**
 * 
 */
package de.lexasoft.mandelbrot;

/**
 * @author nierax
 *
 */
public class ColorGradingCircle extends AbstractColorGrading {

	/**
	 * 
	 */
	ColorGradingCircle() {
	}

	/**
	 * In the circle implementation there must be at least one step between all
	 * colors and on more between the last and the first one.
	 * <p>
	 * In that case we would need n steps + the original n (n + n). Which can be
	 * calculated by 2*n.
	 */
	@Override
	protected int minimumGrading(int noCUngraded) {
		return 2 * noCUngraded;
	}

	/**
	 * In the circle implementation there is one interval between all colors and one
	 * more between the last and the first one.
	 * <p>
	 * That's why the number of intervals is n -1+1, which simply is n.
	 */
	@Override
	protected int nrOfIntervals(int noCUngraded) {
		return noCUngraded;
	}

}
