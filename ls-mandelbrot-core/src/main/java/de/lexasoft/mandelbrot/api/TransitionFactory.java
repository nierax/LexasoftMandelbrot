/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the transition between 2 properties in the following dimensions:
 * <ul>
 * <li>topLeft start => topLeft end</li>
 * <li>bottomRight start => bottomRight end</li>
 * <li>maximumIterations start => maximumIterations end</li>
 * </ul>
 * Example: <br/>
 * [start] -> [end] in 4 steps => <br/>
 * [start] -> [step1] -> [step2] -> [step3] -> [step4] -> [end]
 * <p>
 * Please note: All other parameters are kept unchanged, including the
 * filenames. This must be done after the list is complete.
 * 
 * @author nierax
 *
 */
public class TransitionFactory {

	class TransitionFactors {
		MandelbrotPointPosition tlFactors;
		MandelbrotPointPosition brFactors;
		double mIFactor;
	}

	private Transition transition;

	TransitionFactory(Transition transition) {
		if (transition.steps() < 1) {
			throw new IllegalArgumentException("At least one step for transition required.");
		}
		this.transition = transition;
	}

	private MandelbrotPointPosition calcPointTransition(MandelbrotPointPosition start, MandelbrotPointPosition end,
	    int steps) {
		double deltaX = (start.cx() - end.cx()) / steps;
		double deltaY = (start.cy() - end.cy()) / steps;
		return MandelbrotPointPosition.of(deltaX, deltaY);
	}

	/**
	 * Calculates the step factor in the formula startValue - (deltaValue *
	 * stepFactor).
	 * <p>
	 * The step factor is the value, which controls the transition mode.
	 * <p>
	 * For {@link TransitionVariant#LINEAR} the step factor simply is the step
	 * itself.
	 * 
	 * @param step The step of the transition, being performed.
	 * @return The factor for this step.
	 */
	protected double stepFactor(int step) {
		return step;
	}

	/**
	 * Does the transition and returns a list with only the transition steps. Both
	 * start and end point are not included.
	 * 
	 * @param start      Start point of the transition
	 * @param end        End point of the transition
	 * @param transition Then transitions parameters
	 * @return List with the transition steps, only.
	 */
	public List<MandelbrotCalculationProperties> createTransitions(MandelbrotCalculationProperties start,
	    MandelbrotCalculationProperties end) {
		List<MandelbrotCalculationProperties> listOfProps = new ArrayList<>();
		TransitionFactors factors = new TransitionFactors();
		factors.tlFactors = calcPointTransition(start.getTopLeft(), end.getTopLeft(), transition.steps() + 1);
		factors.brFactors = calcPointTransition(start.getBottomRight(), end.getBottomRight(), transition.steps() + 1);
		factors.mIFactor = (double) (start.getMaximumIterations() - end.getMaximumIterations()) / (transition.steps() + 1);
		for (int i = 1; i < transition.steps() + 1; i++) {
			MandelbrotCalculationProperties step = start.cloneValues();
			step.getTopLeft().setCx(start.getTopLeft().cx() - (factors.tlFactors.cx() * stepFactor(i)));
			step.getTopLeft().setCy(start.getTopLeft().cy() - (factors.tlFactors.cy() * stepFactor(i)));
			step.getBottomRight().setCx(start.getBottomRight().cx() - (factors.brFactors.cx() * stepFactor(i)));
			step.getBottomRight().setCy(start.getBottomRight().cy() - (factors.brFactors.cy() * stepFactor(i)));
			step.setMaximumIterations((int) (start.getMaximumIterations() - (factors.mIFactor * stepFactor(i))));
			listOfProps.add(step);
		}
		return listOfProps;
	}

	/**
	 * 
	 * @param transition
	 * @return
	 */
	public static TransitionFactory of(Transition transition) {
		return (transition.variant() == TransitionVariant.LINEAR) ? new TransitionFactory(transition)
		    : new SoftTransitionFactory(transition);
	}

	/**
	 * @return the transition
	 */
	Transition transition() {
		return transition;
	}
}
