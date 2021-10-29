/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.ctrl.ColorAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.ColorDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * @author nierax
 *
 */
class ColorControllerTest {

	private MandelbrotAttributesDTO model;
	private ColorControlPanel view;
	private ColorController cut;
	private FocusEvent focusEvent;
	private ItemEvent itemEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotAttributesDTO.ofDefaults();
		view = new ColorControlPanel();
		cut = new ColorController(model.getColor(), view);
		focusEvent = new FocusEvent(view, 0);
		itemEvent = new ItemEvent(view.getColorGradingStyle(), 0, model.getColor().getColorGrading().getStyle(),
		    ItemEvent.SELECTED);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.ColorController#ColorController(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.swing.ColorControlPanel)}.
	 */
	@Test
	final void testColorController() {
		assertNotNull(cut);
		ColorAttributesDTO color = model.getColor();
		assertEquals(color.getPaletteVariant(), view.getPaletteVariant().getSelectedItem());
		assertEquals(color.getColorGrading().getStyle(), view.getColorGradingStyle().getSelectedItem());
		assertEquals(color.getColorGrading().getColorsTotal(), Integer.parseInt(view.getTotalColors().getText()));
		assertNotNull(view.getMessagePanel());
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
		cut.paletteVariant(paletteVariant);
		cut.gradingStyle(gradingStyle);
		cut.initView();

		// Set the new value
		view.getTotalColors().setText(Integer.toString(newValue));

		// Run test
		cut.changeTotalColors(focusEvent);

		// Assert results
		// Do model and view have the changed value?
		assertEquals(expectedNew, cut.totalNrOfColors());
		assertEquals(expectedNew, Integer.parseInt(view.getTotalColors().getText()));
		if (expectedNew > newValue) {
			assertNotEquals("", view.getMessagePanel().getCompleteText());
		} else {
			assertEquals("", view.getMessagePanel().getCompleteText());
		}
	}

	private static Stream<Arguments> testChangeColorGradingStyle() {
		return Stream.of(
		    // Test cases
		    Arguments.of(ColorGradingStyle.NONE, 6, PaletteVariant.BLUEWHITE, 6, false),
		    Arguments.of(ColorGradingStyle.LINE, 6, PaletteVariant.BLUEWHITE, 6, true),
		    Arguments.of(ColorGradingStyle.CIRCLE, 13, PaletteVariant.BLUEWHITE, 13, true),
		    Arguments.of(ColorGradingStyle.CIRCLE, 10, PaletteVariant.RAINBOW7, 14, true),
		    Arguments.of(ColorGradingStyle.LINE, 13, PaletteVariant.RAINBOW29, 57, true),
		    Arguments.of(ColorGradingStyle.LINE, 2, PaletteVariant.BLUEWHITE, 3, true),
		    Arguments.of(ColorGradingStyle.LINE, 2, PaletteVariant.CUSTOM, 3, true),
		    Arguments.of(ColorGradingStyle.LINE, 6, PaletteVariant.CUSTOM, 6, true));
	}

	/**
	 * Tests the function of the method
	 * {@link ColorController#changeColorGradingStyle(java.awt.event.ItemEvent)}.
	 * 
	 * @param newValue     The new value for color grading style to set.
	 * @param nrOfColors   The number of colors for grading in the model.
	 * @param variant      The palette variant in the model.
	 * @param expNrOfColor The number of colors, expected after the method is
	 *                     called.
	 * @param expEnabled   Should the input field for total number of colors be
	 *                     enabled?
	 */
	@ParameterizedTest
	@MethodSource
	final void testChangeColorGradingStyle(ColorGradingStyle newValue, int nrOfColors, PaletteVariant variant,
	    int expNrOfColor, boolean expEnabled) {
		// Prepare view and controller
		cut.gradingStyle(newValue);
		itemEvent = new ItemEvent(view.getColorGradingStyle(), 0, newValue, ItemEvent.SELECTED);
		cut.totalNrOfColors(nrOfColors);
		cut.paletteVariant(variant);
		// Use cut's function to put these values into the view
		cut.initView();
		// Now set the value of color grading style to the view (simulate input)
		view.getColorGradingStyle().setSelectedItem(newValue);

		// Run test
		cut.changeColorGradingStyle(itemEvent);

		// Assert results
		assertEquals(expEnabled, view.getTotalColors().isEnabled());
		assertEquals(newValue, cut.gradingStyle());
		assertEquals(newValue, view.getColorGradingStyle().getSelectedItem());
		assertEquals(expNrOfColor, Integer.parseInt(view.getTotalColors().getText()));
		assertEquals(expNrOfColor, cut.totalNrOfColors());
	}

