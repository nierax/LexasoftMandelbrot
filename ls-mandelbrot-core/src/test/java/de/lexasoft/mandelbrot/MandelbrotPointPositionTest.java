/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
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
	 * Test method for {@link de.lexasoft.mandelbrot.api.MandelbrotPointPosition#movex(double)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = {0.0, -0.2, 2.2})
	void testDeltax(double deltax) {
		double result = cut.movex(deltax);
		assertEquals(deltax, result, "Not the right difference.");
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.api.MandelbrotPointPosition#movey(double)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = {0.0, -0.2, 2.2})
	void testDeltay(double deltay) {
		double result = cut.movey(deltay);
		assertEquals(deltay, result, "Not the right difference.");
	}

}
