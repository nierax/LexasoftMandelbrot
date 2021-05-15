/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;

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

	private MandelbrotPointPosition calcPointTransition(MandelbrotPointPosition start, MandelbrotPointPosition end,
	    int steps) {
		double deltaX = (start.cx() - end.cx()) / steps;
		double deltaY = (start.cy() - end.cy()) / steps;
		return MandelbrotPointPosition.of(deltaX, deltaY);
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
	    MandelbrotCalculationProperties end, Transition transition) {
		if (transition.steps() < 1) {
			throw new IllegalArgumentException("At least one step for transition required.");
		}
		List<MandelbrotCalculationProperties> listOfProps = new ArrayList<>();
		TransitionFactors factors = new TransitionFactors();
		factors.tlFactors = calcPointTransition(start.getTopLeft(), end.getTopLeft(), transition.steps() + 1);
		factors.brFactors = calcPointTransition(start.getBottomRight(), end.getBottomRight(), transition.steps() + 1);
		factors.mIFactor = (double) (start.getMaximumIterations() - end.getMaximumIterations()) / (transition.steps() + 1);
		TransitionStep tranStep = TransitionStep.of(transition.variant());
		for (int i = 1; i < transition.steps() + 1; i++) {
			MandelbrotCalculationProperties step = start.cloneValues();
			step.setTopLeft(tranStep.transitionTo(start.getTopLeft(), factors.tlFactors, i));
			step.setBottomRight(tranStep.transitionTo(start.getBottomRight(), factors.brFactors, i));
			step.setMaximumIterations(tranStep.transitionTo(start.getMaximumIterations(), factors.mIFactor, i));
			listOfProps.add(step);
		}
		return listOfProps;
	}
}
