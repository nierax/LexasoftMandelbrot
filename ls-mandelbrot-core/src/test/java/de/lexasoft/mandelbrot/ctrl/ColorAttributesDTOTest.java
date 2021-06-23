/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * @author admin
 *
 */
class ColorAttributesDTOTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.ColorAttributesDTO#ofDefault()}.
	 */
	@Test
	final void testOfDefault() {
		ColorAttributesDTO cut = ColorAttributesDTO.ofDefault();
		assertNotNull(cut);
		assertEquals(PaletteVariant.BLUEWHITE, cut.getPaletteVariant());
		assertNull(cut.getCustomColorPalette());
		assertEquals(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 6), cut.getColorGrading());
		assertEquals(Color.BLACK, cut.getMandelbrotColor().getColor());
	}

}
