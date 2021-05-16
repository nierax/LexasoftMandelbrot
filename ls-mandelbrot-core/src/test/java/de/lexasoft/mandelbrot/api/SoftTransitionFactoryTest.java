/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class SoftTransitionFactoryTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static Stream<Arguments> testStepFactor() {
		return Stream.of(Arguments.of(Transition.of(10, TransitionVariant.SOFT_INOUT), 1, 0.14621d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_INOUT), 9, 9.85377d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_IN), 1, 0.1d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_IN), 9, 8.1d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_OUT), 1, 1.9d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_OUT), 9, 9.9),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_INOUT), 1, 0.045798),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_INOUT), 29, 29.954202),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_INOUT), 1, 0.0044569),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_INOUT), 299, 299.995543),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_INOUT), 1, 4.43490e-4),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_INOUT), 2999, 2999.9995644),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_IN), 1, 0.0333326),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_IN), 29, 28.03352),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_IN), 1, 0.0033267),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_IN), 299, 298.00353),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_IN), 1, 3.32667e-4),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_IN), 2999, 2998.0203266),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_OUT), 1, 1.96664),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_OUT), 29, 29.96667),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_OUT), 1, 1.99467),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_OUT), 299, 299.996667),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_OUT), 1, 1.997667),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_OUT), 2999, 2999.99967));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.SoftTransitionFactory#stepFactor(int)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testStepFactor(Transition transition, int step, double expected) {
		TransitionFactory cut = TransitionFactory.of(transition);
		assertTrue(cut instanceof SoftTransitionFactory);
		assertEquals(expected, cut.stepFactor(step), 0.00001);
	}

}
