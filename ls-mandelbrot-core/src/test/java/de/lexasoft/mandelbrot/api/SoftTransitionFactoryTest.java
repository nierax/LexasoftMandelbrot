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
		return Stream.of(Arguments.of(Transition.of(10, TransitionVariant.SOFT_INOUT), 1, 0.131783385d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_INOUT), 9, 10.42415769d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_IN), 1, 0.0909109090d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_IN), 9, 7.363603636d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_OUT), 1, 1.90910909d),
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_OUT), 9, 10.6363563),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_INOUT), 1, 0.04427581d),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_INOUT), 29, 30.81754d),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_INOUT), 1, 0.00444375868d),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_INOUT), 299, 300.982179d),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_INOUT), 1, 4.43490e-4),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_INOUT), 2999, 3000.99825d),
		    Arguments.of(Transition.of(58, TransitionVariant.SOFT_INOUT), 57, 58.9068057),
		    Arguments.of(Transition.of(58, TransitionVariant.SOFT_INOUT), 59, 59),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_IN), 1, 0.03226193d),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_IN), 28, 25.2903334D),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_IN), 29, 27.1290397),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_IN), 31, 31),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_IN), 1, 0.0033267),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_IN), 299, 297.01300096d),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_IN), 1, 3.32667e-4),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_IN), 2999, 2997.003999),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_OUT), 1, 1.967858064d),
		    Arguments.of(Transition.of(30, TransitionVariant.SOFT_OUT), 29, 30.8709682d),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_OUT), 1, 1.9953222d),
		    Arguments.of(Transition.of(300, TransitionVariant.SOFT_OUT), 299, 300.986709d),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_OUT), 1, 1.998333d),
		    Arguments.of(Transition.of(3000, TransitionVariant.SOFT_OUT), 2999, 3000.99866d));
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
		assertEquals(expected, cut.stepFactor(step).doubleValue(), 0.00001);
	}

}
