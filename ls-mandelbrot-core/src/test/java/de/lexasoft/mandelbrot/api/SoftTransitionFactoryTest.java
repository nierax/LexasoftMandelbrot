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
		    Arguments.of(Transition.of(10, TransitionVariant.SOFT_OUT), 9, 9.9));
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
