/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;

/**
 * This interface calculates a single transition step.
 * <p>
 * There should be implementations for the different variants.
 * 
 * @author nierax
 *
 */
public interface TransitionStep {

	/**
	 * Calculates the transition of the start point on the position idx.
	 * 
	 * @param start Start point to calculate from
	 * @param delta Linear delta per intermediate step.
	 * @param idx   The index of the position - 1 based.
	 * @return
	 */
	MandelbrotPointPosition transitionTo(MandelbrotPointPosition start, MandelbrotPointPosition delta, int idx);

	/**
	 * Calculates the transition of the maximum number of iterations on the position
	 * idx.
	 * 
	 * @param startMaximumIterations Maximum iterations at the start point.
	 * @param delta                  Linear delta per intermediate step.
	 * @param idx                    The index of the position - 1 based.
	 * @return The maximum number of iterations on position idx.
	 */
	int transitionTo(int startMaximumIterations, double delta, int idx);

	static TransitionStep of(TransitionVariant variant) {
		switch (variant) {
		case LINEAR:
			return new LinearTransitionStep();

		default:
			break;
		}
		return null;
	}

}
