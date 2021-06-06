/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.event.FocusEvent;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * @author nierax
 *
 */
class ColorControllerTest {

	private MandelbrotCalculationProperties model;
	private ColorControlPanel view;
	private MandelbrotCanvas canvas;
	private ColorController cut;
	private FocusEvent focusEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotCalculationProperties.ofDefault();
		view = new ColorControlPanel();
		canvas = new MandelbrotCanvas(model);
		cut = new ColorController(model, view, canvas);
		focusEvent = new FocusEvent(view, 0);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.ColorController#ColorController(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.swing.ColorControlPanel)}.
	 */
	@Test
	final void testColorController() {
		assertNotNull(cut);
		assertEquals(model.getPaletteVariant(), view.getPaletteVariant().getSelectedItem());
		assertEquals(model.getColorGrading().getStyle(), view.getColorGradingStyle().getSelectedItem());
		assertEquals(model.getColorGrading().getColorsTotal(), Integer.parseInt(view.getTotalColors().getText()));
		assertEquals("", view.getErrorText().getText());
	}

	private static Stream<Arguments> testChangeTotalColorsOk() {
		return Stream.of(
		    // These values are valid and the expected new value is the same as the new
		    // value set.
		    Arguments.of(3, ColorGradingStyle.LINE, PaletteVariant.BLUEWHITE, 3),
		    Arguments.of(4, ColorGradingStyle.CIRCLE, PaletteVariant.BLUEWHITE, 4),
		    Arguments.of(13, ColorGradingStyle.LINE, PaletteVariant.RAINBOW7, 13),
		    Arguments.of(14, ColorGradingStyle.CIRCLE, PaletteVariant.RAINBOW7, 14),
		    Arguments.of(57, ColorGradingStyle.LINE, PaletteVariant.RAINBOW29, 57),
		    Arguments.of(58, ColorGradingStyle.CIRCLE, PaletteVariant.RAINBOW29, 58),
		    Arguments.of(100, ColorGradingStyle.CIRCLE, PaletteVariant.RAINBOW7, 100),
		    // These values are not valid and the expected new value is the minimum value
		    // for this variant.
		    Arguments.of(2, ColorGradingStyle.LINE, PaletteVariant.BLUEWHITE, 3),
		    Arguments.of(2, ColorGradingStyle.CIRCLE, PaletteVariant.BLUEWHITE, 4),
		    Arguments.of(10, ColorGradingStyle.LINE, PaletteVariant.RAINBOW7, 13),
		    Arguments.of(10, ColorGradingStyle.CIRCLE, PaletteVariant.RAINBOW7, 14),
		    Arguments.of(25, ColorGradingStyle.LINE, PaletteVariant.RAINBOW29, 57),
		    Arguments.of(25, ColorGradingStyle.CIRCLE, PaletteVariant.RAINBOW29, 58));
	}

	/**
	 * Tests the controller method for changing the number of colors in color
	 * grading, if the new number of colors is valid.
	 * 
	 * @param newValue
	 * @param gradingStyle
	 * @param paletteVariant
	 */
	@ParameterizedTest
	@MethodSource
	final void testChangeTotalColorsOk(int newValue, ColorGradingStyle gradingStyle, PaletteVariant paletteVariant,
	    int expectedNew) {
		// Prepare view and model
		view.getTotalColors().setText(Integer.toString(newValue));
		model.setPaletteVariant(paletteVariant);
		view.getPaletteVariant().setSelectedItem(paletteVariant);
		model.getColorGrading().setStyle(gradingStyle);
		view.getColorGradingStyle().setSelectedItem(gradingStyle);

		// Run test
		cut.changeTotalColors(focusEvent);

		// Assert results
		// Do model and view have the changed value?
		assertEquals(expectedNew, model.getColorGrading().getColorsTotal());
		assertEquals(expectedNew, Integer.parseInt(view.getTotalColors().getText()));
		if (expectedNew > newValue) {
			assertNotEquals("", view.getErrorText().getText());
		} else {
			assertEquals("", view.getErrorText().getText());
		}
	}

}
