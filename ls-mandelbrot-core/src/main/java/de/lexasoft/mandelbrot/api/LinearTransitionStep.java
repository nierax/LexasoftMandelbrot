/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;

/**
 * Implementation of {@link TransitionStep} for linear transitions.
 * 
 * @author nierax
 *
 */
public class LinearTransitionStep implements TransitionStep {

	/**
	 * 
	 */
	public LinearTransitionStep() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public MandelbrotPointPosition transitionTo(MandelbrotPointPosition start, MandelbrotPointPosition delta, int idx) {
		double x = start.cx() - (delta.cx() * idx);
		double y = start.cy() - (delta.cy() * idx);
		return MandelbrotPointPosition.of(x, y);
	}

	@Override
	public int transitionTo(int startMaximumIterations, double delta, int idx) {
		return (int) (startMaximumIterations - (delta * idx));
	}

}
