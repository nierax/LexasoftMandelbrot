/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author admin
 *
 */
class MandelbrotCalculationPropertiesTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotCalculationProperties#of(java.lang.String)}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testOf() throws JsonParseException, JsonMappingException, IOException {
		MandelbrotCalculationProperties cut = MandelbrotCalculationProperties.of("src/test/resources/mandelbrot-test.yaml");
		assertEquals(-2.02d, cut.getTopLeft().cx());
		assertEquals(-1.2d, cut.getTopLeft().cy());
		assertEquals(0.7d, cut.getBottomRight().cx());
		assertEquals(1.2d, cut.getBottomRight().cy());
		assertEquals(500, cut.getMaximumIterations());
		assertEquals(4590, cut.getImageWidth());
		assertEquals(4050, cut.getImageHeight());
		assertSame(ColorVariant.GRADIENT2, cut.getColorVariant());

		assertEquals(25, cut.getColor1().getRed());
		assertEquals(140, cut.getColor1().getGreen());
		assertEquals(255, cut.getColor1().getBlue());

		assertEquals(255, cut.getColor2().getRed());
		assertEquals(255, cut.getColor2().getGreen());
		assertEquals(255, cut.getColor2().getBlue());

		assertEquals(5, cut.getNrOfColors());
	}

}
