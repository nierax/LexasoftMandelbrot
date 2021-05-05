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
 * 
 * Important: All other parameters are kept unchanged, including the filenames.
 * This must be done after the list is complete.
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
	 * @param start
	 * @param end
	 * @param transition
	 * @return
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
		for (int i = 1; i < transition.steps() + 1; i++) {
			MandelbrotCalculationProperties step = start.cloneValues();
			step.getTopLeft().setCx(start.getTopLeft().cx() - (factors.tlFactors.cx() * i));
			step.getTopLeft().setCy(start.getTopLeft().cy() - (factors.tlFactors.cy() * i));
			step.getBottomRight().setCx(start.getBottomRight().cx() - (factors.brFactors.cx() * i));
			step.getBottomRight().setCy(start.getBottomRight().cy() - (factors.brFactors.cy() * i));
			step.setMaximumIterations((int) (start.getMaximumIterations() - (factors.mIFactor * i)));
			listOfProps.add(step);
		}
		return listOfProps;
	}
}
