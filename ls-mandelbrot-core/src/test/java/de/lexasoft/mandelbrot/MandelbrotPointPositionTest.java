/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author admin
 *
 */
class MandelbrotPointPositionTest {

	private MandelbrotPointPosition cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = MandelbrotPointPosition.of(0, 0);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotPointPosition#movex(double)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = { 0.0, -0.2, 2.2 })
	void testDeltax(double deltax) {
		double result = cut.movex(deltax);
		assertEquals(deltax, result, "Not the right difference.");
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotPointPosition#movey(double)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = { 0.0, -0.2, 2.2 })
	void testDeltay(double deltay) {
		double result = cut.movey(deltay);
		assertEquals(deltay, result, "Not the right difference.");
	}

	private static Stream<Arguments> testEquals() {
		return Stream.of(
		    // Right
		    Arguments.of(1.0, 0.5, 1.0, 0.5, true), Arguments.of(1.00001, 0.05, 1.00001, 0.05, true),
		    // Not right
		    Arguments.of(2.0, 0.5, 1.0, 0.5, false), Arguments.of(1.0, 0.5, 2.0, 0.5, false),
		    Arguments.of(1.0, 0.8, 1.0, 0.5, false), Arguments.of(1.0, 0.5, 1.0, 0.8, false));
	}

	@ParameterizedTest
	@MethodSource
	void testEquals(double cx, double cy, double otherCx, double otherCy, boolean expected) {
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(cx, cy);
		MandelbrotPointPosition other = MandelbrotPointPosition.of(otherCx, otherCy);
		assertEquals(cut.equals(other), expected);
	}

}
