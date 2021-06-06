/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * 
 * @author nierax
 *
 */
class MandelbrotColorizeFactoryTest {

	private List<Color> colors;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		colors = new ArrayList<>();
		colors.add(Color.BLUE);
		colors.add(Color.WHITE);
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant black and white.
	 * <p>
	 * In this case both list of colors and color interval are not needed.
	 */
	@Test
	void testOfBlackAndWhite() {
		MandelbrotColorize cut = MandelbrotColorizeFactory.of(PaletteVariant.BLACK_WHITE, null, null);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotBlackWhite);
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant rainbow in 29 steps.
	 * <p>
	 * The grading is 0, which means, that the original RAINBOW29 list is expected.
	 */
	@Test
	void testOfRainbow29() {
		MandelbrotColorize cut = MandelbrotColorizeFactory.of(PaletteVariant.RAINBOW29, null, null);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(29, palette.size());
		assertEquals(128, palette.get(0).getRed());
		assertEquals(0, palette.get(0).getGreen());
		assertEquals(0, palette.get(0).getBlue());
		assertEquals(168, palette.get(28).getRed());
		assertEquals(0, palette.get(28).getGreen());
		assertEquals(185, palette.get(28).getBlue());
		assertEquals(29, palette.size());
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant rainbow in 29 steps and a grading of 57 (minimum value).
	 * <p>
	 * A custom (ungraded) palette is not needed, as RAINBOW29 is a predefined list.
	 */
	@Test
	void testOfRainbow29Graded() {
		MandelbrotColorize cut = MandelbrotColorizeFactory.of(PaletteVariant.RAINBOW29, null,
		    MandelbrotColorGrading.of(ColorGradingStyle.LINE, 57));
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(57, palette.size());
		assertEquals(128, palette.get(0).getRed());
		assertEquals(0, palette.get(0).getGreen());
		assertEquals(0, palette.get(0).getBlue());
		assertEquals(168, palette.get(56).getRed());
		assertEquals(0, palette.get(56).getGreen());
		assertEquals(185, palette.get(56).getBlue());
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant blue white with a line grading of 3 (minimum).
	 * <p>
	 * A custom (ungraded) palette is not needed, as BLUEWHITE is a predefined list.
	 */
	@Test
	void testOfBlueWhiteGraded() {
		MandelbrotColorize cut = MandelbrotColorizeFactory.of(PaletteVariant.BLUEWHITE, null,
		    MandelbrotColorGrading.of(ColorGradingStyle.LINE, 3));
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(3, palette.size());
		assertEquals(new Color(25, 140, 255), palette.get(0));
		assertEquals(Color.WHITE, palette.get(2));
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant custom in 2 steps, graded to 3 steps.
	 */
	@Test
	void testOfCustomGraded() {
		MandelbrotColorize cut = MandelbrotColorizeFactory.of(PaletteVariant.CUSTOM, colors,
		    MandelbrotColorGrading.of(ColorGradingStyle.LINE, 3));
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(3, palette.size());
		assertEquals(Color.BLUE, palette.get(0));
		assertEquals(new Color(128, 128, 255), palette.get(1));
		assertEquals(Color.WHITE, palette.get(2));
	}

	private static Stream<Arguments> testOfCustomNotGraded() {
		return Stream.of(null, Arguments.of(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 0)),
		    Arguments.of(MandelbrotColorGrading.of(ColorGradingStyle.CIRCLE, 0)),
		    Arguments.of(MandelbrotColorGrading.of(ColorGradingStyle.NONE, 0)),
		    Arguments.of(MandelbrotColorGrading.of(ColorGradingStyle.NONE, 50)));
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.MandelbrotColorize#of()} with
	 * color variant custom in 2 steps, not graded.
	 */
	@ParameterizedTest
	@MethodSource
	void testOfCustomNotGraded(MandelbrotColorGrading grading) {
		MandelbrotColorize cut = MandelbrotColorizeFactory.of(PaletteVariant.CUSTOM, colors, grading);
		assertNotNull(cut);
		assertTrue(cut instanceof MandelbrotColorPalette);
		List<Color> palette = ((MandelbrotColorPalette) cut).getPalette();
		assertNotNull(palette);
		// Check, whether this is the right palette.
		assertEquals(2, palette.size());
		assertEquals(Color.BLUE, palette.get(0));
		assertEquals(Color.WHITE, palette.get(1));
	}

}