	private static Stream<Arguments> testChangePalettVariant() {
		return Stream.of(
		    // Black & White: Color grading must be disabled, but the value underneath
		    // doesn't change
		    Arguments.of(PaletteVariant.BLACK_WHITE, 6, ColorGradingStyle.LINE, 6, false, false),
		    // Blue White with a valid number of colors. Grading must be enabled.
		    Arguments.of(PaletteVariant.BLUEWHITE, 6, ColorGradingStyle.LINE, 6, true, true),
		    // RAINBOW7 with a valid number of colors. Grading must be enabled.
		    Arguments.of(PaletteVariant.RAINBOW7, 13, ColorGradingStyle.LINE, 13, true, true),
		    // RAINBOW29 with a valid number of colors. Grading must be enabled.
		    Arguments.of(PaletteVariant.RAINBOW29, 65, ColorGradingStyle.LINE, 65, true, true),
		    // Blue White with an invalid number of colors. Value must be corrected.
		    Arguments.of(PaletteVariant.BLUEWHITE, 2, ColorGradingStyle.LINE, 3, true, true),
		    // RAINBOW7 with an invalid number of colors. Value must be corrected.
		    Arguments.of(PaletteVariant.RAINBOW7, 6, ColorGradingStyle.LINE, 13, true, true),
		    // RAINBOW29 with an invalid number of colors. Value must be corrected.
		    Arguments.of(PaletteVariant.RAINBOW29, 13, ColorGradingStyle.LINE, 57, true, true),
		    // RAINBOW7 with grading style NONE. Grading style must be enabled, number of
		    // colors must be disabled.
		    Arguments.of(PaletteVariant.RAINBOW7, 13, ColorGradingStyle.NONE, 13, true, false),
		    // RAINBOW7 with grading style NONE and far too low noC. Grading style must be
		    // enabled, number of colors must be disabled and the value must remain
		    // unchanged.
		    Arguments.of(PaletteVariant.RAINBOW7, 3, ColorGradingStyle.NONE, 3, true, false));
	}

	/**
	 * Tests the method changePaletteVarian() to the expected combinations
	 * 
	 * @param newValue
	 * @param nrOfColors
	 */
	@ParameterizedTest
	@MethodSource
	final void testChangePalettVariant(PaletteVariant newValue, int nrOfColors, ColorGradingStyle style,
	    int expNrOfColors, boolean expGradingStyleEnabled, boolean expGradingNrOfCEnabled) {
		// Prepare values
		cut.totalNrOfColors(nrOfColors);
		cut.gradingStyle(style);
		// Use cut's function to put these values into the view
		cut.initView();
		// Enter the new value
		view.getPaletteVariant().setSelectedItem(newValue);
		itemEvent = new ItemEvent(view.getPaletteVariant(), 0, newValue, ItemEvent.SELECTED);

		// Run test
		cut.changePalettVariant(itemEvent);
		// Is the color grading section correctly enabled?
		assertEquals(expGradingStyleEnabled, view.getColorGradingStyle().isEnabled());
		assertEquals(expGradingNrOfCEnabled, view.getTotalColors().isEnabled());
		// Is the value set in model and view, correctly?
		assertEquals(newValue, cut.paletteVariant());
		assertEquals(newValue, view.getPaletteVariant().getSelectedItem());
		// Is the number of colors set correctly, changed, if needed?
		assertEquals(expNrOfColors, cut.totalNrOfColors());
		assertEquals(expNrOfColors, Integer.parseInt(view.getTotalColors().getText()));
	}

	/**
	 * Will the controller reinitialize correctly, when the model is replaced?
	 */
	@Test
	final void testReplaceModel() {
		// First change color values in the model
		ColorAttributesDTO color = model.getColor();
		color.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.CIRCLE, 20));
		color.setMandelbrotColor(ColorDTO.of(10, 10, 10));
		color.setPaletteVariant(PaletteVariant.RAINBOW7);

		// Now call replaceModel()
		cut.replaceModel(color);

		// Than check, whether the controller model has the new values.
		assertEquals(ColorGradingStyle.CIRCLE, view.getColorGradingStyle().getSelectedItem());
		assertEquals(20, Integer.valueOf(view.getTotalColors().getText()));
		// assertEquals(new Color(10,10,10), view.get) Not yet implemented
		assertEquals(PaletteVariant.RAINBOW7, view.getPaletteVariant().getSelectedItem());
	}

}
