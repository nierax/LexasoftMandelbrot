/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.math.BigDecimal;
import java.math.MathContext;
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
		MathContext prec = CalculationPrecision.of().value();
		BigDecimal deltaX = (start.cx().subtract(end.cx())).divide(BigDecimal.valueOf(steps), prec);
//		double deltaX = (start.cx() - end.cx()) / steps;
		BigDecimal deltaY = (start.cy().subtract(end.cy())).divide(BigDecimal.valueOf(steps), prec);
//		double deltaY = (start.cy() - end.cy()) / steps;
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
	protected BigDecimal stepFactor(int step) {
		return BigDecimal.valueOf(step);
	}

	private BigDecimal val1_minus__val2_times_faktor_(BigDecimal val1, BigDecimal val2, BigDecimal factor) {
		return val1.subtract(val2.multiply(factor, CalculationPrecision.of().value()));
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
			step.getTopLeft()
			    .setCx(val1_minus__val2_times_faktor_(start.getTopLeft().cx(), factors.tlFactors.cx(), stepFactor(i)));
			step.getTopLeft()
			    .setCy(val1_minus__val2_times_faktor_(start.getTopLeft().cy(), factors.tlFactors.cy(), stepFactor(i)));
			step.getBottomRight()
			    .setCx(val1_minus__val2_times_faktor_(start.getBottomRight().cx(), factors.brFactors.cx(), stepFactor(i)));
			step.getBottomRight()
			    .setCy(val1_minus__val2_times_faktor_(start.getBottomRight().cy(), factors.brFactors.cy(), stepFactor(i)));
			step.setMaximumIterations(
			    (int) (start.getMaximumIterations() - (factors.mIFactor * stepFactor(i).doubleValue())));
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
