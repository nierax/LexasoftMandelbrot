/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.common.math.Bezier;
import de.lexasoft.common.math.MathException;
import de.lexasoft.common.math.Point;

/**
 * Handles the SOFT_* transitions by calculating the step factor depending on,
 * which transition variant is used.
 * 
 * @author nierax
 *
 */
public class SoftTransitionFactory extends TransitionFactory {

	private Bezier bezier;

	/**
	 * Dont't use this class directly. It is constructed via the of() Method in
	 * {@link TransitionFactory}, if needed.
	 * 
	 * @param transition
	 */
	SoftTransitionFactory(Transition transition) {
		super(transition);
		bezier = Bezier.of(createControlPoints(transition));
	}

	private List<Point> createControlPoints(Transition transition) {
		List<Point> controlPoints = new ArrayList<>();
		int steps = transition.steps();
		switch (transition.variant()) {
		case SOFT_INOUT:
			controlPoints.add(Point.of(0, 0));
			controlPoints.add(Point.of(steps * 0.5d, 0));
			controlPoints.add(Point.of(steps * 0.5d, steps));
			controlPoints.add(Point.of(steps, steps));
			break;
		case SOFT_IN:
			controlPoints.add(Point.of(0, 0));
			controlPoints.add(Point.of(steps * 0.5d, 0));
			controlPoints.add(Point.of(steps, steps));
			break;
		case SOFT_OUT:
			controlPoints.add(Point.of(0, 0));
			controlPoints.add(Point.of(steps * 0.5d, steps));
			controlPoints.add(Point.of(steps, steps));
			break;
		default:
			throw new IllegalArgumentException("Can not deal transition variant " + transition.variant());
		}
		return controlPoints;
	}

	@Override
	protected double stepFactor(int step) {
		double initialDt = 0.1;
		int rest = transition().steps();
		while (rest > 10) {
			initialDt *= 0.1;
			rest /= 10;
		}
		try {
			return bezier.bezier(bezier.tFromX2((double) step, initialDt)).y();
		} catch (MathException e) {
			throw new IllegalArgumentException(
			    String.format("Could not handle bezier calculation for x=%s with dt=%s", step, initialDt), e);
		}
	}

}
