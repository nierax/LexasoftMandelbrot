/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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
		cut = MandelbrotPointPosition.of(BigDecimal.valueOf(Double.valueOf(0)), BigDecimal.valueOf(Double.valueOf(0)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotPointPosition#movex(BigDecimal)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = { 0.0, -0.2, 2.2 })
	void testDeltax(double deltax) {
		BigDecimal result = cut.movex(BigDecimal.valueOf(deltax));
		assertEquals(deltax, result.doubleValue(), "Not the right difference.");
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotPointPosition#movey(BigDecimal)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = { 0.0, -0.2, 2.2 })
	void testDeltay(double deltay) {
		BigDecimal result = cut.movey(BigDecimal.valueOf(deltay));
		assertEquals(deltay, result.doubleValue(), "Not the right difference.");
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
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(BigDecimal.valueOf(cx), BigDecimal.valueOf(cy));
		MandelbrotPointPosition other = MandelbrotPointPosition.of(BigDecimal.valueOf(otherCx),
		    BigDecimal.valueOf(otherCy));
		assertEquals(cut.equals(other), expected);
	}

	@Test
	void testOfCopy() {
		MandelbrotPointPosition source = MandelbrotPointPosition.of(BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.5));
		MandelbrotPointPosition other = MandelbrotPointPosition.of(source);
		assertNotNull(other);
		assertNotSame(source, other);
		assertEquals(1.0, other.cx().doubleValue());
		assertEquals(1.5, other.cy().doubleValue());
	}

	@Test
	void of_from_double_with_values() {
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(2.02d, -0.8d);
		assertNotNull(cut);
		assertEquals(2.02d, cut.cx().doubleValue());
		assertEquals(-0.8d, cut.cy().doubleValue());
	}

	@Test
	void of_from_double_with_Double_NaN() {
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(2.02d, Double.NaN);
		assertNotNull(cut);
		assertEquals(2.02d, cut.cx().doubleValue());
		assertFalse(cut.isCySet());

		cut = MandelbrotPointPosition.of(Double.NaN, -0.8d);
		assertNotNull(cut);
		assertFalse(cut.isCxSet());
		assertEquals(-0.8d, cut.cy().doubleValue());
	}

	/**
	 * Values should be as given, returned instance must be the same as the cut.
	 */
	@Test
	void testMoveTo() {
		MandelbrotPointPosition result = cut.moveTo(BigDecimal.valueOf(-2.02), BigDecimal.valueOf(1.2));

		assertEquals(-2.02, cut.cx().doubleValue());
		assertEquals(1.2, cut.cy().doubleValue());
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
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(BigDecimal.valueOf(cx), BigDecimal.valueOf(cy));
		// Run
		MandelbrotPointPosition result = //
		    cut.move(MandelbrotPointPosition.of(BigDecimal.valueOf(deltaX), BigDecimal.valueOf(deltaY)));
		// Check
		assertSame(cut, result);
		assertEquals(expX, result.cx().doubleValue(), 0.0001);
		assertEquals(expY, result.cy().doubleValue(), 0.0001);
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
		MandelbrotPointPosition cut = MandelbrotPointPosition.of(BigDecimal.valueOf(cx), BigDecimal.valueOf(cy));
		// Run
		MandelbrotPointPosition result = //
		    cut.subtract(MandelbrotPointPosition.of(BigDecimal.valueOf(sX), BigDecimal.valueOf(sY)));
		// Check
		assertNotNull(result);
		assertNotSame(cut, result);
		assertEquals(deltaX, result.cx().doubleValue(), 0.0001);
		assertEquals(deltaY, result.cy().doubleValue(), 0.0001);
	}

	/**
	 * Test for isCxSet Method.
	 * <ul>
	 * <li>NaN: false</li>
	 * <li>0: true</li>
	 * <li>1: true</li>
	 * </ul>
	 */
	@Test
	final void testIsCxSet() {
		assertFalse(new MandelbrotPointPosition().isCxSet());
		assertTrue(cut.isCxSet());
		assertTrue(MandelbrotPointPosition.of(BigDecimal.valueOf(1), BigDecimal.valueOf(1)).isCxSet());
	}

	/**
	 * Test for isCySet Method.
	 * <ul>
	 * <li>NaN: false</li>
	 * <li>0: true</li>
	 * <li>1: true</li>
	 * </ul>
	 */
	@Test
	final void testIsCySet() {
		assertFalse(new MandelbrotPointPosition().isCySet());
		assertTrue(cut.isCySet());
		assertTrue(MandelbrotPointPosition.of(BigDecimal.ZERO, BigDecimal.valueOf(1)).isCySet());
	}

}
