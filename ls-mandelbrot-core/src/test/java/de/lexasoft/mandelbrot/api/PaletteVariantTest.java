/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class PaletteVariantTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static List<Color> createPalette(Color... colors) {
		List<Color> palette = new ArrayList<>();
		for (Color color : colors) {
			palette.add(color);
		}
		return palette;
	}

	private final static Stream<Arguments> testColorPalette() {
		return Stream.of(Arguments.of(PaletteVariant.BLACK_WHITE, 0, createPalette()),
		    Arguments.of(PaletteVariant.BLUEWHITE, 2, createPalette(new Color(25, 140, 255), new Color(255, 255, 255))),
		    Arguments.of(PaletteVariant.CUSTOM, 0, createPalette()));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.PaletteVariant#colorPalette()}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testColorPalette(PaletteVariant cut, int expNrOfColors, List<Color> expColorPalette) {
		assertEquals(expNrOfColors, cut.nrOfColorsUngraded());
		assertEquals(expColorPalette, cut.colorPalette());
	}

	private final static Stream<Arguments> testNrOfColorsUngraded() {
		return Stream.of(Arguments.of(PaletteVariant.BLACK_WHITE, 0), Arguments.of(PaletteVariant.RAINBOW29, 29),
		    Arguments.of(PaletteVariant.RAINBOW7, 7), Arguments.of(PaletteVariant.BLUEWHITE, 2),
		    Arguments.of(PaletteVariant.CUSTOM, 0));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.PaletteVariant#nrOfColorsUngraded}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testNrOfColorsUngraded(PaletteVariant cut, int expNrOfColors) {
		assertEquals(expNrOfColors, cut.nrOfColorsUngraded());
	}

}
