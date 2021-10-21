/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
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

	@Test
	void testOfCopy() {
		MandelbrotPointPosition source = MandelbrotPointPosition.of(1.0, 1.5);
		MandelbrotPointPosition other = MandelbrotPointPosition.of(source);
		assertNotNull(other);
		assertNotSame(source, other);
		assertEquals(1.0, other.cx());
		assertEquals(1.5, other.cy());
	}

	/**
	 * Values should be as given, returned instance must be the same as the cut.
	 */
	@Test
	void testMoveTo() {
		MandelbrotPointPosition result = cut.moveTo(-2.02, 1.2);

		assertEquals(-2.02, cut.cx());
		assertEquals(1.2, cut.cy());
		assertSame(cut, result);
	}

	private final static Stream<Arguments> testMove() {
		return Stream.of(//
		    // Don't move anything
		    Arguments.of(0.2, 0.3, 0, 0, 0.2, 0.3),
		    // Move to right and down
		    Arguments.of(0.2, 0.3, 1, 0.5, 1.2, 0.8),
		    // Move to left and down
		    Arguments.of(0.2, 0.3, -1, 0.5, -0.8, 0.8),
		    // Move to left and up
		    Arguments.of(0.2, 0.3, -1, -0.5, -0.8, -0.2),
		    // Move to right and up
		    Arguments.of(0.2, 0.3, 1, -0.5, 1.2, -0.2));
	}

	/**
	 * 
	 * @param cx     Initial x
	 * @param cy     Initial y
	 * @param deltaX Delta in x dimension
	 * @param deltaY Delta in y dimension
	 * @param expX   Expected x value
	 * @param expY   Expected y value
	 */
	@ParameterizedTest
	@MethodSource
	void testMove(double cx, double cy, double deltaX, double deltaY, double expX, double expY) {
		// Prepare
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(cx, cy);
		// Run
		MandelbrotPointPosition result = cut.move(MandelbrotPointPosition.of(deltaX, deltaY));
		// Check
		assertSame(cut, result);
		assertEquals(expX, result.cx(), 0.0001);
		assertEquals(expY, result.cy(), 0.0001);
	}

	private final static Stream<Arguments> testSubtract() {
		return Stream.of( //
		    // x and y +
		    Arguments.of(0.2, 0.3, 0.1, 0.05, 0.1, 0.25),
		    // x - and y +
		    Arguments.of(0.2, 0.3, -0.1, 0.05, 0.3, 0.25),
		    // x and y -
		    Arguments.of(0.2, 0.3, -0.1, -0.05, 0.3, 0.35),
		    // x + and y -
		    Arguments.of(0.2, 0.3, 0.1, -0.05, 0.1, 0.35));
	}

	/**
	 * 
	 * @param cx     The initial x value
	 * @param cy     The initial y value
	 * @param sX     The x value to be subtracted
	 * @param sY     The y value to be subtracted
	 * @param deltaX The expected delta in x value
	 * @param deltaY The expected delta in y value
	 */
	@ParameterizedTest
	@MethodSource
	final void testSubtract(double cx, double cy, double sX, double sY, double deltaX, double deltaY) {
		// Prepare
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(cx, cy);
		// Run
		MandelbrotPointPosition result = cut.subtract(MandelbrotPointPosition.of(sX, sY));
		// Check
		assertNotNull(result);
		assertNotSame(cut, result);
		assertEquals(deltaX, result.cx(), 0.0001);
		assertEquals(deltaY, result.cy(), 0.0001);
	}

}
