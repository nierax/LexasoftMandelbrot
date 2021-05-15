/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;

/**
 * @author admin
 *
 */
class LinearTransitionStepTest {

	TransitionStep cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new LinearTransitionStep();
	}

	private static MandelbrotPointPosition point(double x, double y) {
		return MandelbrotPointPosition.of(x, y);
	}

	private static Stream<Arguments> testTransitionToMandelbrotPointPositionInt() {
		return Stream.of(Arguments.of(point(0, 0), point(1, 1), -4, point(4, 4)),
		    Arguments.of(point(0, 0), point(1, 1), -1, point(1, 1)),
		    Arguments.of(point(1, 1), point(0.5, 1), -2, point(2, 3)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.LinearTransitionStep#transitionTo(de.lexasoft.mandelbrot.MandelbrotPointPosition, int)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testTransitionToMandelbrotPointPositionInt(MandelbrotPointPosition start, MandelbrotPointPosition delta, int idx,
	    MandelbrotPointPosition expected) {
		MandelbrotPointPosition transitionStep = cut.transitionTo(start, delta, idx);
		assertNotNull(transitionStep);
		assertEquals(expected.cx(), transitionStep.cx(), 0.0001);
		assertEquals(expected.cy(), transitionStep.cy(), 0.0001);
	}

	private static Stream<Arguments> testTransitionToIntInt() {
		return Stream.of(Arguments.of(50, 20, -4, 130));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.LinearTransitionStep#transitionTo(int, int)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testTransitionToIntInt(int start, double delta, int idx, int expected) {
		int transitionStep = cut.transitionTo(start, delta, idx);
		assertEquals(expected, transitionStep);
	}

}
