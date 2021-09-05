/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * Collects validations needed before starting the calculation.
 * 
 * @author nierax
 *
 */
public class ValidationAPI {

	/**
	 * 
	 */
	private ValidationAPI() {
	}

	public int minimumNrOfColorsForGrading(int nrOfColorsUngraded, ColorGradingStyle gradingStyle) {
		switch (gradingStyle) {
		case LINE:
			return 2 * nrOfColorsUngraded - 1;
		case CIRCLE:
			return 2 * nrOfColorsUngraded;
		default:
			return 0;
		}
	}

	public static ValidationAPI of() {
		return new ValidationAPI();
	}

}
