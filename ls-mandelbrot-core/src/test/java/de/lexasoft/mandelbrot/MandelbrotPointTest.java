/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author admin
 *
 */
class MandelbrotPointTest {
	
	private MandelbrotPoint cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut= new MandelbrotPoint();
	}
	
	private static Stream<Arguments> testIterate() {
		return Stream.of(Arguments.of(0.0, 0.0, 500, true), Arguments.of(0.75, -0.75, 500, false));
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotPoint#iterate(double, double, int)}.
	 * <p>
	 * Checks some known points of the Mandelbrot set to evaluate that the method works correctly.
	 */
	@ParameterizedTest
	@MethodSource
	void testIterate(double cx, double cy, int maxIt, boolean expected) {
		int counter = cut.iterate(cx, cy, maxIt);
		boolean result = counter == maxIt;
		assertEquals(expected, result, "This point was not correctly recogned.");
	}

}
