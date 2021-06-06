/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
class ColorControllerTest {

	MandelbrotCalculationProperties model;
	ColorControlPanel view;
	MandelbrotCanvas canvas;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotCalculationProperties.ofDefault();
		view = new ColorControlPanel();
		canvas = new MandelbrotCanvas(model);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.ColorController#ColorController(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.swing.ColorControlPanel)}.
	 */
	@Test
	final void testColorController() {
		ColorController cut = new ColorController(model, view, canvas);
		assertNotNull(cut);
		assertEquals(model.getPaletteVariant(), view.getPaletteVariant().getSelectedItem());
		assertEquals(model.getColorGrading().getStyle(), view.getColorGradingStyle().getSelectedItem());
		assertEquals(model.getColorGrading().getColorsTotal(), Integer.parseInt(view.getTotalColors().getText()));
		assertEquals("", view.getErrorText().getText());
	}

}
