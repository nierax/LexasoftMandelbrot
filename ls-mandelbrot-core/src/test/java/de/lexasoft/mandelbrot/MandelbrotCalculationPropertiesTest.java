/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Color;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author nierax
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
		assertEquals("./junit-tmp/mandelbrot-test.tiff", cut.getImageFilename());

		assertSame(PaletteVariant.CUSTOM, cut.getPaletteVariant());
		assertEquals(2, cut.getColors().size());

		assertEquals(25, cut.getColors().get(0).getRed());
		assertEquals(140, cut.getColors().get(0).getGreen());
		assertEquals(255, cut.getColors().get(0).getBlue());

		assertEquals(255, cut.getColors().get(1).getRed());
		assertEquals(255, cut.getColors().get(1).getGreen());
		assertEquals(255, cut.getColors().get(1).getBlue());

		assertEquals(5, cut.getColorGrading());

		assertEquals(Color.BLACK, cut.getMandelbrotColor());
	}

}
