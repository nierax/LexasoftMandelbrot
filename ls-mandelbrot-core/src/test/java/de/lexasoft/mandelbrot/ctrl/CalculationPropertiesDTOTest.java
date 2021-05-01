/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Color;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.PaletteVariant;

/**
 * @author nierax
 *
 */
class CalculationPropertiesDTOTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.CalculationPropertiesDTO#of(java.lang.String)}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testOf() throws JsonParseException, JsonMappingException, IOException {
		CalculationPropertiesDTO cut = CalculationPropertiesDTO.of("src/test/resources/mandelbrot-test.yaml");
		assertEquals("-2.02", cut.getTopLeft().getCx());
		assertEquals("1.2", cut.getTopLeft().getCy());
		assertEquals("0.7", cut.getBottomRight().getCx());
		assertEquals("-1.2", cut.getBottomRight().getCy());
		assertEquals(500, cut.getMaximumIterations());
		assertEquals(4590, cut.getImageWidth());
		assertEquals(4050, cut.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test.tiff", cut.getImageFilename());

		assertSame(PaletteVariant.CUSTOM, cut.getPaletteVariant());
		assertEquals(2, cut.getCustomColorPalette().size());

		assertEquals(25, cut.getCustomColorPalette().get(0).getColor().getRed());
		assertEquals(140, cut.getCustomColorPalette().get(0).getColor().getGreen());
		assertEquals(255, cut.getCustomColorPalette().get(0).getColor().getBlue());

		assertEquals(255, cut.getCustomColorPalette().get(1).getColor().getRed());
		assertEquals(255, cut.getCustomColorPalette().get(1).getColor().getGreen());
		assertEquals(255, cut.getCustomColorPalette().get(1).getColor().getBlue());

		assertEquals(5, cut.getColorGrading());

		assertEquals(Color.BLACK.getRGB(), cut.getMandelbrotColor().getColor().getRGB());
	}

}
