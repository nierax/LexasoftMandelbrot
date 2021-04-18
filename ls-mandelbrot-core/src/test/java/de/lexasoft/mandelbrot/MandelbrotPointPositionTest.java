/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotPointPosition#deltax(double)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = {0.0, -0.2, 2.2})
	void testDeltax(double deltax) {
		double result = cut.deltax(deltax);
		assertEquals(deltax, result, "Not the right difference.");
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotPointPosition#deltay(double)}.
	 */
	@ParameterizedTest
	@ValueSource(doubles = {0.0, -0.2, 2.2})
	void testDeltay(double deltay) {
		double result = cut.deltay(deltay);
		assertEquals(deltay, result, "Not the right difference.");
	}

}
