/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * Handles the SOFT_* transitions by calculating the step factor depending on,
 * which transition variant is used.
 * 
 * @author nierax
 *
 */
public class SoftTransitionFactory extends TransitionFactory {

	/**
	 * Dont't use this class directly. It is constructed via the of() Method in
	 * {@link TransitionFactory}, if needed.
	 * 
	 * @param transition
	 */
	SoftTransitionFactory(Transition transition) {
		super(transition);
	}

	@Override
	protected int stepFactor(int step) {
		// TODO Auto-generated method stub
		return super.stepFactor(step);
	}

}
